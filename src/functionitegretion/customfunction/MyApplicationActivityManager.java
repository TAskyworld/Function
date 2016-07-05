package functionitegretion.customfunction;

import java.util.LinkedList;
import java.util.List;
import android.app.Activity;
import android.app.Application;
/**
 * activity������
 * @author ThinkPad User
 *
 */
public class MyApplicationActivityManager extends Application {
	private static MyApplicationActivityManager instance;

	private List<Activity> activityList = new LinkedList<Activity>();

	public MyApplicationActivityManager() {
	}

	// ����ģʽ��ȡΨһ��MyApplicationʵ��
	public static MyApplicationActivityManager getInstance() {
		if (null == instance) {
			instance = new MyApplicationActivityManager();
		}
		return instance;
	}

	// ���Activity��������
	public void addActivity(Activity activity) {
		if (!activityList.contains(activity))
			activityList.add(activity);
	}

	// ��������Activity��finish
	public void exit() {
		for (Activity activity : activityList) {
			activity.finish();
		}
		System.exit(0);
	}
}
