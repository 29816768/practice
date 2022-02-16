package com.wyq.mydemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.cmread.miguread.login.config.MgLoginConfig;
import com.cmread.migureadsdk.MgReadBasicSdk;
import com.wyq.annotation_lib.Inject;
import com.wyq.annotation_lib.annatation_common.OnClickCommon;
import com.wyq.annotation_lib.annatation_common.OnLongClickCommon;
import com.wyq.annotation_lib.annotation.BindView;
import com.wyq.annotation_lib.annotation.Click;
import com.wyq.annotation_lib.annotation.ContentView;
import com.wyq.mydemo.http.HttpCallback;
import com.wyq.mydemo.http.HttpHelper;
import com.wyq.mydemo.http.ResponseData;

import java.util.HashMap;

@ContentView(R.layout.activity_main)
public class MainActivity extends AppCompatActivity {

    @BindView(R.id.bt_test1)
    Button button0;

    @BindView(R.id.bt_test3)
    private Button button3;

    @BindView(R.id.bt_t3)
    private Button time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Inject.inject(this);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("wyq", "onClick: setOnClickListener");
            }
        });

        button3.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Log.d("wyq", "onClick: setOnLongClickListener");
                return true;
            }
        });
    }

    //发起网络请求
    @Click(R.id.bt_test1)
    private void show() {
        String url = "https://v.juhe.cn/historyWeather/citys";
        HashMap<String, Object> params = new HashMap<>();
        params.put("province_id", "2");
        params.put("key", "bb52107206585ab074f5e59a8c73875b");

        HttpHelper.getInstance().post(url, params, new HttpCallback<ResponseData>() {
            @Override
            public void onSuccess(ResponseData objResult) {
                Toast.makeText(MainActivity.this, objResult.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

/*    @Click(R.id.bt_test3)
    private void show3() {
        Toast.makeText(this, "show3 is run", Toast.LENGTH_SHORT).show();
    }*/

    @Click(R.id.bt_test4)
    private void show4() {
        Toast.makeText(this, "show4 is run", Toast.LENGTH_SHORT).show();
    }

    @Click(R.id.bt_t3)
    private void show5() {
        startActivity(new Intent(MainActivity.this, TimeActivity.class));
    }
    @Click(R.id.bt_t4)
    private void show6() {
        MgReadBasicSdk.startMgReadSdkMainPage(MainActivity.this, "", "D0022579",
                MgLoginConfig.MgCCMMLoginType.MG_CCMM_STANDARD_UP_TOKEN
            );
    }


    @OnClickCommon(R.id.bt_t1) // 点击事件
    private void test1() {
        Toast.makeText(this, "兼容事件 点击", Toast.LENGTH_SHORT).show();
    }

    @OnLongClickCommon(R.id.bt_t2) // 长按事件
    private boolean test2() {
        Toast.makeText(this, "兼容事件 长按", Toast.LENGTH_SHORT).show();
        return false;
    }

}