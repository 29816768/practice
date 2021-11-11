package com.wyq.annotation_lib;

import android.app.Activity;
import android.util.Log;
import android.view.View;

import com.wyq.annotation_lib.annatation_common.OnBaseCommon;
import com.wyq.annotation_lib.annotation.BindView;
import com.wyq.annotation_lib.annotation.Click;
import com.wyq.annotation_lib.annotation.ContentView;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Author: wangyongqi
 * Date: 2021/11/11 10:07
 * Description: 处理注解
 */
public class Inject {
    private static final String TAG = Inject.class.getSimpleName();

    public static void inject(Activity activity) {
        injectSetContentView(activity);
        injectBindView(activity);
        injectClick(activity);
        injectEvent(activity);
    }

    /**
     * 把布局绑定到Activity中去
     *
     * @param activity Activity 的 this
     */
    private static void injectSetContentView(Activity activity) {
        Class<?> mActivityClass = activity.getClass();
        ContentView mContentView = mActivityClass.getAnnotation(ContentView.class);
        if (null == mContentView) {
            Log.d(TAG, "ContentView is null ");
            return;
        }
        int layoutId = mContentView.value();
        try {
            Method setContentViewMethod = mActivityClass.getMethod("setContentView", int.class);
            setContentViewMethod.invoke(activity, layoutId);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }

    }

    /**
     * 把控件板绑定到Activity中去
     *
     * @param activity Activity 的 this
     */
    private static void injectBindView(Activity activity) {
        Class<?> mMainActivityClass = activity.getClass();

        // mMainActivityClass.getFields(); // 1  2  public
        Field[] fields = mMainActivityClass.getDeclaredFields();// 私有的 公开的 都可以

        for (Field field : fields) {
            field.setAccessible(true); // 让JVM不要去管 private 修饰的
            BindView bindView = field.getAnnotation(BindView.class);
            if (bindView == null) {
                Log.d(TAG, "BindView is null");
                continue; // 结束本次，继续下一次
            }
            int viewID = bindView.value();
            try {
                Method findViewByIdMethod = mMainActivityClass.getMethod("findViewById", int.class);
                Object resultView = findViewByIdMethod.invoke(activity, viewID);
                field.set(activity, resultView);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * 把点击事件绑定到 Activity中去，写死代码setOnClickListener
     *
     * @param activity MainActivity 的 this
     */
    private static void injectClick(Activity activity) {
        Class<?> mMainActivityClass = activity.getClass();

        Method[] declaredMethods = mMainActivityClass.getDeclaredMethods();

        for (Method declaredMethod : declaredMethods) {
            declaredMethod.setAccessible(true);

            // 过滤必须是 Click注解
            Click click = declaredMethod.getAnnotation(Click.class);

            if (click == null) {
                Log.d(TAG, "BindView is null");
                continue; // 结束本次，继续下一次
            }

            // R.id.bt_test3 == viewID
            int viewID = click.value();

            try {
                // 1.findViewById 反射寻找 findViewById
                Method findViewByIdMethod = mMainActivityClass.getMethod("findViewById", int.class);

                // 2.findViewById 反射执行 的 成果拿到   resultView == button1的实例对象了
                Object resultView = findViewByIdMethod.invoke(activity, viewID);

                // 3.findViewById 反射执行 的 成果拿到 .setOnClickListener { }
                View view = (View) resultView;

                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // 4.反射回调到 Click注解的show函数  最终一定要反射执行 show函数
                        //declaredMethod == 第三个函数 show函数，为什么，因为前面代码已经过滤了
                        try {
                            declaredMethod.invoke(activity);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * 把点击事件绑定到Activity中去，通用版本
     *
     * @param activity Activity 的 this
     */
    private static void injectEvent(Activity activity) {
        Class<?> activityClass = activity.getClass();
        Method[] declaredMethods = activityClass.getDeclaredMethods();
        for (Method declaredMethod : declaredMethods) {
            declaredMethod.setAccessible(true);

            Annotation[] annotations = declaredMethod.getAnnotations();
            for (Annotation annotation : annotations) {
                // 获取当前注解的父注解 是否有 OnBaseCommon
                Class<? extends Annotation> annotationType = annotation.annotationType();
                OnBaseCommon onBaseCommon = annotationType.getAnnotation(OnBaseCommon.class);

                if (onBaseCommon == null) {
                    Log.d(TAG, "OnBaseCommon is null");
                    continue;
                }
                // 获取事件三要数
                String setCommonListener = onBaseCommon.setCommonListener();
                Class setCommonObjectListener = onBaseCommon.setCommonObjectListener();
                String callbackMethod = onBaseCommon.callbackMethod();

                try {
                    // 获取 @OnClickCommon(R.id.bt_t1) value == R.id.bt_t1
                    Method valueMethod = annotationType.getDeclaredMethod("value");
                    valueMethod.setAccessible(true);
                    int value = (int) valueMethod.invoke(annotation);

                    // 就是执行 findViewById 得到控件
                    Method findViewByIdMethod = activityClass.getMethod("findViewById", int.class);
                    Object resultView = findViewByIdMethod.invoke(activity, value);

                    // 再次启用反射
                    Method mViewMethod = resultView.getClass().getMethod(setCommonListener, setCommonObjectListener);
                    // 动态代理 监听 第三个要素
                    Object newProxyInstance = Proxy.newProxyInstance(
                            setCommonObjectListener.getClassLoader(),
                            new Class[]{setCommonObjectListener},
                            new InvocationHandler() {
                                @Override
                                public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                                    return declaredMethod.invoke(activity, null);
                                }
                            });

                    mViewMethod.invoke(resultView, newProxyInstance);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }


    }


}
