package com.wyq.mydemo.designMode.state;


/**
 * Author: wangyongqi
 * Date: 2022/2/25 10:53
 * Description:
 */
public class StartState implements State {

    public void doAction(Context context) {
        System.out.println("Player is in start state");
        context.setState(this);
    }

    public String toString(){
        return "Start State";
    }
}