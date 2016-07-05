package functionitegretion.customfunction;

import java.util.Hashtable;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

/**
 * 编码转二维码图片并显示
 * 
 * @author TA
 */
public class CreateQRImage {
	/**
	 * 
	 * @param source
	 *            数据资源（编码）
	 * @param imageview
	 *            需要显示到的imageview
	 * @param width
	 *            、height 生成的二维码的宽高
	 */
	public void createQRImage(String source, ImageView imageview, int width,
			int height) {
		try {
			// 判断URL合法性
			if (source == null || "".equals(source) || source.length() < 1) {
				return;
			}
			Hashtable<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();
			hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.Q);
			hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
			// 图像数据转换，使用了矩阵转换
			BitMatrix bitMatrix = new QRCodeWriter().encode(source,
					BarcodeFormat.QR_CODE, width, height, hints);
			int[] pixels = new int[width * height];
			// 下面这里按照二维码的算法，逐个生成二维码的图片，
			// 两个for循环是图片横列扫描的结果
			for (int y = 0; y < height; y++) {
				for (int x = 0; x < width; x++) {
					if (bitMatrix.get(x, y)) {
						pixels[y * width + x] = 0xff000000;
					} else {
						pixels[y * height + x] = 0xffffffff;
					}
				}
			}
			// 生成二维码图片的格式，使用ARGB_8888
			Bitmap bitmap = Bitmap.createBitmap(width, height,
					Bitmap.Config.ARGB_8888);
			bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
			// 显示到一个ImageView上面
			imageview.setImageBitmap(bitmap);
		} catch (WriterException e) {
			e.printStackTrace();
		}
	}
}
