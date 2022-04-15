package com.wyq.mydemo;

import android.app.Application;
import android.content.Context;
import android.view.Choreographer;

import com.tencent.matrix.Matrix;
import com.tencent.matrix.iocanary.IOCanaryPlugin;
import com.tencent.matrix.iocanary.config.IOConfig;
import com.wyq.mydemo.http.HttpHelper;
import com.wyq.mydemo.http.VolleyRequest;
import com.wyq.mydemo.matrix.DynamicConfigImpDemo;
import com.wyq.mydemo.matrix.TestPluginListener;
import com.wyq.mydemo.util.FPSFrameCallback;

/**
 * Author: wangyongqi
 * Date: 2021/11/10 15:36
 * Description:
 */
public class MyApplication extends Application {

    @Override
    protected  void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //用来计算丢帧情况
        Choreographer.getInstance().postFrameCallback(new FPSFrameCallback(System.nanoTime()));

        initNetworkLib();

        initMetrix();

//        new Task1().create(MyApplication.this);
//        new Task2().create(MyApplication.this);
//        new Task3().create(MyApplication.this);
//        new Task4().create(MyApplication.this);
//        new Task5().create(MyApplication.this);
//
//        new StartupManager.Builder()
//                .addStartup(new Task5())
//                .addStartup(new Task4())
//                .addStartup(new Task3())
//                .addStartup(new Task2())
//                .addStartup(new Task1())
//                .build(this)
//                .start().await();
    }

    private void initMetrix() {
        Matrix.Builder builder = new Matrix.Builder(this); // build matrix
        builder.pluginListener(new TestPluginListener(this)); // add general pluginListener
        DynamicConfigImpDemo dynamicConfig = new DynamicConfigImpDemo(); // dynamic config
        // init plugin
        IOCanaryPlugin ioCanaryPlugin = new IOCanaryPlugin(new IOConfig.Builder()
                .dynamicConfig(dynamicConfig)
                .build());
        //add to matrix
        builder.plugin(ioCanaryPlugin);

        //init matrix
        Matrix.init(builder.build());

        // start plugin
        ioCanaryPlugin.start();
    }

    //一行代码切换网络框架
    private void initNetworkLib() {
        HttpHelper.init(new VolleyRequest(this));
//        HttpHelper.init(new XUtilsRequest(this));
//        HttpHelper.init(new OkHttpRequest());
    }
}
