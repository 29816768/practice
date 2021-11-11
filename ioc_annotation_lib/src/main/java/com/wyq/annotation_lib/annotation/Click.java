package com.wyq.annotation_lib.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Author: wangyongqi
 * Date: 2021/11/11 10:51
 * Description:绑定事件
 */
@Target(ElementType.METHOD) // 作用域在方法之上
@Retention(RetentionPolicy.RUNTIME) // 运行时期
public @interface Click {
    int value() default -1;
}
