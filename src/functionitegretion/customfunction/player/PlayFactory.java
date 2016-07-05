package functionitegretion.customfunction.player;

import functionitegretion.customfunction.player.IPlayer.PLAY_TYPE;
import android.content.Context;
/**
 * 播放工厂类
 * @author ThinkPad User
 *
 */
public class PlayFactory {
	private static IPlayer _Player;
	
	public static IPlayer getInstance(Context context, PLAY_TYPE type){
		
		if(type == PLAY_TYPE.IFlyTTS){
			_Player = IFlyTTS.getInstance(context);
		}
		else if(type == PLAY_TYPE.MP3){
			_Player = MP3Player.getInstance(context);
		}
		return _Player;
	}
}
