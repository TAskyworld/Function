package functionitegretion.customfunction;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * ���ڸ�ʽת��������
 * 
 * @author TA
 *
 */
public abstract class DateTimeHelper {
	/**
	 * ��ʽ������ ��-��-�� ʱ���֣���
	 */
	public static SimpleDateFormat sdf = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");// Сд��mm��ʾ���Ƿ���

	/**
	 * ��ǰ����ʱ��
	 */
	public static String getNow() {
		Date date = new Date();
		return sdf.format(date);
	}

	/**
	 * ��ʽΪ"mm:ss" �֣���
	 */
	public static SimpleDateFormat shortTimeFormater = new SimpleDateFormat(
			"HH:mm");// Сд��mm��ʾ���Ƿ���

	/**
	 * ��ʽΪ"HH:mm:ss" ʱ���֣���
	 */
	public static SimpleDateFormat timeFormater = new SimpleDateFormat(
			"HH:mm:ss");// Сд��mm��ʾ���Ƿ���

	/**
	 * ��ȡָ����ʽ�������ַ���
	 * 
	 * @param formater
	 *            ��ʽ�ַ���
	 * @return
	 */
	public static String dateToString(String formater) {
		return new SimpleDateFormat(formater).format(new Date());
	}

	/**
	 * ��ȡָ����ʽ�������ַ���
	 * 
	 * @param formater
	 *            ��ʽ�ַ���
	 * @param date
	 *            ��Ҫ��ʽ����Date
	 * @return
	 */
	public static String dateToString(Date date, String formater) {
		return new SimpleDateFormat(formater).format(date);
	}
}
