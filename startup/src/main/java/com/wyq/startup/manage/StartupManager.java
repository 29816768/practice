package com.wyq.startup.manage;

import android.content.Context;
import android.os.Looper;

import com.wyq.startup.Startup;
import com.wyq.startup.bean.StartupSortStore;
import com.wyq.startup.run.StartupRunnable;
import com.wyq.startup.sort.TopologySort;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Author: wangyongqi
 * Date: 2022/2/16 16:06
 * Description:构建者模式
 */
public class StartupManager {

    private CountDownLatch awaitCountDownLatch;
    private Context context;
    private List<Startup<?>> startupList;
    private StartupSortStore startupSortStore;

    public StartupManager(Context context, List<Startup<?>> list, CountDownLatch countDownLatch) {
        this.context = context;
        this.startupList = list;
        this.awaitCountDownLatch = countDownLatch;
    }

    public StartupManager start() {
        if (Looper.myLooper() != Looper.getMainLooper()) {
            throw new RuntimeException("Please start in MainThread");
        }
        startupSortStore = TopologySort.sort(startupList);
        for (Startup<?> startup : startupSortStore.getResult()) {
            StartupRunnable startupRunnable = new StartupRunnable(context, startup, this);
            if (startup.callCreateOnMainThread()) {
                startupRunnable.run();
            } else {
                startup.executor().execute(startupRunnable);
            }
        }
        return this;
    }

    public void await() {
        try {
            awaitCountDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void notifyChildren(Startup<?> startup) {
        if (!startup.callCreateOnMainThread() && startup.waitOnMainThread()) {
            awaitCountDownLatch.countDown();
        }
        //获得已经完成的当前任务的所有子任务
        if (startupSortStore.getStartupChildrenMap().containsKey(startup.getClass())) {
            List<Class<? extends Startup>> childStratupClass = startupSortStore
                    .getStartupChildrenMap().get(startup.getClass());
            if (childStratupClass != null) {
                for (Class<? extends Startup> cls : childStratupClass) {
                    //通知子任务 startup父任务已完成
                    Startup<?> childStartup = startupSortStore.getStartupMap().get(cls);
                    if (childStartup != null) {
                        childStartup.toNotify();
                    }
                }
            }
        }
    }

    public static class Builder {
        List<Startup<?>> list = new ArrayList<>();

        public Builder addStartup(Startup<?> Startup) {
            list.add(Startup);
            return this;
        }

        public Builder addAllStartup(List<Startup<?>> startups) {
            list.addAll(startups);
            return this;
        }

        public StartupManager build(Context context) {
            AtomicInteger atomicInteger = new AtomicInteger();
            for (Startup<?> startup : list) {
                //记录需要主线程等待完成的异步任务
                if (!startup.callCreateOnMainThread() && startup.waitOnMainThread()) {
                    atomicInteger.incrementAndGet();
                }
            }
            CountDownLatch countDownLatch = new CountDownLatch(atomicInteger.get());
            return new StartupManager(context, list, countDownLatch);
        }
    }
}
