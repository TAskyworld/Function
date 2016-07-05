package functionitegretion.customfunction;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;

/**
 * 横向进度条dialog
 * 
 * @author TA
 */
public class CustomProgressdialog {
	private ProgressDialog mprogressdialog;
    private AlertDialog.Builder dialog;
	/**
	 * 进度条弹窗
	 */
	public ProgressDialog Dialogs(Context context) {
		mprogressdialog = new ProgressDialog(context);
		// 设置横向进度条风格
		mprogressdialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		mprogressdialog.setTitle("提示"); // 设置标题
		// mpdialog.setIcon(R.drawable.icon); //设置图标
		mprogressdialog.setMessage("这是横向进度条"); // 设置内容
		mprogressdialog.setMax(100);
		mprogressdialog.setProgress(0);
		mprogressdialog.setSecondaryProgress(50);
		mprogressdialog.setIndeterminate(false); // 设置进度条是否可以不明确
		mprogressdialog.setCancelable(true); // 设置进度条是否可以取消
		// mprogressdialog.setButton("确定", new DialogInterface.OnClickListener() {
		//
		// @Override
		// public void onClick(DialogInterface dialog, int whic) {
		// // TODO Auto-generated method stub
		// dialog.cancel(); //取消
		// }
		// });
		mprogressdialog.show(); // 显示进度条
		return mprogressdialog;
	}
	
	public void initDialog(Context context) {
		dialog = new AlertDialog.Builder(context);
		dialog.setTitle("提示");
		dialog.setMessage("数据正在处理中，请稍候...");
		dialog.show();
	}

	public void hidProgressDialog() {
		if (dialog != null) {
			((DialogInterface) dialog).cancel();
		}
	}
}
