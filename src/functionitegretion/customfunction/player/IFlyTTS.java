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
 * ���沥��ʵ����
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
	 * ���캯��
	 * 
	 * @param context
	 */
	private IFlyTTS(Context context) {
		// ��ʼ���ϳɶ���
		_speech = SpeechSynthesizer.createSynthesizer(context, initListener);
		_shared = context.getSharedPreferences("com.iflytek.setting",
				Context.MODE_PRIVATE);

		_speech.setParameter(SpeechConstant.ENGINE_TYPE,
				SpeechConstant.TYPE_LOCAL);
		// ���÷����� voicerΪ��Ĭ��ͨ������+����ָ�������ˡ�
		_speech.setParameter(SpeechConstant.VOICE_NAME, "");
		// ���ò�������Ƶ������
		_speech.setParameter(SpeechConstant.STREAM_TYPE,
				_shared.getString("stream_preference", "1"));// 0��Ϊ��Ͳ
	}

	/**
	 * ��ʼ��������
	 */
	private InitListener initListener = new InitListener() {
		@Override
		public void onInit(int code) {
			if (code != ErrorCode.SUCCESS) {
				_toast.setText("��ʼ��ʧ��,�����룺" + code);
				_toast.show();
			}
		}
	};

	/**
	 * ����ģʽʵ����
	 * 
	 * @param context
	 * @return TTS�ϳ�����
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
	 * ���������������¼�
	 */
	@Override
	public void setOnPlayCompleted(OnPlayCompleted listener) {
		if (null == listeners) {
			listeners = new ArrayList<OnPlayCompleted>();
		}
		listeners.add(listener);
	}

	/**
	 * �Ƴ�������������¼�
	 */
	@Override
	public void removeOnPlayCompleted(OnPlayCompleted listener) {
		if (null != listeners && listeners.contains(listener)) {
			listeners.remove(listener);
		}
	}

	/**
	 * �Ƿ񲥱����
	 */
	@Override
	public boolean isCompleted() {
		return _playEnd;
	}

	/**
	 * ��ȡ������������
	 */
	@Override
	public String getContent() {
		return _content;
	}

	/**
	 * ��ʼ��������
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
				_toast.setText("δ��װ���߰�");
				_toast.show();
			}
		} catch (Exception e) {
		}
	}

	/**
	 * ���������¼�
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
	 * ��ֹ��������
	 */
	@Override
	public void doStop() {
		if (null != _speech) {
			_speech.stopSpeaking();
		}
		listener.onCompleted(null);
	}
}
