package functionitegretion.customfunction;

import java.text.DecimalFormat;

/**
 * �ַ�������������
 * 
 * @author TA
 *
 */
public class StringHelper {

	public static String GetString(double value, int count) {
		String s = PadRight("###.##", count, '0');
		DecimalFormat df = new DecimalFormat(s);
		return df.format(value);
	}

	public static String GetString(float value, int count) {
		String s = PadRight("###.##", count, '0');
		DecimalFormat df = new DecimalFormat(s);
		return df.format(value);
	}

	/**
	 * ��֤�ַ����Ƿ��
	 * 
	 * @return true or false
	 */
	public static boolean IsNullOrEmpty(String str) {
		if (null == str || str.equals("")) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * ��תString
	 * 
	 * @param original
	 *            ��Ҫ��ת���ַ���
	 * @return ��ת����ַ���
	 */
	public static String Reverse(String original) {
		char[] array = original.toCharArray();
		String reverse = ""; // ע�����ǿմ�������null
		for (int i = array.length - 1; i >= 0; i--)
			reverse += array[i];
		return reverse;
	}

	/**
	 * �ַ����Ҷ��롣 ����һ�����ַ��������ַ���ͨ���ڴ�ʵ���е��ַ�������ָ���� Unicode �ַ����ﵽָ�����ܳ��ȣ��Ӷ�ʹ��Щ�ַ��Ҷ��롣
	 * 
	 * @param sourceString
	 *            Դ�ַ�����
	 * @param totalWidth
	 *            ����ַ����е��ַ���������ԭʼ�ַ��������κ���������ַ���
	 * @param paddingChar
	 *            Unicode ����ַ���
	 * @return
	 */
	public static String PadLeft(String sourceString, int totalWidth,
			char paddingChar) {
		if (null == sourceString || "" == sourceString) {
			return "";
		}
		if (sourceString.length() >= totalWidth) {
			return sourceString;
		}
		String temp = sourceString;
		for (int i = 0; i < totalWidth - sourceString.length(); i++) {
			temp = String.valueOf(paddingChar) + temp;
		}
		return temp;
	}

	/**
	 * �ַ�������� ����һ�����ַ��������ַ���ͨ���ڴ��ַ����е��ַ��Ҳ����ָ���� Unicode �ַ����ﵽָ�����ܳ��ȣ��Ӷ�ʹ��Щ�ַ�����롣
	 * 
	 * @param sourceString
	 *            Դ�ַ�����
	 * @param totalWidth
	 *            ����ַ����е��ַ���������ԭʼ�ַ��������κ���������ַ���
	 * @param paddingChar
	 *            Unicode ����ַ���
	 * @return
	 */
	public static String PadRight(String sourceString, int totalWidth,
			char paddingChar) {
		if (null == sourceString || "" == sourceString) {
			return "";
		}
		if (sourceString.length() >= totalWidth) {
			return sourceString;
		}
		String temp = sourceString;
		for (int i = 0; i < totalWidth - sourceString.length(); i++) {
			temp += String.valueOf(paddingChar);
		}
		return temp;
	}

	public static int GetIndexFromArray(String[] list, String item) {
		int index = 0;
		for (int i = 0; i < list.length; i++) {
			if (list[i].equals(item)) {
				index = i;
			}
		}
		return index;
	}

	/**
	 * ��ָ���ַ����е�һ��������ʽ���滻Ϊָ��������ַ�����ʾ��ʽ��
	 * 
	 * @param format
	 *            ���ϸ�ʽ�ַ�����
	 * @param args
	 *            һ���������飬���а����������Ҫ���ø�ʽ�Ķ���
	 * @return format �ĸ��������еĸ�ʽ�����滻Ϊ args ����Ӧ������ַ�����ʾ��ʽ��
	 */
	public static String Format(String format, Object... args) {
		for (int i = 0; i < args.length; i++) {
			String item = args[i].toString();
			if (format.indexOf("{" + i + "}") < 0) {
				break;
			}
			while (format.indexOf("{" + i + "}") >= 0) {
				format = format.replace("{" + i + "}", item);
			}
		}
		return format;
	}
}
