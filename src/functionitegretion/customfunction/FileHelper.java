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
	 * ���������ļ�������
	 * 
	 * @param oldPath
	 *            String ԭ�ļ�·�� �磺c:/fqf
	 * @param newPath
	 *            String ���ƺ�·�� �磺f:/fqf/ff
	 * @return boolean
	 */
	public void copyFolder(String oldPath, String newPath) {

		try {
			(new File(newPath)).mkdirs(); // ����ļ��в����� �������ļ���
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
				if (temp.isDirectory()) {// ��������ļ���
					copyFolder(oldPath + "/" + file[i], newPath + "/" + file[i]);
				}
			}
		} catch (Exception e) {
			// Toast.makeText(context, "���������ļ������ݲ�������",
			// Toast.LENGTH_LONG).show();
			System.out.println("���������ļ������ݲ�������");
			e.printStackTrace();
		}
	}

	/**
	 * �����ļ�
	 * 
	 * @param source
	 *            ��Դ�ļ���ַ
	 * @param to
	 *            Ŀ���ļ���ַ
	 * @param deleteSource
	 *            �Ƿ�ɾ���ļ�
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
	 * ɾ���ļ�
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
	 * �ж��ļ��Ƿ���ں��Ƿ���
	 * 
	 * @param strFile
	 *            �ļ�·��
	 * @return ������δ���򷵻�true
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
     * ����ļ��Ƿ���   ���ö�ȡ�ļ����ķ�ʽ
     * @param fileName �ļ������ļ���·��
     * @return �����ļ������ļ����ܷ��ȡ���ļ��Ƿ��� �ܶ�ȡ���򷵻�true ���򷵻�false 
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
	 *  ����ļ��Ƿ���
	 * @param fileName �ļ�·��
	 * @return  ���򷵻�false δ���򷵻�true
	 */
	private boolean readFile(String fileName) {
		FileInputStream inStream;
		try {
			inStream = new FileInputStream(fileName);
			ByteArrayOutputStream outStream = new ByteArrayOutputStream();
			byte[] buffer = new byte[1024];
			int len = 0;
			while ((len = inStream.read(buffer)) != -1) {
				outStream.write(buffer, 0, len); // /��ȡbuffer�����д�0-len��Χ�����ݣ������ݶ����ڴ�
			}
			byte[] data = outStream.toByteArray();
			outStream.flush();//����ڴ��е�������
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
