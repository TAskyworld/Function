package functionitegretion.customfunction.player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnPreparedListener;
/**
 * MP3文件播放实现类
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
	 * 构造函数
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
	 * 单例模式初始化MP3播放器
	 * 
	 * @param context
	 * @return MP3播放器
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
	 * 语音播报是否完成
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
	 * 执行语音播报
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
	 * 终止语音播报
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
