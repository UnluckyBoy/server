package com.server.model.pojo;

import java.io.Serializable;

public class UserInfo implements Serializable {
    private int mId;
    private String mImage;
    private String mName;
    private String mPassword;
    private String mSex;
    private String mAccount;
    private String mPhone;
    private String mEmail;
    private int mGptNum;

    public int getmGptNum() {
        return mGptNum;
    }

    public void setmGptNum(int mGptNum) {
        this.mGptNum = mGptNum;
    }

    public int getmId() {
        return mId;
    }

    public void setmId(int mId) {
        this.mId = mId;
    }

    public String getmImage() {
        return mImage;
    }

    public void setmImage(String mImage) {
        this.mImage = mImage;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getmPassword() {
        return mPassword;
    }

    public void setmPassword(String mPassword) {
        this.mPassword = mPassword;
    }

    public String getmSex() {
        return mSex;
    }

    public void setmSex(String mSex) {
        this.mSex = mSex;
    }

    public String getmAccount() {
        return mAccount;
    }

    public void setmAccount(String mAcount) {
        this.mAccount = mAcount;
    }

    public String getmPhone() {
        return mPhone;
    }

    public void setmPhone(String mPhone) {
        this.mPhone = mPhone;
    }

    public String getmEmail() {
        return mEmail;
    }

    public void setmEmail(String mEmail) {
        this.mEmail = mEmail;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "mId=" + mId +
                ", mImage='" + mImage + '\'' +
                ", mName='" + mName + '\'' +
                ", mPassword='" + mPassword + '\'' +
                ", mSex='" + mSex + '\'' +
                ", mAcount='" + mAccount + '\'' +
                ", mPhone='" + mPhone + '\'' +
                ", mEmail='" + mEmail + '\'' +
                '}';
    }
}