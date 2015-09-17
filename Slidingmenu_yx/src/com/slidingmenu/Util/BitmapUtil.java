package com.slidingmenu.Util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

/**
 * ����������Bitmap�ӹ���������
 * @author android_ls
 *
 */
public class BitmapUtil {

    /**
     * ��ͼƬ���Բ�ǣ�����һ��
     * @param bitmap Bitmap
     * @param pixels Բ�ǵĻ���
     * @return Բ��ͼƬ
     */
    public static Bitmap drawRoundBitmap(Bitmap bitmap, float pixels) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        // paint.setColor()�Ĳ�����������ΪColor.TRANSPARENT�⣬��������д
        paint.setColor(Color.RED);
        canvas.drawRoundRect(rectF, pixels, pixels, paint);

        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        return output;
    }

    /**
     * ��ͼƬ���Բ�ǣ���������
     * @param bitmap Bitmap
     * @param pixels Բ�ǵĻ���
     * @return Բ��ͼƬ
     */
    public static Bitmap drawRoundCorner(Bitmap bitmap, float pixels) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        RectF outerRect = new RectF(0, 0, bitmap.getWidth(), bitmap.getHeight());
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        // paint.setColor()�Ĳ�����������ΪColor.TRANSPARENT�⣬��������д
        paint.setColor(Color.WHITE);
        canvas.drawRoundRect(outerRect, pixels, pixels, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        Drawable drawable = new BitmapDrawable(bitmap);
        drawable.setBounds(0, 0, bitmap.getWidth(), bitmap.getHeight());
        canvas.saveLayer(outerRect, paint, Canvas.ALL_SAVE_FLAG);
        drawable.draw(canvas);
        canvas.restore();

        return output;
    }

    /**
     * ��Bitmap��������ѹ��
     * @param bitmap Bitmap
     * @return ByteArrayInputStream
     */
    public static Bitmap compressBitmap(Bitmap bitmap) {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        // ͼƬ����Ĭ��ֵΪ100����ʾ��ѹ��
        int quality = 100;
        // PNG������ģ���������������á���ˣ���������ΪJPEG
        bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outStream);

        // �ж�ѹ����ͼƬ�Ĵ�С�Ƿ����100KB�����������ѹ��
        while (outStream.toByteArray().length / 1024 > 100) {
            outStream.reset();

            // ѹ��quality%����ѹ��������ݴ�ŵ�baos��
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outStream);
            quality -= 10;
        }

        System.out.println("quality = " + quality);

        byte[] data = outStream.toByteArray();
        return BitmapFactory.decodeByteArray(data, 0, data.length);
    }

    /**
     * ����ָ����ѹ����������ú��ʵ�Bitmap������һ��
     * @param file File
     * @param width ָ���Ŀ��
     * @param height ָ���ĸ߶�
     * @return Bitmap
     */
    public static Bitmap decodeStream(File file, int width, int height) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(file.getAbsolutePath(), options);

        int w = options.outWidth;
        int h = options.outHeight;

        // �ӷ������˻�ȡ��ͼƬ��СΪ��80x120
        // ������Ҫ��ͼƬ��СΪ��40x40
        // ���űȣ�120/40 = 3��Ҳ����˵����Ҫ��ͼƬ��СΪԭͼ��1/3

        // ���űȡ������ǹ̶��������ţ�ֻ�ø߻��߿�����һ�����ݽ��м��㼴��
        int ratio = 1; // Ĭ��Ϊ������
        if (w >= h && w > width) {
            ratio = (int) (w / width);
        } else if (w < h && h > height) {
            ratio = (int) (h / height);
        }

        if (ratio <= 0) {
            ratio = 1;
        }

        System.out.println("ͼƬ�����ű���ֵratio = " + ratio);

        options = new BitmapFactory.Options();
        options.inJustDecodeBounds = false;
        // ����ֵinSampleSize��ʾ����ͼ��СΪԭʼͼƬ��С�ļ���֮һ����������ֵΪ2��
        // ��ȡ��������ͼ�Ŀ�͸߶���ԭʼͼƬ��1/2��ͼƬ��С��Ϊԭʼ��С��1/4��
        options.inSampleSize = ratio;

        return BitmapFactory.decodeFile(file.getAbsolutePath(), options);
    }

    /**
     * ����ָ����ѹ����������ú��ʵ�Bitmap����������
     * @param inStream InputStream
     * @param width ָ���Ŀ��
     * @param height ָ���ĸ߶�
     * @return Bitmap
     * @throws IOException
     */
    public static Bitmap decodeStream(InputStream inStream, int width, int height) throws IOException {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;

        // ����������ȡ����
        byte[] data = StreamTool.read(inStream);
        BitmapFactory.decodeByteArray(data, 0, data.length, options);

        int w = options.outWidth;
        int h = options.outHeight;

        // �ӷ������˻�ȡ��ͼƬ��СΪ��80x120
        // ������Ҫ��ͼƬ��СΪ��40x40
        // ���űȣ�120/40 = 3��Ҳ����˵����Ҫ��ͼƬ��СΪԭͼ��1/3

        // ���űȡ������ǹ̶��������ţ�ֻ�ø߻��߿�����һ�����ݽ��м��㼴��
        int ratio = 1; // Ĭ��Ϊ������
        if (w >= h && w > width) {
            ratio = (int) (w / width);
        } else if (w < h && h > height) {
            ratio = (int) (h / height);
        }

        if (ratio <= 0) {
            ratio = 1;
        }

        System.out.println("ͼƬ�����ű���ֵratio = " + ratio);

        options.inJustDecodeBounds = false;
        // ����ֵinSampleSize��ʾ����ͼ��СΪԭʼͼƬ��С�ļ���֮һ����������ֵΪ2��
        // ��ȡ��������ͼ�Ŀ�͸߶���ԭʼͼƬ��1/2��ͼƬ��С��Ϊԭʼ��С��1/4��
        options.inSampleSize = ratio;
        return BitmapFactory.decodeByteArray(data, 0, data.length);
    }

    /**
     * ����ָ����ѹ����������ú��ʵ�Bitmap�������ķ����������ڲ��ԣ�
     * @param inStream
     * @param width
     * @param height
     * @return
     * @throws IOException
     */
    public static Bitmap decodeStreamError(InputStream inStream, int width, int height) throws IOException {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(inStream, null, options);

        int w = options.outWidth;
        int h = options.outHeight;

        // �ӷ������˻�ȡ��ͼƬ��СΪ��80x120
        // ������Ҫ��ͼƬ��СΪ��40x40
        // ���űȣ�120/40 = 3��Ҳ����˵����Ҫ��ͼƬ��СΪԭͼ��1/3

        // ���űȡ������ǹ̶��������ţ�ֻ�ø߻��߿�����һ�����ݽ��м��㼴��
        int ratio = 1; // Ĭ��Ϊ������
        if (w >= h && w > width) {
            ratio = (int) (w / width);
        } else if (w < h && h > height) {
            ratio = (int) (h / height);
        }

        if (ratio <= 0) {
            ratio = 1;
        }

        System.out.println("ͼƬ�����ű���ֵratio = " + ratio);

        options.inJustDecodeBounds = false;
        // ����ֵinSampleSize��ʾ����ͼ��СΪԭʼͼƬ��С�ļ���֮һ����������ֵΪ2��
        // ��ȡ��������ͼ�Ŀ�͸߶���ԭʼͼƬ��1/2��ͼƬ��С��Ϊԭʼ��С��1/4��
        options.inSampleSize = ratio;

        return BitmapFactory.decodeStream(inStream, null, options);
    }
    
public static Bitmap drawableToBitmap(Drawable drawable) {
        
        Bitmap bitmap = Bitmap
                        .createBitmap(
                                        drawable.getIntrinsicWidth(),
                                        drawable.getIntrinsicHeight(),
                                        drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
                                                        : Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        //canvas.setBitmap(bitmap);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        drawable.draw(canvas);
        return bitmap;
}

}
