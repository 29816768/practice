package com.wyq.mydemo.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapRegionDecoder;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;

import java.io.IOException;
import java.io.InputStream;

import androidx.annotation.Nullable;

/**
 * Author: wangyongqi
 * Date: 2022/3/22 14:41
 * Description: 加载大图
 */
public class LargeImageView extends View implements GestureDetector.OnGestureListener {

    private static final String TAG = "LargeImageView";
    private BitmapRegionDecoder mDecoder;

    //绘制区域
    private volatile Rect mRect = new Rect();
    private int mScaledTouchSlop;

    //上次滑动的坐标
    private int mLastX = 0;
    private int mLastY = 0;

    //图片的宽度和高度
    private int mImageWidth, mImageHeight;

    //手势控制器
    private GestureDetector mGestureDetector;
    private BitmapFactory.Options options;


    public LargeImageView(Context context) {
       this(context, null);
    }

    public LargeImageView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public LargeImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        //设置显示图片的参数，如果对图片质量有要求，就选择ARGB_8888模式
        options = new BitmapFactory.Options();
        //options.inSampleSize = 2;
        options.inPreferredConfig = Bitmap.Config.RGB_565;

        mScaledTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
        Log.d(TAG, "sts:" + mScaledTouchSlop);
        //初始化手势控制器
        mGestureDetector = new GestureDetector(context, this);
        InputStream is = null;
        try {
            is = context.getResources().getAssets().open("timg.jpg");
            //初始化BitmapRegionDecode，并用它来显示图片
            //如果在decodeStream之前使用is，会导致出错
            // 此时流的起始位置已经被移动过了，需要调用is.reset()来重置，然后再decodeStream(imgInputStream, null, options)
            mDecoder = BitmapRegionDecoder.newInstance(is, false);
            BitmapFactory.Options tmpOptions = new BitmapFactory.Options();
            // 设置为true则只获取图片的宽高等信息，不加载进内存
            tmpOptions.inJustDecodeBounds = true;
            is.reset();

            BitmapFactory.decodeStream(is, null, tmpOptions);
            mImageWidth = tmpOptions.outWidth;
            mImageHeight = tmpOptions.outHeight;

            Log.d(TAG, "width:" + mImageWidth + ",height:" + mImageHeight);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //把触摸事件交给手势控制器处理
        return mGestureDetector.onTouchEvent(event);
    }

    @Override
    public boolean onDown(MotionEvent e) {
        mLastX = (int) e.getRawX();
        mLastY = (int) e.getRawY();
        return true;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        int x = (int) e2.getRawX();
        int y = (int) e2.getRawY();
        move(x, y);
        return true;
    }

    private void move(int x, int y) {
        boolean isInvalidate = false;

        int deltaX = x - mLastX;
        int deltaY = y - mLastY;
        Log.d(TAG, "move, deltaX:" + deltaX + " deltaY:" + deltaY);
        //如果图片宽度大于屏幕宽度
        if (mImageWidth > getWidth()) {
            mRect.offset(-deltaX, 0);
            //检查是否到达图片最右端
            if (mRect.right > mImageWidth) {
                mRect.right = mImageWidth;
                mRect.left = mImageWidth - getWidth();
            }
            //检查左端
            if (mRect.left < 0) {
                mRect.left = 0;
                mRect.right = getWidth();
            }
            isInvalidate = true;
        }
        //如果图片高度大于屏幕高度
        if (mImageHeight > getHeight()) {
            mRect.offset(0, -deltaY);
            //是否到达底部
            if (mRect.bottom > mImageHeight) {
                mRect.bottom = mImageHeight;
                mRect.top = mImageHeight - getHeight();
            }
            if (mRect.top < 0) {
                mRect.top = 0;
                mRect.bottom = getHeight();
            }
            isInvalidate = true;
        }
        if (isInvalidate) {
            invalidate();
        }

        mLastX = x;
        mLastY = y;
    }

    @Override
    public void onLongPress(MotionEvent e) {
        mLastX = (int) e.getRawX();
        mLastY = (int) e.getRawY();
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        int x = (int) e2.getRawX();
        int y = (int) e2.getRawY();
        move(x, y);
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //显示图片
        Bitmap bitmap = mDecoder.decodeRegion(mRect, options);
        canvas.drawBitmap(bitmap, 0, 0, null);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = getMeasuredWidth();
        int height = getMeasuredHeight();

        int imageWidth = mImageWidth;
        int imageHeight = mImageHeight;

        Log.d(TAG, "measure: width= " + width + " height= " + height +
                " imageWidth= " + imageWidth + " imageHeight= " + imageHeight);

        //默认显示图片的中心区域
        mRect.left = imageWidth / 2 - width / 2;
        mRect.top = imageHeight / 2 - height / 2;
        mRect.right = mRect.left + width;
        mRect.bottom = mRect.top + height;
    }
}
