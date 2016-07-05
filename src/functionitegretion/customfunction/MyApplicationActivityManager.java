package functionitegretion.customfunction;

import java.util.LinkedList;
import java.util.List;
import android.app.Activity;
import android.app.Application;
/**
 * activity管理类
 * @author ThinkPad User
 *
 */
public class MyApplicationActivityManager extends Application {
	private static MyApplicationActivityManager instance;

	private List<Activity> activityList = new LinkedList<Activity>();

	public MyApplicationActivityManager() {
	}

	// 单例模式获取唯一的MyApplication实例
	public static MyApplicationActivityManager getInstance() {
		if (null == instance) {
			instance = new MyApplicationActivityManager();
		}
		return instance;
	}

	// 添加Activity到容器中
	public void addActivity(Activity activity) {
		if (!activityList.contains(activity))
			activityList.add(activity);
	}

	// 遍历所有Activity并finish
	public void exit() {
		for (Activity activity : activityList) {
			activity.finish();
		}
		System.exit(0);
	}
}
