package com.wyq.mydemo;

import android.graphics.Bitmap;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.integration.webp.decoder.WebpDrawable;
import com.bumptech.glide.integration.webp.decoder.WebpDrawableTransformation;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;

import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.Date;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * Author: wangyongqi
 * Date: 2021/12/30 17:38
 * Description:
 */
public class TimeActivity extends AppCompatActivity {
    private TextView curTime;
    private ImageView image;
    private LooperHandler mHandler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //无title
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //全屏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //此两段代码必须设置在setContentView()方法之前
        setContentView(R.layout.activity_time);
        curTime = findViewById(R.id.curTime);
        image = findViewById(R.id.image);

        mHandler = new LooperHandler(this);
        mHandler.postDelayed(mRunnable, 1000);

        Transformation<Bitmap> circleCrop = new CenterCrop();

        Glide.with(this)
                //.asGif()
                //.load(R.drawable.time_bg)
                .load("https://hbimg.huabanimg.com/cab731a502484006bcd7e1e07ec5f56d91c67c88daded-mksk0n_fw658/format/webp")
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .optionalTransform(circleCrop)
                .optionalTransform(WebpDrawable.class, new WebpDrawableTransformation(circleCrop))
                .into(image);

    }

    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            Message msg = mHandler.obtainMessage(1);
            mHandler.sendMessage(msg);
        }
    };

    /**
     * handler 持有当前 Activity 的弱引用防止内存泄露
     */
    private static class LooperHandler extends Handler {
        WeakReference<TimeActivity> mWeakReference;
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        Transformation<Bitmap> circleCrop = new CenterCrop();

        LooperHandler(TimeActivity activity) {
            mWeakReference = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            TimeActivity activity = mWeakReference.get();
            if (msg.what == 1) {
                Date date = new Date(System.currentTimeMillis());
                Log.d("wyq", format.format(date));
                activity.curTime.setText(format.format(date));
                if (format.format(date).equals("00:00:00")) {
                    Glide.with(activity)
                            .load("https://hbimg.huabanimg.com/1e9d36ff4fa684c7656ecfcc4d199cb9a1c99b4b1a7cfb-F6WFSI_fw658/format/webp")
                            .optionalTransform(circleCrop)
                            .optionalTransform(WebpDrawable.class, new WebpDrawableTransformation(circleCrop))
                            .into(activity.image);
//                    Glide.with(activity)
//                            .asGif()
//                            .load(R.drawable.time_bg)
//                            //.diskCacheStrategy(DiskCacheStrategy.RESOURCE)
//                            .into(activity.image);
                    activity.curTime.setMovementMethod(ScrollingMovementMethod.getInstance());
                    activity.curTime.setText("新年快乐");
                    activity.mHandler.removeMessages(1);
                    activity.mRunnable = null;
                } else {
                    activity.mHandler.postDelayed(activity.mRunnable, 1000);
                }
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeMessages(1);
        mRunnable = null;
    }
}

