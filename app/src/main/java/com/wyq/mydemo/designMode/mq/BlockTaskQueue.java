package com.wyq.mydemo.designMode.mq;

import android.util.Log;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Author: wangyongqi
 * Date: 2022/4/15 15:15
 * Description: 阻塞队列
 * 如果队列是空的，调用它的 take()方法时，它就会一直阻塞在那里，当列表不为空时，这个方法就不会阻塞
 */
public class BlockTaskQueue {

    private final static String TAG = "BlockTaskQueue";
    private AtomicInteger mAtomicInteger = new AtomicInteger();

    private final BlockingQueue<ITask> mTaskQueue = new PriorityBlockingQueue<>();

    private BlockTaskQueue() {
    }

    //单例模式
    private static class BlockTaskQueueHolder {
        private final static BlockTaskQueue INSTANCE = new BlockTaskQueue();
    }

    public static BlockTaskQueue getInstance() {
        return BlockTaskQueueHolder.INSTANCE;
    }

    /**
     * 插入时因为每一个Task都实现了comparable接口,所以队列会按照Task复写的compare()方法定义的优先级次序进行插入
     * 当优先级相同时，使用AtomicInteger原子类自增 来为每一个task 设置sequence，
     * sequence的作用是标记两个相同优先级的任务入队的次序
     */
    public <T extends ITask> int add(T task){
        if (!mTaskQueue.contains(task)){
            task.setSequence(mAtomicInteger.incrementAndGet());
            mTaskQueue.add(task);
            Log.d(TAG, "\n add task " + task.toString());
        }
        return mTaskQueue.size();
    }

    public <T extends ITask> void remove(T task){
        if (mTaskQueue.contains(task)){
            mTaskQueue.remove(task);
            Log.d(TAG,  "\n" + "task has been finished. remove it from task queue");
        }
        if (mTaskQueue.size() == 0){
            mAtomicInteger.set(0);
        }
    }

    public ITask poll(){
        return mTaskQueue.poll();
    }

    public ITask take() throws InterruptedException{
        return mTaskQueue.take();
    }

    public void clear(){
        mTaskQueue.clear();
    }

    public int size(){
        return mTaskQueue.size();
    }

}
