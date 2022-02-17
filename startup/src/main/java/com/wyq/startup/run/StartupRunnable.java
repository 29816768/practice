package com.wyq.startup.run;

import android.content.Context;
import android.os.Process;

import com.wyq.startup.Startup;
import com.wyq.startup.bean.Result;
import com.wyq.startup.manage.StartupCacheManager;
import com.wyq.startup.manage.StartupManager;

/**
 * Author: wangyongqi
 * Date: 2022/2/16 16:37
 * Description:
 */
public class StartupRunnable implements Runnable {

    private StartupManager startupManager;
    private Startup<?> startup;
    private Context context;

    public StartupRunnable(Context context, Startup<?> startup,
                           StartupManager startupManager) {
        this.context = context;
        this.startup = startup;
        this.startupManager = startupManager;
    }

    @Override
    public void run() {
        Process.setThreadPriority(startup.getThreadPriority());
        startup.toWait();
        Object result = startup.create(context);
        StartupCacheManager.getInstance().saveInitializedComponent(startup.getClass(),
                new Result(result));

        startupManager.notifyChildren(startup);
    }
}
