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
 * ����ת��ά��ͼƬ����ʾ
 * 
 * @author TA
 */
public class CreateQRImage {
	/**
	 * 
	 * @param source
	 *            ������Դ�����룩
	 * @param imageview
	 *            ��Ҫ��ʾ����imageview
	 * @param width
	 *            ��height ���ɵĶ�ά��Ŀ��
	 */
	public void createQRImage(String source, ImageView imageview, int width,
			int height) {
		try {
			// �ж�URL�Ϸ���
			if (source == null || "".equals(source) || source.length() < 1) {
				return;
			}
			Hashtable<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();
			hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.Q);
			hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
			// ͼ������ת����ʹ���˾���ת��
			BitMatrix bitMatrix = new QRCodeWriter().encode(source,
					BarcodeFormat.QR_CODE, width, height, hints);
			int[] pixels = new int[width * height];
			// �������ﰴ�ն�ά����㷨��������ɶ�ά���ͼƬ��
			// ����forѭ����ͼƬ����ɨ��Ľ��
			for (int y = 0; y < height; y++) {
				for (int x = 0; x < width; x++) {
					if (bitMatrix.get(x, y)) {
						pixels[y * width + x] = 0xff000000;
					} else {
						pixels[y * height + x] = 0xffffffff;
					}
				}
			}
			// ���ɶ�ά��ͼƬ�ĸ�ʽ��ʹ��ARGB_8888
			Bitmap bitmap = Bitmap.createBitmap(width, height,
					Bitmap.Config.ARGB_8888);
			bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
			// ��ʾ��һ��ImageView����
			imageview.setImageBitmap(bitmap);
		} catch (WriterException e) {
			e.printStackTrace();
		}
	}
}
