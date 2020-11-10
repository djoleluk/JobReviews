package com.pistolshrimpstudio.jobreviews.custom;

import android.os.Parcel;
import android.os.Parcelable;

public class JobPosition implements Parcelable {
    private String mTitle;
    private String mType;
    private String mLocation;
    private String mCompany;
    private String mPostDate;
    private String mDescURL;
    private String mCompanyURL;
    private String mLogoUrl;

    public JobPosition() {
        mTitle = "";
        mType = "";
        mLocation = "";
        mCompany = "";
        mPostDate = "";
        mDescURL = "";
        mCompanyURL = "";
        mLogoUrl = "";
    }

    protected JobPosition(Parcel in) {
        mTitle = in.readString();
        mType = in.readString();
        mLocation = in.readString();
        mCompany = in.readString();
        mPostDate = in.readString();
        mDescURL = in.readString();
        mCompanyURL = in.readString();
        mLogoUrl = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mTitle);
        dest.writeString(mType);
        dest.writeString(mLocation);
        dest.writeString(mCompany);
        dest.writeString(mPostDate);
        dest.writeString(mDescURL);
        dest.writeString(mCompanyURL);
        dest.writeString(mLogoUrl);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<JobPosition> CREATOR = new Creator<JobPosition>() {
        @Override
        public JobPosition createFromParcel(Parcel in) {
            return new JobPosition(in);
        }

        @Override
        public JobPosition[] newArray(int size) {
            return new JobPosition[size];
        }
    };

    public void setTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public void setCompany(String mCompany) {
        this.mCompany = mCompany;
    }

    public void setType(String mType) {
        this.mType = mType;
    }

    public void setLocation(String mLocation) {
        this.mLocation = mLocation;
    }

    public void setPostDate(String mPostTime) {
        this.mPostDate = mPostTime;
    }

    public void setDescURL(String mURL) {
        this.mDescURL = mURL;
    }

    public void setCompanyURL(String mCompanyURL) {
        this.mCompanyURL = mCompanyURL;
    }

    public void setLogoUrl(String mLogoUrl) {
        this.mLogoUrl = mLogoUrl;
    }

    public String getCompany() {
        return mCompany;
    }

    public String getCompanyURL() {
        return mCompanyURL;
    }

    public String getLocation() {
        return mLocation;
    }

    public String getPostDate() {
        return mPostDate;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getType() {
        return mType;
    }

    public String getDescURL() {
        return mDescURL;
    }

    public String getLogoUrl() {
        return mLogoUrl;
    }

    @Override
    public String toString() {
        return mTitle;
    }

}
