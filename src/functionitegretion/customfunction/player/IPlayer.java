package functionitegretion.customfunction.player;

/**
 * �������Žӿ�
 * @author ThinkPad User
 */
public abstract class IPlayer {
	/**
	 * ��Ƶ������ɽӿ�
	 * @author XLiangLee
	 */
	public interface OnPlayCompleted{
		void PlayCompleted();
	}
	
	/**
	 * ������ʽö��
	 * @author XLiangLee
	 */
	public enum PLAY_TYPE{
		IFlyTTS,
		MP3
	}
	
	/**
	 * ���������������¼�
	 * @param listener ������������¼�
	 */
	public abstract void setOnPlayCompleted(OnPlayCompleted listener);
	
	/**
	 * �Ƴ�������������¼�
	 * @param listener ������������¼�
	 */
	public abstract void removeOnPlayCompleted(OnPlayCompleted listener);
	
	/**
	 * �Ƿ񲥱����
	 * @return ��Ƶ�Ƿ񲥱����; true : ��; false : ��
	 */
	public abstract boolean isCompleted(); 
	
	/**
	 * ��ȡ��������
	 * @return ����������������;��ʹ����������,���غϳ��ı�;��ʹ��MediaPlayer����,�����ļ���
	 */
	public abstract String getContent();
	
	/**
	 * ��ʼ������Ƶ
	 * @param content ����������������;��ʹ����������,���غϳ��ı�;��ʹ��MediaPlayer����,�����ļ���
	 */
	public abstract void doPlay(String content);
	
	/**
	 * ֹͣ��������
	 */
	public abstract void doStop();
}
