package functionitegretion.customfunction.player;

import android.app.Application;

import com.iflytek.cloud.SpeechUtility;
/**
 * ���沥�ų�ʼ��
 * @author ThinkPad User
 */
public class SpeechApp extends Application {
	@Override
	public void onCreate() {
		SpeechUtility.createUtility(SpeechApp.this, "appid=54859360");
		super.onCreate();
	}
}
