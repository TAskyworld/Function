package functionitegretion.customfunction.player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnPreparedListener;
/**
 * MP3�ļ�����ʵ����
 * @author TA
 *
 */
public class MP3Player extends IPlayer {
	private static MP3Player _Player;
	private static MediaPlayer _MediaPlayer;
	private List<OnPlayCompleted> listeners = null;
	private static HashMap<Context, MP3Player> _Players = null;
	private String _content = "";
	private boolean _playEnd = false;

	/**
	 * ���캯��
	 * @param context
	 */
	private MP3Player(Context context) {
		_MediaPlayer = new MediaPlayer();
		_MediaPlayer.setOnCompletionListener(new OnCompletionListener() {
			@Override
			public void onCompletion(MediaPlayer arg0) {
				_playEnd = true;
				if (null != listeners && listeners.size() > 0) {
					synchronized (listeners) {
						for (int i = 0; i < listeners.size(); i++) {
							OnPlayCompleted lis = listeners.get(i);
							lis.PlayCompleted();
						}
					}
				}
			}
		});
		_MediaPlayer.setOnPreparedListener(new OnPreparedListener() {

			@Override
			public void onPrepared(MediaPlayer arg0) {
				_playEnd = false;
				_MediaPlayer.start();
			}
		});
	}

	/**
	 * ����ģʽ��ʼ��MP3������
	 * 
	 * @param context
	 * @return MP3������
	 */
	public static IPlayer getInstance(Context context) {
		if (null == _Player) {
			_Players = new HashMap<Context, MP3Player>();
		}
		if (_Players.containsKey(context)) {
			return _Players.get(context);
		}
		_Player = new MP3Player(context);
		_Players.put(context, _Player);
		return _Player;
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
	 * ���������Ƿ����
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
	 * ִ����������
	 */
	@Override
	public void doPlay(String content) {
		try {
			if (_MediaPlayer.isPlaying())
				doStop();
			_MediaPlayer.reset();
			_MediaPlayer.setDataSource(content);
			_MediaPlayer.prepareAsync();
			_content = content;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * ��ֹ��������
	 */
	@Override
	public void doStop() {
		if (null != _MediaPlayer) {
			_MediaPlayer.stop();
		}
		_playEnd = true;
		if (null != listeners && listeners.size() > 0) {
			synchronized (listeners) {
				for (int i = 0; i < listeners.size(); i++) {
					OnPlayCompleted lis = listeners.get(i);
					lis.PlayCompleted();
				}
			}
		}

	}

	@Override
	protected void finalize() throws Throwable {
		_MediaPlayer.release();
		super.finalize();
	}
}
