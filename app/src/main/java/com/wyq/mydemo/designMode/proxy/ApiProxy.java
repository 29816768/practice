package com.wyq.mydemo.designMode.proxy;

import android.util.Log;

/**
 * Author: wangyongqi
 * Date: 2022/2/22 16:51
 * Description:
 */
public class ApiProxy implements Api{
    ApiImpl mImp;

    public ApiProxy(Api api) {
        mImp = (ApiImpl) api;
    }

    @Override
    public void test(String str) {
        Log.d("proxy1", "代理类前面加日志");
        mImp.test(str);
        Log.d("proxy1", "代理类后面加日志");
    }
}
