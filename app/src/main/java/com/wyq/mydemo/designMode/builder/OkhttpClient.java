package com.wyq.mydemo.designMode.builder;

import java.util.ArrayList;

/**
 * Author: wangyongqi
 * Date: 2022/4/12 15:39
 * Description:
 */
public class OkhttpClient {
    private int mTime;
    private ArrayList<String> mList;

    public OkhttpClient(Builder builder) {
        mTime = builder.mTime;
        mList = builder.mList;
    }

    public static class Builder {
        private int mTime;
        private ArrayList<String> mList;

        public Builder setTime(int time) {
            mTime = time;
            return this;
        }

        public Builder setList(ArrayList<String> list) {
            mList = list;
            return this;
        }

        public OkhttpClient build() {
            return new OkhttpClient(this);
        }
    }
}
