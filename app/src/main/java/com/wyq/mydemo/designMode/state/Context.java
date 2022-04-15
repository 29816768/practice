package com.wyq.mydemo.designMode.state;

/**
 * Author: wangyongqi
 * Date: 2022/2/25 10:54
 * Description:
 */
public class Context {
    private State state;

    public Context(){
        state = null;
    }

    public void setState(State state){
        this.state = state;
    }

    public State getState(){
        return state;
    }
}