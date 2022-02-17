package com.wyq.startup.tasks;

import android.content.Context;
import android.os.Looper;
import android.os.SystemClock;


import com.wyq.startup.AndroidStartup;
import com.wyq.startup.Startup;
import com.wyq.startup.utils.LogUtils;

import java.util.List;

import androidx.annotation.Nullable;

public class Task1 extends AndroidStartup<String> {

    @Nullable
    @Override
    public String create(Context context) {
        String t = Looper.myLooper() == Looper.getMainLooper()
                ? "主线程: " : "子线程: ";
        LogUtils.log(t + " Task1：学习Java基础");
        SystemClock.sleep(3_000);
        LogUtils.log(t + " Task1：掌握Java基础");
        return "Task1返回数据";
    }


    //执行此任务需要依赖哪些任务
    @Override
    public List<Class<? extends Startup<?>>> dependencies() {
        return super.dependencies();
    }

    @Override
    public boolean callCreateOnMainThread() {
        return false;
    }

    @Override
    public boolean waitOnMainThread() {
        return false;
    }
}
