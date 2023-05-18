package com.server.model.pojo;

import java.io.Serializable;

/**
 * @ClassName ArticleInfo
 * @Author Create By matrix
 * @Date 2023/5/11 0011 17:33
 */
public class ArticleInfo implements Serializable {
    private int mId;
    private String mTitle;
    private String mCover;
    private String mDescription;
    private String mContent;
    private String mAuthor;
    private int mHot;
    private String mType;
    private int mFileType;
    private String mCreateTime;

    public int getmId() {
        return mId;
    }

    public void setmId(int mId) {
        this.mId = mId;
    }

    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getmCover() {
        return mCover;
    }

    public void setmCover(String mCover) {
        this.mCover = mCover;
    }

    public String getmDescription() {
        return mDescription;
    }

    public void setmDescription(String mDescription) {
        this.mDescription = mDescription;
    }

    public String getmContent() {
        return mContent;
    }

    public void setmContent(String mContent) {
        this.mContent = mContent;
    }

    public String getmAuthor() {
        return mAuthor;
    }

    public void setmAuthor(String mAuthor) {
        this.mAuthor = mAuthor;
    }

    public int getmHot() {
        return mHot;
    }

    public void setmHot(int mHot) {
        this.mHot = mHot;
    }

    public String getmType() {
        return mType;
    }

    public void setmType(String mType) {
        this.mType = mType;
    }

    public int getmFileType() {
        return mFileType;
    }

    public void setmFileType(int mFileType) {
        this.mFileType = mFileType;
    }

    public String getmCreateTime() {
        return mCreateTime;
    }

    public void setmCreateTime(String mCreateTime) {
        this.mCreateTime = mCreateTime;
    }

    @Override
    public String toString() {
        return "ArticleInfo{" +
                "mId=" + mId +
                ", mTitle='" + mTitle + '\'' +
                ", mCover='" + mCover + '\'' +
                ", mDescription='" + mDescription + '\'' +
                ", mContent='" + mContent + '\'' +
                ", mAuthor='" + mAuthor + '\'' +
                ", mHot=" + mHot +
                ", mType='" + mType + '\'' +
                ", mFileType=" + mFileType +
                ", mCreateTime='" + mCreateTime + '\'' +
                '}';
    }
}
