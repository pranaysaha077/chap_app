package com.example.dell.commonroom;

/**
 * Created by Dell on 01-02-2018.
 */

public class MessagesClass {

    private String message, toNum, fromNum;

    public MessagesClass(String message, String toNum, String fromNum) {
        this.message = message;
        this.toNum = toNum;
        this.fromNum = fromNum;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getToNum() {
        return toNum;
    }

    public void setToNum(String toNum) {
        this.toNum = toNum;
    }

    public String getFromNum() {
        return fromNum;
    }

    public void setFromNum(String fromNum) {
        this.fromNum = fromNum;
    }

    public MessagesClass() {
    }
}
