package com.wyq.mydemo;

import android.app.Application;

import com.wyq.mydemo.http.HttpHelper;
import com.wyq.mydemo.http.OkHttpRequest;
import com.wyq.mydemo.http.VolleyRequest;
import com.wyq.mydemo.http.XUtilsRequest;

/**
 * Author: wangyongqi
 * Date: 2021/11/10 15:36
 * Description:
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        initNetworkLib();
    }

    //一行代码切换网络框架
    private void initNetworkLib() {
        HttpHelper.init(new VolleyRequest(this));
//        HttpHelper.init(new XUtilsRequest(this));
//        HttpHelper.init(new OkHttpRequest());
    }
}
