package com.example.crashapp.service.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class BaseBean {
    private int code;
    private int errormsg;
    @Generated(hash = 107079)
    public BaseBean(int code, int errormsg) {
        this.code = code;
        this.errormsg = errormsg;
    }
    @Generated(hash = 1972076277)
    public BaseBean() {
    }
    public int getCode() {
        return this.code;
    }
    public void setCode(int code) {
        this.code = code;
    }
    public int getErrormsg() {
        return this.errormsg;
    }
    public void setErrormsg(int errormsg) {
        this.errormsg = errormsg;
    }

    

}
