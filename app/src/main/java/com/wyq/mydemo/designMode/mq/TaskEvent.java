package com.wyq.mydemo.designMode.mq;

import java.lang.ref.WeakReference;

/**
 * Author: wangyongqi
 * Date: 2022/4/15 15:50
 * Description:
 */
public class TaskEvent {
    private WeakReference<ITask> mTask;
    int mEventType;

    public ITask getTask() {
        return mTask.get();
    }

    public void setTask(ITask mTask) {
        this.mTask = new WeakReference<>(mTask);
    }

    public int getEventType() {
        return mEventType;
    }

    public void setEventType(int mEventType) {
        this.mEventType = mEventType;
    }

    public static class EventType {
        public static final int DO = 0X00;
        public static final int FINISH = 0X01;
    }
}
