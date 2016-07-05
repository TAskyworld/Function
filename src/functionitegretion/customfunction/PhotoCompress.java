package functionitegretion.customfunction;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap.Config;

/**
 * ͼƬת����bitmapѹ�������
 * 
 * @author TA
 */
public class PhotoCompress {
	/**
	 * ���ݶ�Ӧ�����ַ������ź��λͼ
	 * 
	 * @param pathname
	 *            ͼƬ��Դ��ַ
	 * @param reqWidth
	 *            ͼƬ�����ȳߴ�
	 * @param reqHeight
	 *            ͼƬ����߶ȳߴ�
	 * @return
	 */
	public Bitmap getLoadBitmap(String pathname, int reqWidth, int reqHeight) {
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(pathname, options);
		// int reqWidth;
		// int reqHeight;
		// reqWidth =getWindowManager().getDefaultDisplay().getWidth();
		// reqHeight = getWindowManager().getDefaultDisplay().getHeight();
		Bitmap bitmap2 = ratio(pathname, reqWidth, reqHeight);
		return bitmap2;
	}

	/**
	 * ��bitmapͼƬѹ��������bitmap Compress image by pixel, this will modify image
	 * width/height. TODO
	 */
	public Bitmap ratio(String imgPath, float pixelW, float pixelH) {
		BitmapFactory.Options newOpts = new BitmapFactory.Options();
		// ��ʼ����ͼƬ����ʱ��options.inJustDecodeBounds ���true����ֻ���߲�������
		newOpts.inJustDecodeBounds = true;
		newOpts.inPreferredConfig = Config.RGB_565;
		// Get bitmap info, but notice that bitmap is null now
		Bitmap bitmap = BitmapFactory.decodeFile(imgPath, newOpts);

		newOpts.inJustDecodeBounds = false;
		int w = newOpts.outWidth;
		int h = newOpts.outHeight;
		// ��Ҫ���ŵ�Ŀ��ߴ�
		float hh = pixelH;// ���ø߶�Ϊ240fʱ���������Կ���ͼƬ��С��
		float ww = pixelW;// ���ÿ��Ϊ120f���������Կ���ͼƬ��С��
		// ���űȡ������ǹ̶��������ţ�ֻ�ø߻��߿�����һ�����ݽ��м��㼴��
		int be = 1;// be=1��ʾ������
		if (w > h && w > ww) {// �����ȴ�Ļ����ݿ�ȹ̶���С����
			be = (int) (newOpts.outWidth / ww);
		} else if (w < h && h > hh) {// ����߶ȸߵĻ����ݿ�ȹ̶���С����
			be = (int) (newOpts.outHeight / hh);
		}
		if (be <= 0)
			be = 1;
		newOpts.inSampleSize = be;// �������ű���
		// ��ʼѹ��ͼƬ��ע���ʱ�Ѿ���options.inJustDecodeBounds ���false��
		bitmap = BitmapFactory.decodeFile(imgPath, newOpts);
		return bitmap;
	}
}
