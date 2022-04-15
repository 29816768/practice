package com.wyq.mydemo.designMode.factory;

/**
 * Author: wangyongqi
 * Date: 2022/4/12 15:28
 * Description:
 */
public class SenderSmsFactory implements Provide{
    @Override
    public Sender senderProvide() {
        return new SmsSender();
    }
}
