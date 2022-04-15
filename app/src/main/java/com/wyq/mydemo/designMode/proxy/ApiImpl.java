package com.wyq.mydemo.designMode.proxy;

import android.util.Log;

/**
 * Author: wangyongqi
 * Date: 2022/2/22 16:04
 * Description:
 */
public class ApiImpl implements Api {


    @Override
    public void test(String str) {
        Log.d("proxy1", " 真实实现类");
    }
}
