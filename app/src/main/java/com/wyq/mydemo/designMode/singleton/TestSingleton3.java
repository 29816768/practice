package com.wyq.mydemo.designMode.singleton;

/**
 * Author: wangyongqi
 * Date: 2022/4/12 16:25
 * Description:类加载机制保证单例
 */
public class TestSingleton3 {

    private static class singleton3 {
        private static TestSingleton3 testSingleton3 = new TestSingleton3();
    }

    public static TestSingleton3 getTestSingleton3() {
        return singleton3.testSingleton3;
    }
}
