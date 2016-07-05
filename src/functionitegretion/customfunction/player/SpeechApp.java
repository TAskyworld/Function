package functionitegretion.customfunction.player;

import android.app.Application;

import com.iflytek.cloud.SpeechUtility;
/**
 * 引擎播放初始化
 * @author ThinkPad User
 */
public class SpeechApp extends Application {
	@Override
	public void onCreate() {
		SpeechUtility.createUtility(SpeechApp.this, "appid=54859360");
		super.onCreate();
	}
}
