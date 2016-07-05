package functionitegretion.customfunction;

import com.example.functionitegretion1.R;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

/**
 * �Զ���toast
 * 
 * @author TA
 * @param context
 *            �����Ļ���
 * @param text
 *            ��ʾ����
 * @param duration
 *            ����ʱ��
 */
public class MyToast {
	/**
	 * ����ϵͳ�Դ���toast
	 * 
	 * @return
	 */
	public static Toast makeTexto(Context context, CharSequence text,
			int duration) {
		return makeText(context, text, duration);
	}

	/** �Զ���toast */
	public static Toast makeText(Context context, CharSequence text,
			int duration) {
		Toast result = new Toast(context);
		LayoutInflater inflate = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		/* �Զ���toast�Ĳ����ļ� */
		View v = inflate.inflate(R.layout.activity_error_info, null);
		/* �Զ���toast�����ڵĿؼ� */
		TextView tv = (TextView) v.findViewById(R.id.ErrorMessage);
		tv.setText(text);
		result.setDuration(Toast.LENGTH_LONG);
		result.setGravity(Gravity.CENTER, 0, 0);
		result.setView(v);
		// result.show();
		result.setDuration(duration);

		return result;
	}
}
