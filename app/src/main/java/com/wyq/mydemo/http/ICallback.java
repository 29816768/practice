package com.wyq.mydemo.http;

/**
 * Author: wangyongqi
 * Date: 2021/11/10 15:38
 * Description: 网络请求回调
 */
interface ICallback {
    void onSuccess(String result); //请求回来的字符串
    void onFailure(String e);
}
