package com.wyq.mydemo.util;

import android.util.Log;
import android.view.Choreographer;

/**
 * Author: wangyongqi
 * Date: 2022/3/14 11:04
 * Description:
 */
public class FPSFrameCallback implements Choreographer.FrameCallback {

    private static final String TAG = "FPS_TEST";
    private long mLastFrameTimeNanos = 0;
    private long mFrameIntervalNanos;

    public FPSFrameCallback(long lastFrameTimeNanos) {
        mLastFrameTimeNanos = lastFrameTimeNanos;
        mFrameIntervalNanos = (long) (1000000000 / 60.0);
    }

    @Override
    public void doFrame(long frameTimeNanos) {
        if (mLastFrameTimeNanos == 0) {
            mLastFrameTimeNanos = frameTimeNanos;
        }
        final long jitterNanos = frameTimeNanos - mLastFrameTimeNanos;
        if (jitterNanos >= mFrameIntervalNanos) {
            final long skippedFrames = jitterNanos / mFrameIntervalNanos;
            if (skippedFrames > 30) {
                //丢帧30以上打印日志
                Log.i(TAG, "Skipped " + skippedFrames + " frames!  "
                        + "The application may be doing too much work on its main thread.");
            }
        }
        mLastFrameTimeNanos = frameTimeNanos;
        Choreographer.getInstance().postFrameCallback(this);
    }
}
