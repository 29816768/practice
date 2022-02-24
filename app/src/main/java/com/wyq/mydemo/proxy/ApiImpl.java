package com.wyq.mydemo.proxy;

import android.util.Log;

/**
 * Author: wangyongqi
 * Date: 2022/2/22 16:04
 * Description:
 */
public class ApiImpl implements Api {


    @Override
    public void test(String test) {
        Log.d("proxy", " 真实实现类 " + test);
    }
}
