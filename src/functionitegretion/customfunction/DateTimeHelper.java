package functionitegretion.customfunction;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日期格式转换帮助类
 * 
 * @author TA
 *
 */
public abstract class DateTimeHelper {
	/**
	 * 格式化日期 年-月-日 时：分：秒
	 */
	public static SimpleDateFormat sdf = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");// 小写的mm表示的是分钟

	/**
	 * 当前日期时间
	 */
	public static String getNow() {
		Date date = new Date();
		return sdf.format(date);
	}

	/**
	 * 格式为"mm:ss" 分：秒
	 */
	public static SimpleDateFormat shortTimeFormater = new SimpleDateFormat(
			"HH:mm");// 小写的mm表示的是分钟

	/**
	 * 格式为"HH:mm:ss" 时：分：秒
	 */
	public static SimpleDateFormat timeFormater = new SimpleDateFormat(
			"HH:mm:ss");// 小写的mm表示的是分钟

	/**
	 * 获取指定格式的日期字符串
	 * 
	 * @param formater
	 *            格式字符串
	 * @return
	 */
	public static String dateToString(String formater) {
		return new SimpleDateFormat(formater).format(new Date());
	}

	/**
	 * 获取指定格式的日期字符串
	 * 
	 * @param formater
	 *            格式字符串
	 * @param date
	 *            需要格式化的Date
	 * @return
	 */
	public static String dateToString(Date date, String formater) {
		return new SimpleDateFormat(formater).format(date);
	}
}
