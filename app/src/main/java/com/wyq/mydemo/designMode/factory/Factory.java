package com.wyq.mydemo.designMode.factory;

/**
 * Author: wangyongqi
 * Date: 2022/4/12 15:28
 * Description:抽象工厂模式
 */
public class Factory {

    public static void main(String[] args) {
        Provide provide = new SenderMailFactory();
        Sender sender = provide.senderProvide();
        sender.send();
    }

}
