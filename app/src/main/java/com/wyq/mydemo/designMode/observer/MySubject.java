package com.wyq.mydemo.designMode.observer;

/**
 * Author: wangyongqi
 * Date: 2022/4/12 17:07
 * Description:
 */
public class MySubject extends AbstractSubject{

    @Override
    public void operation() {
        notifyObservers();
    }
}
