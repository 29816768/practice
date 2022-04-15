package com.wyq.mydemo.myEventBus;

import com.wyq.eventbus_annotation.MethodHandle;

/**
 * Author: wangyongqi
 * Date: 2022/3/22 20:07
 * Description:
 */
public class MyEventBusBuilder {
    MethodHandle methodHandle;

    public MyEventBusBuilder setMethodHandle(MethodHandle aptInvoke){
        this.methodHandle = aptInvoke;
        return this;
    }

    public MyEventBus build(){
        MyEventBus myEventBus = new MyEventBus(this);
        if(MyEventBus.instance == null){
            MyEventBus.instance = myEventBus;
        }
        return myEventBus;
    }
}
