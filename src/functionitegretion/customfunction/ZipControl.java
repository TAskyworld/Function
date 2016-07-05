package functionitegretion.customfunction;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.zip.CRC32;
import java.util.zip.CheckedInputStream;
import java.util.zip.CheckedOutputStream;
import java.util.zip.Deflater;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

import android.util.Log;

/**
 * 
 * [һ�仰���ܼ���]<BR>
 * [������ϸ����]
 * 
 * @author zhouxin
 * @version [Android MTVClient C01, 2011-3-4]
 */
public class ZipControl {
	private static boolean isCreateSrcDir = false;// �Ƿ񴴽�ԴĿ¼
													// ������Ļ���Ҫ˵���¡������Ҫ����ԴĿ¼�Ļ�������������Ϊtrue����Ϊfalse;
	private static String TAG = "ZipControl";

	/**
	 * 
	 * [��ָ��·�����ļ���ѹ������]<BR>
	 * [������ϸ����]
	 * 
	 * @param src
	 *            ����ַ
	 * @param archive
	 *            ָ����ѹ���ļ��е�·��
	 * @param comment
	 *            ����
	 * @throws FileNotFoundException
	 *             �ļ�û���ҵ��쳣
	 * @throws IOException
	 *             IO�����쳣
	 */
	public void writeByApacheZipOutputStream(String[] src, String archive,
			String comment) throws FileNotFoundException, IOException {
		Log.e(TAG, "writeByApacheZipOutputStream");
		// ----ѹ���ļ���
		FileOutputStream f = new FileOutputStream(archive);
		// ʹ��ָ��У��ʹ��������
		CheckedOutputStream csum = new CheckedOutputStream(f, new CRC32());
		java.util.zip.ZipOutputStream zos = new java.util.zip.ZipOutputStream(
				csum);
		// ֧������
		BufferedOutputStream out = new BufferedOutputStream(zos);
		// ����ѹ����ע��
		zos.setComment(comment);
		// ����ѹ��
		zos.setMethod(java.util.zip.ZipOutputStream.DEFLATED);
		// ѹ������Ϊ��ǿѹ������ʱ��Ҫ���ö�һ��
		zos.setLevel(Deflater.BEST_COMPRESSION);
		// ���Ϊ�����ļ���ѹ���������޸�
		for (int i = 0; i < src.length; i++) {
			Log.e(TAG, "src[" + i + "] is " + src[i]);
			File srcFile = new File(src[i]);
			if (!srcFile.exists()
					|| (srcFile.isDirectory() && srcFile.list().length == 0)) {
				Log.e(TAG, "!srcFile.exists()");
				throw new FileNotFoundException(
						"File must exist and ZIP file must have at least one entry.");
			}
			String strSrcString = src[i];
			// ��ȡѹ��Դ���ڸ�Ŀ¼
			strSrcString = strSrcString.replaceAll("////", "/");
			String prefixDir = null;
			if (srcFile.isFile()) {
				prefixDir = strSrcString.substring(0,
						strSrcString.lastIndexOf("/") + 1);
			} else {
				prefixDir = (strSrcString.replaceAll("/$", "") + "/");
			}
			// ������Ǹ�Ŀ¼
			if (prefixDir.indexOf("/") != (prefixDir.length() - 1)
					&& isCreateSrcDir) {
				prefixDir = prefixDir.replaceAll("[^/]+/$", "");
			}
			// ��ʼѹ��
			writeRecursive(zos, out, srcFile, prefixDir);
		}

		out.close();
		// ע��У���Ҫ�����رպ��׼����һ��Ҫ���������رպ�ʹ��
		Log.e(TAG, "Checksum: " + csum.getChecksum().getValue());
		@SuppressWarnings("unused")
		BufferedInputStream bi;
	}

	/**
	 * 
	 * [* ʹ�� org.apache.tools.zip.ZipFile ��ѹ�ļ������� java ����е�
	 * java.util.zip.ZipFile ʹ�÷�ʽ��һ�µģ�ֻ�����������ñ��뷽ʽ�� �ӿڡ�
	 * 
	 * ע��apache û���ṩ ZipInputStream �࣬����ֻ��ʹ�����ṩ��ZipFile ����ȡѹ���ļ���]<BR>
	 * 
	 * @param archive
	 *            ѹ����·��
	 * @param decompressDir
	 *            ��ѹ·��
	 * @throws IOException
	 * @throws FileNotFoundException
	 * @throws ZipException
	 */
	public static void readByApacheZipFile(String archive, String decompressDir)
			throws IOException, FileNotFoundException, ZipException {
		Log.e(TAG, "readByApacheZipFile");
		BufferedInputStream bi;
		ZipFile zf = new ZipFile(archive);// ֧������
		Enumeration<? extends ZipEntry> e = zf.entries();
		while (e.hasMoreElements()) {
			java.util.zip.ZipEntry ze2 = e
					.nextElement();
			String entryName = ze2.getName();
			String path = decompressDir + "/" + entryName;
			if (ze2.isDirectory()) {
				Log.e(TAG, "���ڴ�����ѹĿ¼ - " + entryName);
				File decompressDirFile = new File(path);
				if (!decompressDirFile.exists()) {
					decompressDirFile.mkdirs();
				}
			} else {
				Log.e(TAG, "���ڴ�����ѹ�ļ� - " + entryName);
				String fileDir = path.substring(0, path.lastIndexOf("/"));
				File fileDirFile = new File(fileDir);
				if (!fileDirFile.exists()) {
					fileDirFile.mkdirs();
				}
				BufferedOutputStream bos = new BufferedOutputStream(
						new FileOutputStream(decompressDir + "/" + entryName));
				bi = new BufferedInputStream(zf.getInputStream(ze2));
				byte[] readContent = new byte[1024];
				int readCount = bi.read(readContent);
				while (readCount != -1) {
					bos.write(readContent, 0, readCount);
					readCount = bi.read(readContent);
				}
				bos.close();
			}
		}
		zf.close();
	}

