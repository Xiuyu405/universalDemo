package com.example.crashapp.service.entity;

public class BookBean {

    /**
     * msg : invalid_apikey, Please contact bd-team@douban.com for authorized access.
     * code : 104
     * request : GET /v2/book/search
     */

    private String msg;
    private int code;
    private String request;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }
}
