package functionitegretion.customfunction;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;

/**
 * ���������dialog
 * 
 * @author TA
 */
public class CustomProgressdialog {
	private ProgressDialog mprogressdialog;
    private AlertDialog.Builder dialog;
	/**
	 * ����������
	 */
	public ProgressDialog Dialogs(Context context) {
		mprogressdialog = new ProgressDialog(context);
		// ���ú�����������
		mprogressdialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		mprogressdialog.setTitle("��ʾ"); // ���ñ���
		// mpdialog.setIcon(R.drawable.icon); //����ͼ��
		mprogressdialog.setMessage("���Ǻ��������"); // ��������
		mprogressdialog.setMax(100);
		mprogressdialog.setProgress(0);
		mprogressdialog.setSecondaryProgress(50);
		mprogressdialog.setIndeterminate(false); // ���ý������Ƿ���Բ���ȷ
		mprogressdialog.setCancelable(true); // ���ý������Ƿ����ȡ��
		// mprogressdialog.setButton("ȷ��", new DialogInterface.OnClickListener() {
		//
		// @Override
		// public void onClick(DialogInterface dialog, int whic) {
		// // TODO Auto-generated method stub
		// dialog.cancel(); //ȡ��
		// }
		// });
		mprogressdialog.show(); // ��ʾ������
		return mprogressdialog;
	}
	
	public void initDialog(Context context) {
		dialog = new AlertDialog.Builder(context);
		dialog.setTitle("��ʾ");
		dialog.setMessage("�������ڴ����У����Ժ�...");
		dialog.show();
	}

	public void hidProgressDialog() {
		if (dialog != null) {
			((DialogInterface) dialog).cancel();
		}
	}
}
