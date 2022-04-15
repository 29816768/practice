package com.wyq.mydemo.myEventBus;

import com.wyq.eventbus_annotation.MethodHandle;
import com.wyq.eventbus_annotation.SubscribedMethod;
import com.wyq.eventbus_annotation.Subscription;
import com.wyq.eventbus_annotation.ThreadMode;
import com.wyq.mydemo.MainActivity;
import com.wyq.mydemo.bean.ViewEvent;
import com.wyq.mydemo.bean.WorkEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author: wangyongqi
 * Date: 2022/3/22 20:03
 * Description:
 */
public class AptMethodFinderTemplate implements MethodHandle {

    private static final Map<Object, List<SubscribedMethod>> aptMap = new HashMap<>();

    static {
        aptMap.put(MainActivity.class, findMethodsInMainActivity());
    }


    @Override
    public List<SubscribedMethod> getAllSubscribedMethods(Object subscriber) {
        return aptMap.get(subscriber);
    }

    @Override
    public void invokeMethod(Subscription subscription, Object event) {

    }

    private static List<SubscribedMethod> findMethodsInMainActivity(){
        List<SubscribedMethod> subscribedMethods = new ArrayList<>();
        subscribedMethods.add(new SubscribedMethod(MainActivity.class, WorkEvent.class, ThreadMode.POSTING, 0, "onEvent"));
        subscribedMethods.add(new SubscribedMethod(MainActivity.class, ViewEvent.class, ThreadMode.MAIN, 0, "handleView"));
        return subscribedMethods;
    }
}
