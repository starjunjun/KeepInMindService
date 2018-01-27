package com.cross.Bean;

public class ReturnJson<T> {
    private String resultcode;
    private String reason;
    private T result;

    public String getResultcode() {
        return resultcode;
    }

    public ReturnJson setResultcode(String status) {
        this.resultcode = status;
        return this;
    }

    public T getResult() {
        return result;
    }

    public ReturnJson setResult(T result) {
        this.result = result;
        return this;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
