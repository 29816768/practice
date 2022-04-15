package com.wyq.mydemo.designMode.builder;

import java.util.ArrayList;

/**
 * Author: wangyongqi
 * Date: 2022/4/12 15:48
 * Description:构建者模式
 */
public class Test {

    public static void main(String[] args) {
        OkhttpClient okhttpClient = new OkhttpClient.Builder()
                .setTime(1)
                .setList(new ArrayList<>())
                .build();
    }
}
