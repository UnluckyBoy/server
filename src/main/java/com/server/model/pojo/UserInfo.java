package com.server.model.pojo;

import java.io.Serializable;

public class UserInfo implements Serializable {
    private int mId;
    private String mHead;
    private String mName;
    private String mPassword;
    private String mSex;
    private String mAccount;
    private String mPhone;
    private String mEmail;
    private int mGptNum;
    private int mLevel;
    private int mStatus;
    private String mAddressIp;


    public UserInfo(int mId,String mHead,String mName,String mPassword,
                    String mSex,String mAccount,String mPhone,String mEmail,int mGptNum,int mLevel) {
        this.mId = mId;
        this.mHead = mHead;
        this.mName = mName;
        this.mPassword = mPassword;
        this.mSex = mSex;
        this.mAccount = mAccount;
        this.mPhone = mPhone;
        this.mEmail = mEmail;
        this.mGptNum = mGptNum;
        this.mLevel = mLevel;
    }

    public int getmId() {
        return mId;
    }

    public void setmId(int mId) {
        this.mId = mId;
    }

    public String getmHead() {
        return mHead;
    }

    public void setmHead(String mHead) {
        this.mHead = mHead;
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

    public void setmAccount(String mAccount) {
        this.mAccount = mAccount;
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

    public int getmGptNum() {
        return mGptNum;
    }

    public void setmGptNum(int mGptNum) {
        this.mGptNum = mGptNum;
    }

    public int getmLevel() {
        return mLevel;
    }

    public void setmLevel(int mLevel) {
        this.mLevel = mLevel;
    }

    public int getmStatus() {
        return mStatus;
    }

    public void setmStatus(int mStatus) {
        this.mStatus = mStatus;
    }

    public String getmAddressIp() {
        return mAddressIp;
    }

    public void setmAddressIp(String mAdressIp) {
        this.mAddressIp = mAdressIp;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "mId=" + mId +
                ", mImage='" + mHead + '\'' +
                ", mName='" + mName + '\'' +
                ", mPassword='" + mPassword + '\'' +
                ", mSex='" + mSex + '\'' +
                ", mAcount='" + mAccount + '\'' +
                ", mPhone='" + mPhone + '\'' +
                ", mEmail='" + mEmail + '\'' +
                '}';
    }
}