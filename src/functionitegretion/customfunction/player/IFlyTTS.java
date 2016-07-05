package functionitegretion.customfunction.player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.SynthesizerListener;
/**
 * 引擎播放实现类
 * @author ThinkPad User
 *
 */
public class IFlyTTS extends IPlayer {
	private static IFlyTTS _tts;
	private static HashMap<Context, IFlyTTS> _players = null;
	private SpeechSynthesizer _speech;
	private SharedPreferences _shared;
	private boolean _playEnd = false;
	private String _content = "";
	private List<OnPlayCompleted> listeners = null;
	private Toast _toast;

	/**
	 * 构造函数
	 * 
	 * @param context
	 */
	private IFlyTTS(Context context) {
		// 初始化合成对象
		_speech = SpeechSynthesizer.createSynthesizer(context, initListener);
		_shared = context.getSharedPreferences("com.iflytek.setting",
				Context.MODE_PRIVATE);

		_speech.setParameter(SpeechConstant.ENGINE_TYPE,
				SpeechConstant.TYPE_LOCAL);
		// 设置发音人 voicer为空默认通过语音+界面指定发音人。
		_speech.setParameter(SpeechConstant.VOICE_NAME, "");
		// 设置播放器音频流类型
		_speech.setParameter(SpeechConstant.STREAM_TYPE,
				_shared.getString("stream_preference", "1"));// 0则为听筒
	}

	/**
	 * 初始化监听。
	 */
	private InitListener initListener = new InitListener() {
		@Override
		public void onInit(int code) {
			if (code != ErrorCode.SUCCESS) {
				_toast.setText("初始化失败,错误码：" + code);
				_toast.show();
			}
		}
	};

	/**
	 * 单例模式实例化
	 * 
	 * @param context
	 * @return TTS合成引擎
	 */
	public static IPlayer getInstance(Context context) {
		if (null == _players) {
			_players = new HashMap<Context, IFlyTTS>();
		}
		if (_players.containsKey(context)) {
			return _players.get(context);
		}
		_tts = new IFlyTTS(context);
		_players.put(context, _tts);
		return _tts;
	}

	/**
	 * 添加语音播报完成事件
	 */
	@Override
	public void setOnPlayCompleted(OnPlayCompleted listener) {
		if (null == listeners) {
			listeners = new ArrayList<OnPlayCompleted>();
		}
		listeners.add(listener);
	}

	/**
	 * 移除语音播报完成事件
	 */
	@Override
	public void removeOnPlayCompleted(OnPlayCompleted listener) {
		if (null != listeners && listeners.contains(listener)) {
			listeners.remove(listener);
		}
	}

	/**
	 * 是否播报完成
	 */
	@Override
	public boolean isCompleted() {
		return _playEnd;
	}

	/**
	 * 获取语音播报内容
	 */
	@Override
	public String getContent() {
		return _content;
	}

	/**
	 * 开始语音播报
	 */
	@Override
	public void doPlay(String content) {
		if (!_playEnd) {
			doStop();
		}
		try {
			int code = _speech.startSpeaking(content, listener);
			_content = content;
			if (code != ErrorCode.SUCCESS) {
				_toast.setText("未安装离线包");
				_toast.show();
			}
		} catch (Exception e) {
		}
	}

	/**
	 * 语音播报事件
	 */
	private SynthesizerListener listener = new SynthesizerListener() {
		@Override
		public void onBufferProgress(int arg0, int arg1, int arg2, String arg3) {
		}

		@Override
		public void onCompleted(SpeechError arg0) {
			_playEnd = true;
			if (arg0 == null) {
				if (null != listeners && listeners.size() > 0) {
					synchronized (listeners) {
						for (int i = 0; i < listeners.size(); i++) {
							OnPlayCompleted lis = listeners.get(i);
							lis.PlayCompleted();
						}
					}
				}
			} else if (arg0 != null) {
				/*
				 * _toast.setText(arg0.getPlainDescription(true));
				 * _toast.show();
				 */
			}
		}

		@Override
		public void onEvent(int arg0, int arg1, int arg2, Bundle arg3) {
		}

		@Override
		public void onSpeakBegin() {
			_playEnd = false;
		}

		@Override
		public void onSpeakPaused() {
		}

		@Override
		public void onSpeakProgress(int arg0, int arg1, int arg2) {
		}

		@Override
		public void onSpeakResumed() {
		}
	};

	/**
	 * 终止语音播报
	 */
	@Override
	public void doStop() {
		if (null != _speech) {
			_speech.stopSpeaking();
		}
		listener.onCompleted(null);
	}
}
