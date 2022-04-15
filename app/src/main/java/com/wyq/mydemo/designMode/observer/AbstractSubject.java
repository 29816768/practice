package com.wyq.mydemo.designMode.observer;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: wangyongqi
 * Date: 2022/4/12 17:04
 * Description:
 */
public abstract class AbstractSubject implements Subject{

    private List<Observer> observerList = new ArrayList<>();

    @Override
    public void add(Observer observer) {
        observerList.add(observer);
    }

    @Override
    public void del(Observer observer) {
        observerList.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for(int i = 0; i < observerList.size(); i++){
            observerList.get(i).update();
        }
    }
}
