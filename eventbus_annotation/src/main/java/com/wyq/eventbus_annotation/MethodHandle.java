package com.wyq.eventbus_annotation;

import java.util.List;

/**
 * Author: wangyongqi
 * Date: 2022/3/21 19:40
 * Description:
 */
public interface MethodHandle {
    public List<SubscribedMethod> getAllSubscribedMethods(Object subscriber);

    public void invokeMethod(Subscription subscription, Object event);
}