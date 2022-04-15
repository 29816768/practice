package com.wyq.mydemo;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Trace;
import android.util.Log;
import android.view.Choreographer;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.Toast;

import com.wyq.annotation_lib.Inject;
import com.wyq.annotation_lib.annatation_common.OnClickCommon;
import com.wyq.annotation_lib.annatation_common.OnLongClickCommon;
import com.wyq.annotation_lib.annotation.BindView;
import com.wyq.annotation_lib.annotation.Click;
import com.wyq.annotation_lib.annotation.ContentView;
import com.wyq.eventbus_annotation.Subscribe;
import com.wyq.eventbus_annotation.ThreadMode;
import com.wyq.mydemo.activity.DrawableActivity;
import com.wyq.mydemo.activity.TimeActivity;
import com.wyq.mydemo.bean.ViewEvent;
import com.wyq.mydemo.bean.WorkEvent;
import com.wyq.mydemo.designMode.mq.LogTask;
import com.wyq.mydemo.designMode.mq.TaskPriority;
import com.wyq.mydemo.http.HttpCallback;
import com.wyq.mydemo.http.HttpHelper;
import com.wyq.mydemo.http.ResponseData;
import com.wyq.mydemo.myEventBus.MyEventBus;
import com.wyq.mydemo.designMode.proxy.Api;
import com.wyq.mydemo.designMode.proxy.ApiImpl;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.HashMap;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

@ContentView(R.layout.activity_main)
public class MainActivity extends AppCompatActivity {

    @BindView(R.id.bt_test1)
    Button button0;

    @BindView(R.id.bt_test3)
    private Button button3;

    @BindView(R.id.bt_t3)
    private Button time;

    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //反射调用方式
        MyEventBus.getDefault().register(this);
        //AptMethodFinder aptMethodFinder = new AptMethodFinder();
        //注解处理调用方式
        //MyEventBus.builder().setMethodHandle(aptMethodFinder).build().register(this);

