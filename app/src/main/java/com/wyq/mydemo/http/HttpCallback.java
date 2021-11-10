package com.wyq.mydemo.http;

import com.google.gson.Gson;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Author: wangyongqi
 * Date: 2021/11/10 15:41
 * Description: OKHttp JSON字符串 String字符串 --->  用户指定的 JavaBean
 */
public abstract class HttpCallback<T> implements ICallback {

    public abstract void onSuccess(T objResult);

    @Override
    public void onSuccess(String result) {
        // 1.得到调用者用什么样的javaBean接收数据
        Class<?> clz = analysisClassInfo(this);
        // 2.把String result转成对应的javaBean
        T objResult = (T) new Gson().fromJson(result,clz);
        // 3.objResult交给程序员
        onSuccess(objResult);
    }

    @Override
    public void onFailure(String e) {

    }


    private Class<?> analysisClassInfo(Object object) {
        // getGenericSuperclass();
        // 可以得到包含原始类型，参数化类型，数组，类型变量，基本数据类型
        Type getType = object.getClass().getGenericSuperclass();
        Type[] params = ((ParameterizedType) getType).getActualTypeArguments();
        return (Class<?>) params[0]; // <>里面只有一个，所以取0号元素即可
    }
}
