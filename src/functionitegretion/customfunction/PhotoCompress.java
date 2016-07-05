package functionitegretion.customfunction;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap.Config;

/**
 * 图片转换成bitmap压缩后输出
 * 
 * @author TA
 */
public class PhotoCompress {
	/**
	 * 根据对应的名字返回缩放后的位图
	 * 
	 * @param pathname
	 *            图片来源地址
	 * @param reqWidth
	 *            图片输出宽度尺寸
	 * @param reqHeight
	 *            图片输出高度尺寸
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
	 * 将bitmap图片压缩，返回bitmap Compress image by pixel, this will modify image
	 * width/height. TODO
	 */
	public Bitmap ratio(String imgPath, float pixelW, float pixelH) {
		BitmapFactory.Options newOpts = new BitmapFactory.Options();
		// 开始读入图片，此时把options.inJustDecodeBounds 设回true，即只读边不读内容
		newOpts.inJustDecodeBounds = true;
		newOpts.inPreferredConfig = Config.RGB_565;
		// Get bitmap info, but notice that bitmap is null now
		Bitmap bitmap = BitmapFactory.decodeFile(imgPath, newOpts);

		newOpts.inJustDecodeBounds = false;
		int w = newOpts.outWidth;
		int h = newOpts.outHeight;
		// 想要缩放的目标尺寸
		float hh = pixelH;// 设置高度为240f时，可以明显看到图片缩小了
		float ww = pixelW;// 设置宽度为120f，可以明显看到图片缩小了
		// 缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
		int be = 1;// be=1表示不缩放
		if (w > h && w > ww) {// 如果宽度大的话根据宽度固定大小缩放
			be = (int) (newOpts.outWidth / ww);
		} else if (w < h && h > hh) {// 如果高度高的话根据宽度固定大小缩放
			be = (int) (newOpts.outHeight / hh);
		}
		if (be <= 0)
			be = 1;
		newOpts.inSampleSize = be;// 设置缩放比例
		// 开始压缩图片，注意此时已经把options.inJustDecodeBounds 设回false了
		bitmap = BitmapFactory.decodeFile(imgPath, newOpts);
		return bitmap;
	}
}
