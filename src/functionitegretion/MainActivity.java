package functionitegretion;

import com.example.functionitegretion1.R;
import functionitegretion.customfunction.loction.BaiduLocation;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

@SuppressLint("HandlerLeak")
public class MainActivity extends ActionBarActivity implements OnClickListener {
	private final static int SCANNIN_GREQUEST_CODE = 1;
	private TextView tv_location;
	private Button btn_location, btn_math, btn_playengine3;
	private Button btn_progressbar;
	private Handler locationhandler = new Handler() {
		@Override
		public void handleMessage(android.os.Message msg) {
			String address = msg.obj.toString();
			tv_location.setText(address);
		};
	};

	private ProgressDialog mpdialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initview();
	}

	private void initview() {
		tv_location = (TextView) findViewById(R.id.tv_location);
		btn_location = (Button) findViewById(R.id.btn_location);
		btn_math = (Button) findViewById(R.id.btn_math);
		btn_progressbar = (Button) findViewById(R.id.btn_playengine2);
		btn_playengine3 = (Button) findViewById(R.id.btn_playengine3);
		btn_math.setOnClickListener(this);
		btn_location.setOnClickListener(this);
		btn_progressbar.setOnClickListener(this);
		btn_playengine3.setOnClickListener(this);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
		case SCANNIN_GREQUEST_CODE:
			if (resultCode == RESULT_OK) {
				Bundle bundle = data.getExtras();
				// ��ʾɨ�赽�����ݣ��ַ�����
				tv_location.setText(bundle.getString("result"));
				// ��ʾͼƬ
				// mImageView.setImageBitmap((Bitmap)
				// data.getParcelableExtra("bitmap"));
			}
			break;
		}
	}

	/*
	 * �ٶȶ�λ*
	 */
	private void initLocation() {
		/** ����location���� */
		BaiduLocation baidu = new BaiduLocation(getApplicationContext(),
				locationhandler);
		baidu.initLocationclient();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_location:
			initLocation();
			break;
		case R.id.btn_math:
			redommath(25);
			break;
		case R.id.btn_playengine2:
			Dialogs();
			handler.sendEmptyMessage(1);
			break;
		case R.id.btn_playengine3:
		    Intent intent = new Intent();
		    intent.setClass(MainActivity.this, MipcaActivityCapture.class);
		    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		    startActivityForResult(intent, SCANNIN_GREQUEST_CODE);
		    break;
		default:
			break;
		}
	}

	private void redommath(int math) {
		tv_location.setText((int) (Math.random() * math) + "");
	}

	/**
	 * ����������
	 */
	private ProgressDialog Dialogs() {
		mpdialog = new ProgressDialog(MainActivity.this);
		// ���ú�����������
		mpdialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		mpdialog.setTitle("��ʾ"); // ���ñ���
		// mpdialog.setIcon(R.drawable.icon); //����ͼ��
		mpdialog.setMessage("���Ǻ��������"); // ��������
		mpdialog.setMax(100);
		mpdialog.setProgress(0);
		mpdialog.setSecondaryProgress(50);
		mpdialog.setIndeterminate(false); // ���ý������Ƿ���Բ���ȷ
		mpdialog.setCancelable(true); // ���ý������Ƿ����ȡ��
		// mpdialog.setButton("ȷ��", new DialogInterface.OnClickListener() {
		//
		// @Override
		// public void onClick(DialogInterface dialog, int whic) {
		// // TODO Auto-generated method stub
		// dialog.cancel(); //ȡ��
		// }
		// });
		mpdialog.show(); // ��ʾ������
		return mpdialog;
	}

	private Handler handler = new Handler() {
		@Override
		public void handleMessage(android.os.Message msg) {
			mpdialog.setProgress(10);
			mpdialog.setMessage("��ʼ");
			new Thread() {
				@Override
				public void run() {
					try {
						Thread.sleep(400);
						mpdialog.setProgress(50);
						mpdialog.setMessage("����������");
					} catch (Exception e) {
						// mpdialog.cancel();
					}
				}
			}.start();

		};
	};
}
