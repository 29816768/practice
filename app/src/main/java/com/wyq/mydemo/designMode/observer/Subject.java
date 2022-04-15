package com.wyq.mydemo.designMode.observer;

/**
 * Author: wangyongqi
 * Date: 2022/4/12 16:53
 * Description:
 */
public interface Subject {
    public void add(Observer observer);

    public void del(Observer observer);

    public void notifyObservers();

    public void operation();
}
