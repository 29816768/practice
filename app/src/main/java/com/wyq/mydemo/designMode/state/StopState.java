package com.wyq.mydemo.designMode.state;


/**
 * Author: wangyongqi
 * Date: 2022/2/25 10:54
 * Description:
 */
public class StopState implements State {

    public void doAction(Context context) {
        System.out.println("Player is in stop state");
        context.setState(this);
    }

    public String toString(){
        return "Stop State";
    }
}