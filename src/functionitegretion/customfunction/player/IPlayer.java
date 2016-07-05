package functionitegretion.customfunction.player;

/**
 * 语音播放接口
 * @author ThinkPad User
 */
public abstract class IPlayer {
	/**
	 * 音频播报完成接口
	 * @author XLiangLee
	 */
	public interface OnPlayCompleted{
		void PlayCompleted();
	}
	
	/**
	 * 播报方式枚举
	 * @author XLiangLee
	 */
	public enum PLAY_TYPE{
		IFlyTTS,
		MP3
	}
	
	/**
	 * 添加语音播报完成事件
	 * @param listener 语音播报完成事件
	 */
	public abstract void setOnPlayCompleted(OnPlayCompleted listener);
	
	/**
	 * 移除语音播报完成事件
	 * @param listener 语音播报完成事件
	 */
	public abstract void removeOnPlayCompleted(OnPlayCompleted listener);
	
	/**
	 * 是否播报完成
	 * @return 音频是否播报完成; true : 是; false : 否
	 */
	public abstract boolean isCompleted(); 
	
	/**
	 * 获取播报内容
	 * @return 播放器播报的内容;如使用语音引擎,返回合成文本;如使用MediaPlayer播报,返回文件名
	 */
	public abstract String getContent();
	
	/**
	 * 开始播报音频
	 * @param content 播放器播报的内容;如使用语音引擎,返回合成文本;如使用MediaPlayer播报,返回文件名
	 */
	public abstract void doPlay(String content);
	
	/**
	 * 停止语音播报
	 */
	public abstract void doStop();
}
