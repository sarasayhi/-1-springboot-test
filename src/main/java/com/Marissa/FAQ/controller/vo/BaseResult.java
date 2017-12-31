package com.Marissa.FAQ.controller.vo;

public class BaseResult<T> {
    private String ans;
    private String code;
    private String msg;
    private T data;

    public BaseResult() {}

    public BaseResult(String ans, String code, T data) {
        this.ans = ans;
        this.code = code;
        this.data = data;
    }

    public String getAns() {
        return ans;
    }

    public void setAns(String ans) {
        this.ans = ans;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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
}