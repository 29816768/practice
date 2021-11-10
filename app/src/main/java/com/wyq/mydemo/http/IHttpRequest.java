package com.wyq.mydemo.http;

import java.util.Map;

/**
 * Author: wangyongqi
 * Date: 2021/11/10 15:59
 * Description: post和get请求
 */
interface IHttpRequest {

    void post(String url, Map<String, Object> params, ICallback callback);

    void get(String url, ICallback callback);
}
