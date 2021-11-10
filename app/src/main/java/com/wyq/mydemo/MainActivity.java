package com.wyq.mydemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.wyq.mydemo.http.HttpCallback;
import com.wyq.mydemo.http.HttpHelper;
import com.wyq.mydemo.http.ResponseData;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    //发起网络请求
    public void onclick(View view) {
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
}