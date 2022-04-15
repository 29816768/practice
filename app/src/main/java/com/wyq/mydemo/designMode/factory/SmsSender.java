package com.wyq.mydemo.designMode.factory;

import android.util.Log;

/**
 * Author: wangyongqi
 * Date: 2022/4/12 15:26
 * Description:SmsSender
 */
public class SmsSender implements Sender {

    @Override
    public void send() {
        Log.d("factory","SmsSender");
    }
}
