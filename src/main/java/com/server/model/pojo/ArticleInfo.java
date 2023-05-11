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
    private int mType;

    public ArticleInfo(int mId, String mTitle, String mCover, String mDescription, String mContent, String mAuthor, int mHot, int mType) {
        this.mId = mId;
        this.mTitle = mTitle;
        this.mCover = mCover;
        this.mDescription = mDescription;
        this.mContent = mContent;
        this.mAuthor = mAuthor;
        this.mHot = mHot;
        this.mType = mType;
    }

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

    public int getmType() {
        return mType;
    }

    public void setmType(int mType) {
        this.mType = mType;
    }
}
