package com.wyq.startup;

import android.content.Context;

import java.util.List;

/**
 * Author: wangyongqi
 * Date: 2022/2/16 16:21
 * Description:
 */
public interface Startup<T> extends Dispatcher{
    T create(Context context);

    /**
     * 本任务依赖哪些任务
     */
    List<Class<? extends Startup<?>>> dependencies();

    /**
     * 依赖任务数量
     */
    int getDependenciesCount();
}
