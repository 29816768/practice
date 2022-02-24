package com.wyq.mydemo.proxy;

/**
 * Author: wangyongqi
 * Date: 2022/2/22 16:51
 * Description:
 */
public class ApiProxy implements Api{
    ApiImpl mImp;

    public ApiProxy(Api api) {
        mImp = (ApiImpl) api;
    }

    @Override
    public void test(String test) {
        mImp.test(" 代理类加点东西 "+test);
    }
}