	/**
	 * 
	 * [ʹ�� java api �е� ZipInputStream ���ѹ�ļ��������ѹ��ʱ������
	 * org.apache.tools.zip.ZipOutputStreamʱ�������� java ����е�
	 * java.util.zip.ZipOutputStreamʱ���÷�������ʹ�ã�ԭ����Ǳ��뷽 ʽ��һ�µ��£�����ʱ���������쳣��
	 * java.lang.IllegalArgumentException at
	 * java.util.zip.ZipInputStream.getUTF8String(ZipInputStream.java:290)
	 * 
	 * ��Ȼ�����ѹ����ʹ�õ���java����java.util.zip.ZipOutputStream ѹ�������ǲ���������ģ�������֧������ ]<BR>
	 * [������ϸ����]
	 * 
	 * @param archive
	 *            ѹ����·��
	 * @param decompressDir
	 *            ��ѹ·��
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static void readByZipInputStream(String archive, String decompressDir)
			throws FileNotFoundException, IOException {
		BufferedInputStream bi;
		// ----��ѹ�ļ�(ZIP�ļ��Ľ�ѹ��ʵ���Ͼ��Ǵ��������ж�ȡ����):
		Log.e(TAG, "��ʼ��ѹ���ļ�");
		FileInputStream fi = new FileInputStream(archive);
		CheckedInputStream csumi = new CheckedInputStream(fi, new CRC32());
		ZipInputStream in2 = new ZipInputStream(csumi);
		bi = new BufferedInputStream(in2);
		java.util.zip.ZipEntry ze;// ѹ���ļ���Ŀ
		// ����ѹ�����е��ļ���Ŀ
		while ((ze = in2.getNextEntry()) != null) {
			String entryName = ze.getName();
			if (ze.isDirectory()) {
				Log.e(TAG, "���ڴ�����ѹĿ¼ - " + entryName);
				File decompressDirFile = new File(decompressDir + "/"
						+ entryName);
				if (!decompressDirFile.exists()) {
					decompressDirFile.mkdirs();
				}
			} else {
				Log.e(TAG, "���ڴ�����ѹ�ļ� - " + entryName);
				BufferedOutputStream bos = new BufferedOutputStream(
						new FileOutputStream(
								decompressDir
										+ "/"
										+ entryName.substring(
												entryName.lastIndexOf("//"),
												entryName.length()
														- (entryName
																.lastIndexOf("//") - 2))));
				byte[] buffer = new byte[1024];
				int readCount = bi.read(buffer);
				while (readCount != -1) {
					bos.write(buffer, 0, readCount);
					readCount = bi.read(buffer);
				}
				bos.close();
			}
		}
		bi.close();
		Log.e(TAG, "Checksum: " + csumi.getChecksum().getValue());
	}

	/**
	 * 
	 * [�ݹ�ѹ��
	 * 
	 * ʹ�� org.apache.tools.zip.ZipOutputStream �����ѹ�������ĺô�����֧������·���� ��Java����е�
	 * java.util.zip.ZipOutputStream ѹ�������ļ���ʱѹ������������롣 ʹ�� apache �е�������� java
	 * ����е��÷���һ�µģ�ֻ�������ñ��뷽ʽ�ˡ�]<BR>
	 * [������ϸ����]
	 * 
	 * @param zos
	 * @param bo
	 * @param srcFile
	 * @param prefixDir
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	private static void writeRecursive(java.util.zip.ZipOutputStream zos,
			BufferedOutputStream bo, File srcFile, String prefixDir)
			throws IOException, FileNotFoundException {
		Log.e(TAG, "writeRecursive");
		java.util.zip.ZipEntry zipEntry;
		String filePath = srcFile.getAbsolutePath().replaceAll("////", "/")
				.replaceAll("//", "/");
		if (srcFile.isDirectory()) {
			filePath = filePath.replaceAll("/$", "") + "/";
		}
		String entryName = filePath.replace(prefixDir, "").replaceAll("/$", "");
		if (srcFile.isDirectory()) {
			if (!"".equals(entryName)) {
				Log.e(TAG, "���ڴ���Ŀ¼ - " + srcFile.getAbsolutePath()
						+ " entryName=" + entryName);
				// �����Ŀ¼������Ҫ��дĿ¼������� /
				zipEntry = new java.util.zip.ZipEntry(entryName + "/");
				zos.putNextEntry(zipEntry);
			}
			File srcFiles[] = srcFile.listFiles();
			for (int i = 0; i < srcFiles.length; i++) {
				writeRecursive(zos, bo, srcFiles[i], prefixDir);
			}
		} else {
			Log.e(TAG, "����д�ļ� - " + srcFile.getAbsolutePath() + " entryName="
					+ entryName);
			BufferedInputStream bi = new BufferedInputStream(
					new FileInputStream(srcFile));
			// ��ʼд���µ�ZIP�ļ���Ŀ��������λ����Ŀ���ݵĿ�ʼ��
			zipEntry = new java.util.zip.ZipEntry(entryName);
			zos.putNextEntry(zipEntry);
			byte[] buffer = new byte[1024];
			int readCount = bi.read(buffer);
			while (readCount != -1) {
				bo.write(buffer, 0, readCount);
				readCount = bi.read(buffer);
			}
			// ע����ʹ�û�����дѹ���ļ�ʱ��һ���������һ��Ҫˢ��һ�ѣ���
			// Ȼ�����е����ݾͻ���뵽������Ŀ��ȥ��
			bo.flush();
			// �ļ������ر�
			bi.close();
		}
	}
}
