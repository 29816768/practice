package com.wyq.mydemo.http;

/**
 * Author: wangyongqi
 * Date: 2021/11/10 15:39
 * Description:
 */
public class ResponseData {

    private String result;
    private int error_code;
    private String resultcode;
    private String reason;

    public String getResult() {
        return result == null ? "" : result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public String getResultcode() {
        return resultcode == null ? "" : resultcode;
    }

    public void setResultcode(String resultcode) {
        this.resultcode = resultcode;
    }

    public String getReason() {
        return reason == null ? "" : reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    @Override
    public String toString() {
        return "ResponseData{" +
                "result='" + result + '\'' +
                ", error_code=" + error_code +
                ", resultcode='" + resultcode + '\'' +
                ", reason='" + reason + '\'' +
                '}';
    }
}
