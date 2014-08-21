package dev.jugo.smartwidget.image;

import dev.jugo.smartwidget.common.Logs;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Rect;

public abstract class BitmapHandler
{
	public static Bitmap makeMosaic(Bitmap bitmap, Rect targetRect, int blockSize) throws OutOfMemoryError
	{

		if (bitmap == null || bitmap.getWidth() == 0 || bitmap.getHeight() == 0 || bitmap.isRecycled())
		{
			if (null != bitmap)
			{
				Logs.showTrace("Bad Bitmap width=" + bitmap.getWidth() + " height=" + bitmap.getHeight()
						+ " is recycle=" + bitmap.isRecycled());
			}
			//throw new RuntimeException("bad bitmap to add mosaic");
			return bitmap;
		}

		if (0 >= blockSize)
		{
			return bitmap;
		}

		if (targetRect == null)
		{
			targetRect = new Rect();
		}

		int bw = bitmap.getWidth();
		int bh = bitmap.getHeight();

		if (targetRect.isEmpty())
		{
			targetRect.set(0, 0, bw, bh);
		}

		int rectW = targetRect.width();
		int rectH = targetRect.height();

		int[] bitmapPxs = new int[bw * bh];

		bitmap.getPixels(bitmapPxs, 0, bw, 0, 0, bw, bh);

		int rowCount = (int) Math.ceil((float) rectH / blockSize);
		int columnCount = (int) Math.ceil((float) rectW / blockSize);
		int maxX = bw;
		int maxY = bh;

		for (int r = 0; r < rowCount; r++)
		{ // row loop
			for (int c = 0; c < columnCount; c++)
			{// column loop
				int startX = targetRect.left + c * blockSize + 1;
				int startY = targetRect.top + r * blockSize + 1;
				dimBlock(bitmapPxs, startX, startY, blockSize, maxX, maxY);
			}
		}

		return Bitmap.createBitmap(bitmapPxs, bw, bh, Config.ARGB_8888);
	}

	private static void dimBlock(int[] pxs, int startX, int startY, int blockSize, int maxX, int maxY)
	{
		int stopX = startX + blockSize - 1;
		int stopY = startY + blockSize - 1;

		if (stopX > maxX)
		{
			stopX = maxX;
		}

		if (stopY > maxY)
		{
			stopY = maxY;
		}

		int sampleColorX = startX + blockSize / 2;
		int sampleColorY = startY + blockSize / 2;

		if (sampleColorX > maxX)
		{
			sampleColorX = maxX;
		}

		if (sampleColorY > maxY)
		{
			sampleColorY = maxY;
		}

		int colorLinePosition = (sampleColorY - 1) * maxX;

		int sampleColor = pxs[colorLinePosition + sampleColorX - 1];// 像素从1开始，但是数组层0开始

		for (int y = startY; y <= stopY; y++)
		{
			int p = (y - 1) * maxX;
			for (int x = startX; x <= stopX; x++)
			{
				// 像素从1开始，但是数组层0开始
				pxs[p + x - 1] = sampleColor;
			}
		}
	}

	public static Bitmap AdjustTobMosaic(Bitmap bitmap, int effectWidth)
	{
		int nAlpha = 0;
		//Bitmap bitmap2 = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Config.ARGB_8888);
		Bitmap bitmap2 = bitmap.copy(Config.ARGB_8888, true);
		// 差異最多的就是以照一定範圍取樣 玩之後直接去下一個範圍
		for (int heightOfffset = 0; heightOfffset < bitmap.getHeight(); heightOfffset += effectWidth)
		{
			for (int widthOffset = 0; widthOffset < bitmap.getWidth(); widthOffset += effectWidth)
			{
				int avgR = 0, avgG = 0, avgB = 0;
				int blurPixelCount = 0;

				for (int x = widthOffset; (x < widthOffset + effectWidth && x < bitmap.getWidth()); ++x)
				{
					for (int y = heightOfffset; (y < heightOfffset + effectWidth && y < bitmap.getHeight()); ++y)
					{
						int pixel = bitmap.getPixel(x, y);
						nAlpha = Color.alpha(pixel);
						avgR += Color.red(pixel);
						avgG += Color.green(pixel);
						avgB += Color.blue(pixel);

						++blurPixelCount;
					}
				}

				// 計算範圍平均
				avgR = avgR / blurPixelCount;
				avgG = avgG / blurPixelCount;
				avgB = avgB / blurPixelCount;

				// 所有範圍內都設定此值
				for (int x = widthOffset; (x < widthOffset + effectWidth && x < bitmap.getWidth()); x++)
				{
					for (int y = heightOfffset; (y < heightOfffset + effectWidth && y < bitmap.getHeight()); y++)
					{
						//	int pixelAlpha = Color.alpha(0);
						int red = Color.red(avgR);
						int green = Color.green(avgG);
						int blue = Color.blue(avgB);
						int new_pixel = Color.argb(nAlpha, red, green, blue);
						Logs.showTrace("a=" + nAlpha + " r=" + red + " g=" + green + " b=" + blue + " pixel="
								+ new_pixel + " ####################");

						bitmap2.setPixel(x, y, new_pixel);

					}
				}

			}
		}

		return bitmap2;
	}

	public static void releaseBitmap(Bitmap bitmap)
	{
		if (null != bitmap)
		{
			if (!bitmap.isRecycled())
			{
				bitmap.recycle();
			}
		}
		bitmap = null;
	}

	public static Bitmap scaleBitmap(Bitmap bmpSrc, int quality)
	{
		int bitmapWidth = bmpSrc.getWidth();
		int bitmapHeight = bmpSrc.getHeight();
		// 缩放图片的尺寸
		float scaleWidth = (float) quality / bitmapWidth;
		float scaleHeight = (float) quality / bitmapHeight;
		Matrix matrix = new Matrix();
		matrix.postScale(scaleWidth, scaleHeight);
		// 产生缩放后的Bitmap对象
		Bitmap resizeBitmap = Bitmap.createBitmap(bmpSrc, 0, 0, bitmapWidth, bitmapHeight, matrix, false);

		if (!bmpSrc.isRecycled())
		{
			bmpSrc.recycle();//记得释放资源，否则会内存溢出
		}
		return resizeBitmap;
	}
}
