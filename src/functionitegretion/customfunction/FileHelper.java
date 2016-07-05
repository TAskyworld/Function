package functionitegretion.customfunction;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FileHelper {
	/**
	 * 复制整个文件夹内容
	 * 
	 * @param oldPath
	 *            String 原文件路径 如：c:/fqf
	 * @param newPath
	 *            String 复制后路径 如：f:/fqf/ff
	 * @return boolean
	 */
	public void copyFolder(String oldPath, String newPath) {

		try {
			(new File(newPath)).mkdirs(); // 如果文件夹不存在 则建立新文件夹
			File a = new File(oldPath);
			String[] file = a.list();
			File temp = null;
			for (int i = 0; i < file.length; i++) {
				if (oldPath.endsWith(File.separator)) {
					temp = new File(oldPath + file[i]);
				} else {
					temp = new File(oldPath + File.separator + file[i]);
				}

				if (temp.isFile()) {
					FileInputStream input = new FileInputStream(temp);
					FileOutputStream output = new FileOutputStream(newPath
							+ "/" + (temp.getName()).toString());
					byte[] b = new byte[1024 * 5];
					int len;
					while ((len = input.read(b)) != -1) {
						output.write(b, 0, len);
					}
					output.flush();
					output.close();
					input.close();
				}
				if (temp.isDirectory()) {// 如果是子文件夹
					copyFolder(oldPath + "/" + file[i], newPath + "/" + file[i]);
				}
			}
		} catch (Exception e) {
			// Toast.makeText(context, "复制整个文件夹内容操作出错",
			// Toast.LENGTH_LONG).show();
			System.out.println("复制整个文件夹内容操作出错");
			e.printStackTrace();
		}
	}

	/**
	 * 复制文件
	 * 
	 * @param source
	 *            来源文件地址
	 * @param to
	 *            目标文件地址
	 * @param deleteSource
	 *            是否删除文件
	 */
	public void FileBackup(String source, String to, boolean deleteSource) {
		try {
			FileInputStream input = new FileInputStream(source);
			FileOutputStream output = new FileOutputStream(to);

			int in = input.read();
			while (in != -1) {
				output.write(in);
				in = input.read();
			}

			input.close();
			output.close();
		} catch (IOException e) {
			System.out.println(e.toString());
		} finally {
			if (deleteSource) {
				File file = new File(source);
				file.delete();
			}
		}
	}

	/**
	 * 删除文件
	 */
	public void delete(File file) {
		if (file.isFile()) {
			file.delete();
			return;
		}
		if (file.isDirectory()) {
			File[] childFiles = file.listFiles();
			if (childFiles == null || childFiles.length == 0) {
				file.delete();
				return;
			}
			for (int i = 0; i < childFiles.length; i++) {
				delete(childFiles[i]);
			}
			file.delete();
		}
	}
	public Map<String, List<String>> GetFlieList(String filepath) {
		List<String> listfile  = new ArrayList<String>();
		List<String> listfilename  = new ArrayList<String>();
		Map<String, List<String>> maps = new HashMap<String, List<String>>();
		File file = new File(filepath);
		if (file.isFile()) {
			listfilename.add(file.getName());
			listfile.add(file.getPath());
		}
		if (file.isDirectory()) {
			File[] childFiles = file.listFiles();
			
			for (int i = 0; i < childFiles.length; i++) {
				delete(childFiles[i]);
				listfilename.add(childFiles[i].getName());
				listfile.add(childFiles[i].getPath()+".jpg");
			}
		}
		maps.put("filepath", listfile);
		maps.put("filenames", listfilename);
		return maps;
	}
	/**
	 * 判断文件是否存在和是否损坏
	 * 
	 * @param strFile
	 *            文件路径
	 * @return 存在且未损坏则返回true
	 */
	public boolean fileIsExists(String strFile) {
		try {
			File file = new File(strFile);
			// FileInputStream in = new FileInputStream(file);
			// byte[] b = new byte[1024];
			// InputStream stream = new BufferedInputStream(in);
			// stream.read(b);
			// stream.close();
			if (!file.exists()) {
				return false;
			} else {
				return read(strFile);
			}
		} catch (Exception e) {
			return false;
		}
	}
    /**
     * 检测文件是否损坏   采用读取文件流的方式
     * @param fileName 文件或者文件夹路径
     * @return 返回文件或者文件夹能否读取即文件是否损坏 能读取流则返回true 否则返回false 
     */
	public boolean read(String fileName) {
		File file = new File(fileName);
		if (file.isDirectory()) {
			File[] files = file.listFiles();
			for (File file1 : files) {
				if (!(readFile(file1.getAbsolutePath()))) {
					return false;
				}
			}
		} else {
			return readFile(fileName);
		}
		return true;
	}

	/**
	 *  检测文件是否损坏
	 * @param fileName 文件路径
	 * @return  损坏则返回false 未损坏则返回true
	 */
	private boolean readFile(String fileName) {
		FileInputStream inStream;
		try {
			inStream = new FileInputStream(fileName);
			ByteArrayOutputStream outStream = new ByteArrayOutputStream();
			byte[] buffer = new byte[1024];
			int len = 0;
			while ((len = inStream.read(buffer)) != -1) {
				outStream.write(buffer, 0, len); // /获取buffer数组中从0-len范围的数据，将数据读入内存
			}
			byte[] data = outStream.toByteArray();
			outStream.flush();//清除内存中的数据流
			inStream.close();
			outStream.close();
			return true;
		} catch (FileNotFoundException e) {
			return false;
		} catch (IOException e) {
			return false;
		}
	}
}
