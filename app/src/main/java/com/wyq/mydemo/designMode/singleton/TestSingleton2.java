package com.wyq.mydemo.designMode.singleton;

/**
 * Author: wangyongqi
 * Date: 2022/4/12 16:14
 * Description:懒汉式单例
 */
public class TestSingleton2 {
    private volatile static TestSingleton2 testSingleton = null;

    private TestSingleton2() {
    }

    public static TestSingleton2 getInstance() {
        if (testSingleton == null) { //检查singleton实例是否为空，如果不为空直接返回。
            synchronized (TestSingleton2.class) {
                if (testSingleton == null) {
                    testSingleton = new TestSingleton2();
                }
            }
        }
        return testSingleton;
    }
}