        //x2c  不支持appcompat，兼容性的问题
        //AsyncLayoutInflater 异步加载布局 不能有fragment 不支持
        //systrace的使用  Frame alert 时间分片 app里的CPU+ GPU
        //正式环境只能反射setAppTracingAllowed 置为true来使用
        Trace.beginSection("Inject_inject");
        Inject.inject(this);
        Trace.endSection();
        findViewById(R.id.bt_test3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Message message = Message.obtain();
                message.arg1 = 1;
                message.obj = "hello";
                handler.sendMessage(message);
            }
        });

        button3.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Log.d("wyq", "onClick: setOnLongClickListener");
                return true;
            }
        });
        final long starTime = System.nanoTime();

        Choreographer.getInstance().postFrameCallback(new Choreographer.FrameCallback() {
            @Override
            public void doFrame(long frameTimeNanos) {
                Log.e("wyqqq", "starTime=" + starTime + ", frameTimeNanos=" + frameTimeNanos
                        + ", frameDueTime=" + (frameTimeNanos - starTime) / 1000000);
            }
        });

        ObjectAnimator objectAnimator = ObjectAnimator
                .ofFloat(button3, "translationX", 0, 100)
                .setDuration(1000);
        objectAnimator.setInterpolator(new LinearInterpolator());
        objectAnimator.start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                Looper.prepare();
                handler = new Handler() {
                    @Override
                    public void handleMessage(@NonNull Message msg) {
                        if (msg.arg1 == 1) {
                            Log.d("wyq123", msg.obj + "");
                        }
                    }
                };
                Looper.loop();
            }
        }).start();

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

    @Click(R.id.bt_test4)
    private void show4() {
        new LogTask("LOW")
                .setDuration(4000)
                .setPriority(TaskPriority.LOW)
                .enqueue();
    }

    @Click(R.id.bt_t3)
    private void show5() {
        new LogTask("DEFAULT")
                .setDuration(5000) //设置了时间，代表这个任务时间是确定的，如果不确定，则不用设置
                .setPriority(TaskPriority.DEFAULT) //设置优先级，默认是DEFAULT
                .enqueue(); //入队
    }

    @Click(R.id.bt_t4)
    private void show6() {
        new LogTask("HIGH")
                .setDuration(3000)
                .setPriority(TaskPriority.HIGH)
                .enqueue();
    }

    @Click(R.id.bt_t5)
    private void show7() throws Exception {
        //静态代理
/*
        ApiImpl imp = new ApiImpl();
        ApiProxy monitorProxy = new ApiProxy(imp);
        monitorProxy.test("代理类");
*/

        //动态代理
        // TODO: 2022/3/31  第一种方式
        //1.接口无法new实例，所以通过新的class对象，来创建代理对象,
        //传入接口Class，得到一个新的Class对象，该对象带有构造器，是可以创建对象的
        Class apiProxyClass = Proxy.getProxyClass(Api.class.getClassLoader(), Api.class);
        //2.得到有参构造器,需要传入InvocationHandler
        Constructor constructor = apiProxyClass.getConstructor(InvocationHandler.class);
        //3.invocationHandler的invoke()里确实没有目标对象，需要我们手动new
        constructor.newInstance(new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                //4.手动new一个目标对象
                Api apiImpl = new ApiImpl();
                //5.反射执行目标对象的方法
                return method.invoke(apiImpl, args);
            }
        });

        // TODO: 2022/3/31  第二种方式
        ApiImpl target = new ApiImpl();
        //传入目标对象，目的：1.根据它实现的接口生成代理对象 2.代理对象调用目标对象方法
        Api apiProxy = (Api) getProxy(target);
        apiProxy.test("123");

        // TODO: 2022/3/31  第三种方式
        ApiImpl target1 = new ApiImpl();
        Api apiProxy1 = (Api) getProxyByNew(target1);
        apiProxy1.test("456");
    }

    private Object getProxyByNew(Object target) throws Exception {
        Object proxy = Proxy.newProxyInstance(
                target.getClass().getClassLoader(),/*类加载器*/
                new Class[]{Api.class},/*让代理对象和目标对象实现相同接口*/
                new InvocationHandler() {/*代理对象的方法最终都会被JVM导向它的invoke方法*/
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        Log.d("proxy1", "2方法开始执行。。。");
                        Object result = method.invoke(target, args);
                        Log.d("proxy1", "2result:" + result + "  " + method.toString() + "  " + Arrays.toString(args));
                        Log.d("proxy1", "2方法结束执行。。。");
                        return result;
                    }
                });
        return proxy;
    }

    //无论现在系统有多少类，只要把实例传进来，getProxy()都能返回对应的代理对象
    private Object getProxy(Object target) throws Exception {
        //参数1：随便找个类加载器给它， 参数2：目标对象实现的接口，让代理对象实现相同接口
        Class proxyClass = Proxy.getProxyClass(target.getClass().getClassLoader(), target.getClass().getInterfaces());
        Constructor constructor = proxyClass.getConstructor(InvocationHandler.class);
        Object proxy = constructor.newInstance(new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                Log.d("proxy1", "1方法开始执行。。。");
                Object result = method.invoke(target, args);
                Log.d("proxy1", "1result:" + result + "  " + method.toString() + "  " + Arrays.toString(args));
                Log.d("proxy1", "1方法结束执行。。。");
                return result;
            }
        });
        return proxy;
    }

    @Click(R.id.bt_t6)
    private void show8() {
        Intent intent = new Intent();
        intent.setClass(this, DrawableActivity.class);
        startActivity(intent);
    }


    @OnClickCommon(R.id.bt_t1) // 点击事件
    private void test1() {
        Toast.makeText(this, "兼容事件 点击", Toast.LENGTH_SHORT).show();
        MyEventBus.getDefault().post(new WorkEvent(1));
    }

    @OnLongClickCommon(R.id.bt_t2) // 长按事件
    private boolean test2() {
        Toast.makeText(this, "兼容事件 长按", Toast.LENGTH_SHORT).show();
        MyEventBus.getDefault().post(new ViewEvent("主线程测试文字"));
        return false;
    }

    @Subscribe(threadMode = ThreadMode.POSTING, priority = 1)
    public void onEvent(WorkEvent event) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(MainActivity.this, "Thread is " + Thread.currentThread().getName()
                        + " Thread, WorkEvent num=" + event.getNum(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void handleView(final ViewEvent event) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(MainActivity.this, "Thread is " + Thread.currentThread().getName() + " Thread, ViewEvent text=" + event.getText(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MyEventBus.getDefault().unregister(this);
    }
}