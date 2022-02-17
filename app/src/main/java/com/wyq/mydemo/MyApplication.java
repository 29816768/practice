package com.wyq.mydemo;

import android.app.Activity;
import android.app.Application;
import android.util.Log;

import com.cmread.mgprotocol.MiguModuleServiceManager;
import com.cmread.miguread_lite.base.MgReadSDK;
import com.cmread.miguread_utils.base.MgReadSdkConfig;
import com.cmread.miguread_utils.common.route.IMgReadCallBack;
import com.wyq.mydemo.http.HttpHelper;
import com.wyq.mydemo.http.OkHttpRequest;
import com.wyq.mydemo.http.VolleyRequest;
import com.wyq.mydemo.http.XUtilsRequest;
import com.wyq.startup.manage.StartupManager;
import com.wyq.startup.tasks.Task1;
import com.wyq.startup.tasks.Task2;
import com.wyq.startup.tasks.Task3;
import com.wyq.startup.tasks.Task4;
import com.wyq.startup.tasks.Task5;

/**
 * Author: wangyongqi
 * Date: 2021/11/10 15:36
 * Description:
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        initNetworkLib();

        initReadSDK();

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

    private void initReadSDK() {
        MgReadSdkConfig mgReadSdkConfig = new MgReadSdkConfig();
        mgReadSdkConfig.setAndroidXFlag(-1);
        mgReadSdkConfig.setShouldInitAmber(false);
        mgReadSdkConfig.setAppScrect("8175674795fc4b619c367b1c1d3b72fa");
        mgReadSdkConfig.setAppKey("MGSP");
        MgReadSDK.initMgReadSdk(this, "D0022335", mgReadSdkConfig);
        MgReadSDK.setMgReadSdkCallBack(new IMgReadCallBack() {
            @Override
            public void startLogin(Activity activity) {
                //MiguYijiSdkTokenManager.startLoginActivity(activity);
            }

            @Override
            public void startShare() {

            }

            @Override
            public void startMemberPage(Activity activity, String s) {
                Log.d("MgReadBasicSdk", "MgShellApplication =========== startMemberPage");
                MiguModuleServiceManager.launchWebPage(activity, "https://www.baidu.com", false);
                //MgReadBasicSdk.setSubscribeCallback(MgReadBasicSdk.MgSdkCallBackResult.MG_SDK_RESULT_SUCCESS);
            }

            @Override
            public boolean isLogin() {
                return false;
            }
        });
    }

    //一行代码切换网络框架
    private void initNetworkLib() {
        HttpHelper.init(new VolleyRequest(this));
//        HttpHelper.init(new XUtilsRequest(this));
//        HttpHelper.init(new OkHttpRequest());
    }
}
