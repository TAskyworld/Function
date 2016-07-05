package functionitegretion.customfunction;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

//import com.google.gson.Gson;
//import com.google.gson.reflect.TypeToken;
//import com.kczx.R;
//import com.kczx.activity.unitl.VersionUpdate.MdownApkThread;
//import com.kczx.common.ApplicationData;
//import com.kczx.entity.ResultMSG;
//import com.kczx.entity.VersionInfo;
//import com.kczx.unitl.HttpUnitl;


import com.example.functionitegretion1.R;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;
/**
 * 自动更新下载安装软件
 * @author TA
 *
 */
public class VersionUpdate {
	private Context mContext;
	/** 提醒窗口 */
	private Dialog noticeDialog;
	/** 下载窗口 */
	private Dialog downloadDialog;
	/** 下载包安装路径 */
	private static final String path = "/sdcard/";
	/** 下载包安装路径 */
	private static final String filename = path + "DrivingExamSystem.apk";
	/** 进度条与通知ui刷新的handler和msg常量 */
	private ProgressBar mProgress;
	/** 下载中 */
	private static final int DOWN_UPDATE = 1;
	/** 下载完成 */
	private static final int DOWN_OVER = 2;
	/** 下载完成进度 */
	private int progress;
	/** 下载线程 */
	private MdownApkThread downLoadThread;

	private boolean interceptFlag = false;

	/** 是否强制更新 **/
	private boolean isConstraint = false;

//	private Gson gson = new Gson();
	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case DOWN_UPDATE:
				mProgress.setProgress(progress);
				break;
			case DOWN_OVER:
				installApk();
				break;
			default:
				break;
			}
		};
	};

	public VersionUpdate(Context context) {
		this.mContext = context;
		File file = new File(filename);
		if (file.exists()) {
			file.delete();
		}
	}


//	// 外部接口让主Activity调用
//	@SuppressLint("HandlerLeak")
//	public void checkUpdateInfo() {
//		getVersionName(mContext);
//		Map<String, Object> params = new HashMap<String, Object>();
//		params.put("type", "001004");
//		Handler post = new Handler() {
//			@Override
//			public void handleMessage(Message msg) {
//				String json = (String) msg.obj;
//				@SuppressWarnings("unchecked")
//				ResultMSG<String> result = (ResultMSG<String>) gson.fromJson(
//						json, ResultMSG.class);
//				if (null == result) {
//					// Toast.makeText(mContext, "初始化失败，请检查网络连接是否正常！",
//					// Toast.LENGTH_LONG).show();
//				} else {
//					if (result.IsSuccess) {
//						try {
//							Toast.makeText(mContext, "获取版本信息成功！",
//									Toast.LENGTH_LONG).show();
//							VersionInfo version = gson.fromJson(result.Tag,
//									new TypeToken<VersionInfo>() {
//									}.getType());
//							String softwareversion = version.Version;
//							if (getVersionName(mContext)
//									.equals(softwareversion)) {
//
//							} else {
////								showNoticeDialog(version.PathInfo.Host + version.PathInfo.Path, null);
//								 showDownloadDialog("http://192.168.1.190/" + "cd/DrivingExamSystem.rar","");
//							}
//						} catch (Exception e) {
//
//						}
//					} else {
//						Toast.makeText(mContext, "获取版本信息失败！", Toast.LENGTH_LONG)
//								.show();
//					}
//				}
//			}
//		};
//		HttpUnitl.getInstantiation().doPost(
//				ApplicationData.getWebApiUrl("020/15/version"), params, post);
//
//	}

	/** 版本名 **/
	public static String getVersionName(Context context) {
		return getPackageInfo(context).versionName;
	}

	/** 版本号 **/
	public static int getVersionCode(Context context) {
		return getPackageInfo(context).versionCode;
	}

	private static PackageInfo getPackageInfo(Context context) {
		PackageInfo pi = null;

		try {
			PackageManager pm = context.getPackageManager();
			pi = pm.getPackageInfo(context.getPackageName(),
					PackageManager.GET_CONFIGURATIONS);

			return pi;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return pi;
	}

	/** 显示通知窗口 */
	private void showNoticeDialog(final String strUrl, String description) {
		AlertDialog.Builder builder = new Builder(mContext);
		builder.setTitle("软件版本更新");
		builder.setMessage("更新说明: \n ");
		builder.setMessage(description);
		builder.setCancelable(false);
		builder.setPositiveButton("更新", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				showDownloadDialog(strUrl, null);
			}
		});
		builder.setNegativeButton("以后再说", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		noticeDialog = builder.create();
		noticeDialog.show();
		Button button = ((AlertDialog) noticeDialog)
				.getButton(AlertDialog.BUTTON_NEGATIVE);
		button.setEnabled(!isConstraint);
	}

	/** 显示下载进度 */
	private void showDownloadDialog(String strUrl, String description) {
		AlertDialog.Builder builder = new Builder(mContext);
		builder.setTitle("软件版本更新");
		builder.setMessage(description);
		builder.setCancelable(false);

		final LayoutInflater inflater = LayoutInflater.from(mContext);
		View v = inflater.inflate(R.layout.update_progress, null);
		mProgress = (ProgressBar) v.findViewById(R.id.pb_update);

		builder.setView(v);
		builder.setNegativeButton("取消", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				interceptFlag = true;
			}
		});
		downloadDialog = builder.create();
		downloadDialog.show();
		// 如果为强制更新版本，禁用取消按钮
		Button button = ((AlertDialog) downloadDialog)
				.getButton(AlertDialog.BUTTON_NEGATIVE);
		button.setEnabled(!isConstraint);
		downloadApk(strUrl);
	}

	private class MdownApkThread extends Thread {
		private String strUrl = null;

		public MdownApkThread(String _url) {
			strUrl = _url;
		}

		@Override
		public void run() {
			try {
				File dir = new File(path);
				if (!dir.exists()) {
					dir.mkdir();
				}
				File file = new File(filename);
				if (file.exists()) {
					file.delete();
				}
				FileOutputStream fos = new FileOutputStream(filename);

				URL url = new URL(strUrl);
				HttpURLConnection conn = (HttpURLConnection) url
						.openConnection();
				conn.connect();
				int length = conn.getContentLength();
				InputStream is = conn.getInputStream();
				int downloaded = 0;
				byte[] buf = new byte[1024];

				int size = 0;
				while (!interceptFlag) {
					size = is.read(buf);
					if (size <= 0) {
						mHandler.sendEmptyMessage(DOWN_OVER);
						break;
					}

					fos.write(buf, 0, size);
					downloaded += size;
					progress = (int) (((float) downloaded / length) * 100);
					mHandler.sendEmptyMessage(DOWN_UPDATE);
				}
				fos.close();
				is.close();
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/** 下载apk */
	private void downloadApk(String url) {
		downLoadThread = new MdownApkThread(url);
		downLoadThread.start();
	}

	/** 安装apk */
	private void installApk() {
		try {
			File file = new File(filename);
			if (!file.exists()) {
				return;
			}
			Intent i = new Intent(Intent.ACTION_VIEW);
			i.setDataAndType(Uri.parse("file://" + file.toString()),
					"application/vnd.android.package-archive");
			mContext.startActivity(i);
			Toast.makeText(mContext, "安装成功", Toast.LENGTH_LONG).show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
