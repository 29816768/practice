package com.wyq.mydemo.designMode.mq;

import android.os.SystemClock;
import android.util.Log;

/**
 * Author: wangyongqi
 * Date: 2022/4/15 15:52
 * Description:
 */
public class LogTask extends BaseTask {

    String name;

    public LogTask(String name) {
        this.name = name;
    }

    @Override
    public void doTask() {
        super.doTask();
        SystemClock.sleep(2000);
        Log.i("LogTask", "--doTask-" + name);
        //如果这个Task的执行时间是不确定的，比如上传图片，那么在上传成功后需要手动调用
        //unLockBlock方法解除阻塞，例如：
/*        uploadImage(new UploadListener {
            void onSuccess () {
                unLockBlock();
            }
        });*/

    }

    //任务执行完的回调，在这里可以做些释放资源或者埋点之类的操作
    @Override
    public void finishTask() {
        super.finishTask();
        Log.i("LogTask", "--finishTask-" + name);
    }

}
