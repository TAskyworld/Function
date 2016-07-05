package functionitegretion.customfunction;

import com.example.functionitegretion1.R;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 自定义toast
 * 
 * @author TA
 * @param context
 *            上下文环境
 * @param text
 *            显示内容
 * @param duration
 *            持续时间
 */
public class MyToast {
	/**
	 * 加载系统自带的toast
	 * 
	 * @return
	 */
	public static Toast makeTexto(Context context, CharSequence text,
			int duration) {
		return makeText(context, text, duration);
	}

	/** 自定义toast */
	public static Toast makeText(Context context, CharSequence text,
			int duration) {
		Toast result = new Toast(context);
		LayoutInflater inflate = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		/* 自定义toast的布局文件 */
		View v = inflate.inflate(R.layout.activity_error_info, null);
		/* 自定义toast布局内的控件 */
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
