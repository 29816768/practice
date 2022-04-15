package com.wyq.mydemo.myEventBus.strategy;

import android.util.Log;

import com.wyq.eventbus_annotation.MethodHandle;
import com.wyq.eventbus_annotation.SubscribedMethod;
import com.wyq.eventbus_annotation.Subscription;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

/**
 * Author: wangyongqi
 * Date: 2022/3/22 20:10
 * Description:
 */
public class AptAnnotationInvoke extends MethodInvokeStrategy{

    private static final String TAG = "AptAnnotationInvoke";

    MethodHandle methodHandle;

    public AptAnnotationInvoke(MethodHandle methodHandle) {
        this.methodHandle = methodHandle;
    }

    @Override
    public List<SubscribedMethod> getAllSubscribedMethods(Object subscriber) {
        return methodHandle.getAllSubscribedMethods(subscriber.getClass());
    }

    @Override
    public void invokeMethod(Subscription subscription, final Object event) {
        final Object subscriber = subscription.getSubscriber();
        SubscribedMethod subscribedMethod = subscription.getSubscribedMethod();
        final String methodName = subscribedMethod.getMethodName();
        switch (subscribedMethod.getThreadMode()) {
            case POSTING:
                Log.d(TAG, "invokeMethod: ThreadMode=POSTING");
                invoke(methodName, subscriber, event);
                break;
            case MAIN:
                Log.d(TAG, "invokeMethod: ThreadMode=MAIN");
                mainHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        invoke(methodName, subscriber, event);
                    }
                });
                break;
            case BACKGROUND:
                Log.d(TAG, "invokeMethod: ThreadMode=BACKGROUND");
                workHander.post(new Runnable() {
                    @Override
                    public void run() {
                        invoke(methodName, subscriber, event);
                    }
                });
                break;
        }

    }

    private void invoke(String methodName, Object subscriber, Object event) {
        try {
            Method declaredMethod = subscriber.getClass().getDeclaredMethod(methodName, event.getClass());
            declaredMethod.invoke(subscriber, event);
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

}
