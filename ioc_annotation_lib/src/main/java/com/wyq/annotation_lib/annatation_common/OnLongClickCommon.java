package com.wyq.annotation_lib.annatation_common;

import android.view.View;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Author: wangyongqi
 * Date: 2021/11/11 14:07
 * Description:长按的注解
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@OnBaseCommon(setCommonListener = "setOnLongClickListener",
        setCommonObjectListener = View.OnLongClickListener.class,
        callbackMethod = "onLongClick")
public @interface OnLongClickCommon {
    int value();
}
