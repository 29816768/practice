package com.wyq.mydemo.http;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

/**
 * Author: wangyongqi
 * Date: 2021/11/10 15:58
 * Description:
 */
public class HttpHelper implements IHttpRequest {

    private static HttpHelper instance;

    private HttpHelper() {
    }

    public static HttpHelper getInstance() {
        if (instance == null) {
            synchronized (HttpHelper.class) {
                if (instance == null) {
                    instance = new HttpHelper();
                }
            }
        }
        return instance;
    }

    // 依赖倒置原则  面向高层 不面向具体细节
    private static IHttpRequest mHttpRequest = null;

    public static void init(IHttpRequest httpRequest) {
        mHttpRequest = httpRequest;
    }

    @Override
    public void post(String url, Map<String, Object> params, ICallback callback) {
        String finalUrl = appendParams(url, params);//此函数就是为了适配get方式，拆出来组params
        mHttpRequest.post(finalUrl, params, callback);
    }

    @Override
    public void get(String url, ICallback callback) {
        mHttpRequest.get(url, callback);
    }

    // 下面就是把  get链接里面的参数内容 ，截取出来 封装成 post请求需要的 params参数集
    public static String appendParams(String url, Map<String, Object> params) {
        if (params == null || params.isEmpty()) {
            return url;
        }
        StringBuilder urlBuilder = new StringBuilder(url);
        if (urlBuilder.indexOf("?") <= 0) {
            urlBuilder.append("?");
        } else {
            if (!urlBuilder.toString().endsWith("?")) {
                urlBuilder.append("&");
            }
        }
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            urlBuilder.append("&" + entry.getKey())
                    .append("=")
                    .append(encode(entry.getValue().toString()));
        }
        return urlBuilder.toString();
    }

    private static String encode(String str) {
        try {
            return URLEncoder.encode(str, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

}
