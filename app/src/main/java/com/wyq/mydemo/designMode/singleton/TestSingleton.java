package com.wyq.mydemo.designMode.singleton;

/**
 * Author: wangyongqi
 * Date: 2022/4/12 16:12
 * Description:饿汉式单例
 */
public class TestSingleton {
    private static TestSingleton testSingleton = new TestSingleton();

    private TestSingleton() {

    }

    public static TestSingleton getInstance() {
        return testSingleton;
    }
}
