package com.wyq.mydemo.designMode.observer;

/**
 * Author: wangyongqi
 * Date: 2022/4/12 17:13
 * Description:观察者模式
 */
public class ObserverTest {
    public static void main(String[] args) {
        Subject subject = new MySubject();
        subject.add(new Observer1());
        subject.add(new Observer2());
        subject.operation();
    }
}
