package com.hx.campus.utils.api;

public class Result<T> {
    // 状态码：0-成功，1-失败，500-错误
    private int status;

    // 泛型数据体
    private T data;

    // 提示信息
    private String msg;

    // 无参构造方法供 Gson 使用

    public Result() {
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }


    public boolean isSuccess() {
        return this.status == 0;
    }
}
