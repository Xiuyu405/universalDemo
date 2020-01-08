package com.example.crashapp.service.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class UserInformation extends BaseBean{
    @Id
    private long userId;
    private String userName;
    private String userMs;
    private Boolean userIsExist;
    @Generated(hash = 642924366)
    public UserInformation(long userId, String userName, String userMs,
            Boolean userIsExist) {
        this.userId = userId;
        this.userName = userName;
        this.userMs = userMs;
        this.userIsExist = userIsExist;
    }
    @Generated(hash = 1920987651)
    public UserInformation() {
    }
    public long getUserId() {
        return this.userId;
    }
    public void setUserId(long userId) {
        this.userId = userId;
    }
    public String getUserName() {
        return this.userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getUserMs() {
        return this.userMs;
    }
    public void setUserMs(String userMs) {
        this.userMs = userMs;
    }
    public Boolean getUserIsExist() {
        return this.userIsExist;
    }
    public void setUserIsExist(Boolean userIsExist) {
        this.userIsExist = userIsExist;
    }
    


}
