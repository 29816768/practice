package com.wyq.mydemo.matrix;

import android.content.Context;
import android.util.Log;

import com.tencent.matrix.plugin.DefaultPluginListener;
import com.tencent.matrix.report.Issue;
import com.tencent.matrix.util.MatrixLog;

/**
 * Author: wangyongqi
 * Date: 2022/3/18 14:58
 * Description:
 */
public class TestPluginListener extends DefaultPluginListener {
    public static final String TAG = "Matrix.TestPluginListener";

    public TestPluginListener(Context context) {
        super(context);
    }

    @Override
    public void onReportIssue(Issue issue) {
        super.onReportIssue(issue);
        MatrixLog.e(TAG, issue.toString());

    }
}
