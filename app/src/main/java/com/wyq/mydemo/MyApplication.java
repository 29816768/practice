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
                Log.d("MgReadBasicSdk","MgShellApplication =========== startMemberPage");
                MiguModuleServiceManager.launchWebPage(activity,  "https://www.baidu.com",false);
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
