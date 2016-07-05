package functionitegretion.customfunction;

import java.text.DecimalFormat;

/**
 * 字符串操作帮助类
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
	 * 验证字符串是否空
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
	 * 反转String
	 * 
	 * @param original
	 *            需要反转的字符串
	 * @return 反转后的字符串
	 */
	public static String Reverse(String original) {
		char[] array = original.toCharArray();
		String reverse = ""; // 注意这是空串，不是null
		for (int i = array.length - 1; i >= 0; i--)
			reverse += array[i];
		return reverse;
	}

	/**
	 * 字符串右对齐。 返回一个新字符串，该字符串通过在此实例中的字符左侧填充指定的 Unicode 字符来达到指定的总长度，从而使这些字符右对齐。
	 * 
	 * @param sourceString
	 *            源字符串。
	 * @param totalWidth
	 *            结果字符串中的字符数，等于原始字符数加上任何其他填充字符。
	 * @param paddingChar
	 *            Unicode 填充字符。
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
	 * 字符串左对齐 返回一个新字符串，该字符串通过在此字符串中的字符右侧填充指定的 Unicode 字符来达到指定的总长度，从而使这些字符左对齐。
	 * 
	 * @param sourceString
	 *            源字符串。
	 * @param totalWidth
	 *            结果字符串中的字符数，等于原始字符数加上任何其他填充字符。
	 * @param paddingChar
	 *            Unicode 填充字符。
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
	 * 将指定字符串中的一个或多个格式项替换为指定对象的字符串表示形式。
	 * 
	 * @param format
	 *            复合格式字符串。
	 * @param args
	 *            一个对象数组，其中包含零个或多个要设置格式的对象。
	 * @return format 的副本，其中的格式项已替换为 args 中相应对象的字符串表示形式。
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
