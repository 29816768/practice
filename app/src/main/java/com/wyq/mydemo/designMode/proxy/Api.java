package com.wyq.mydemo.designMode.proxy;

/**
 * Author: wangyongqi
 * Date: 2022/2/22 16:04
 * Description: 代理类和目标类理应实现同一组接口
 * 之所以实现相同接口，是为了尽可能保证代理对象的内部结构和目标对象一致，
 * 这样我们对代理对象的操作最终都可以转移到目标对象身上，代理对象只需专注于增强代码的编写
 */
public interface Api {
    void test(String test);
}
