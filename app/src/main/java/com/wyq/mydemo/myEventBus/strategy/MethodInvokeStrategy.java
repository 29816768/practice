package com.wyq.mydemo.myEventBus.strategy;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;

import com.wyq.eventbus_annotation.MethodHandle;
import com.wyq.eventbus_annotation.SubscribedMethod;
import com.wyq.eventbus_annotation.Subscription;

import java.util.List;

/**
 * Author: wangyongqi
 * Date: 2022/3/21 19:06
 * Description:
 */
public abstract class MethodInvokeStrategy implements MethodHandle {
    private static final String TAG = "MethodInvokeStrategy";

    private static HandlerThread handlerThread = new HandlerThread("workThread");

    protected static Handler mainHandler;

    protected static Handler workHander;

    public MethodInvokeStrategy() {
        handlerThread.start();
        mainHandler = new Handler(Looper.getMainLooper());
        workHander = new Handler(handlerThread.getLooper());
    }

    public List<SubscribedMethod> getAllSubscribedMethods(Object subscriber) {
        return null;
    }

    public void invokeMethod(Subscription subscription, Object event) {

    }

}
