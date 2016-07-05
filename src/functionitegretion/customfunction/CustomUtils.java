package functionitegretion.customfunction;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;

/**
 * 获取程序的版本号，程序名
 * 
 * @author TA
 *
 */
public class CustomUtils {
	/** 获取VersionName */
	// public static String getVersionName(Context context) {
	// try {
	// PackageInfo pi =
	// context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
	// return pi.versionName;
	// } catch (NameNotFoundException e) {
	// e.printStackTrace();
	// return context.getString(R.string.NotFindVersionName);
	// }
	// }
	/**
	 * 获取VersionCode 版本号
	 */
	public static int getVersionCode(Context context) {
		try {
			PackageInfo pi = context.getPackageManager().getPackageInfo(
					context.getPackageName(), 0);
			return pi.versionCode;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
			return 0;
		}
	}

	/**
	 * 获取应用程序名称
	 */
	public static String getApplicationName(Context context) {
		PackageManager packageManager = null;
		ApplicationInfo applicationInfo = null;
		try {
			packageManager = context.getApplicationContext()
					.getPackageManager();
			applicationInfo = packageManager.getApplicationInfo(
					context.getPackageName(), 0);
		} catch (PackageManager.NameNotFoundException e) {
			applicationInfo = null;
		}
		String applicationName = (String) packageManager
				.getApplicationLabel(applicationInfo);
		return applicationName;
	}
}