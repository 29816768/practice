package com.wyq.mydemo.util;

import java.net.URL;
import java.net.URLConnection;
import java.util.Date;

/**
 * Author: wangyongqi
 * Date: 2021/12/30 17:45
 * Description:
 */
class timeUtil {
    public static Date getNetTime(){
        String webUrl = "http://www.ntsc.ac.cn";//中国科学院国家授时中心
        try {
            URL url = new URL(webUrl);
            URLConnection uc = url.openConnection();
            uc.setReadTimeout(5000);
            uc.setConnectTimeout(5000);
            uc.connect();
            long correctTime = uc.getDate();
            Date date = new Date(correctTime);
            return date;
        } catch (Exception e) {
            return new Date();
        }
    }



}
