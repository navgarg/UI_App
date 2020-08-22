package com.example.uiapp;

public class Ride {

    private int mRideId;
    private int mEarning;
    private int mDistance;
    private String mDuration;
    private String mMonth;
    private String mUserId;

    public Ride(int earning, int distance, String duration, String month, String uid, int rideid) {
        mRideId = rideid;
        mEarning = earning;
        mDuration = duration;
        mDistance = distance;
        mUserId = uid;
        mMonth = month;
    }

    public int getEarning() {
        return mEarning;
    }

    public void setEarning(int mEarning) {
        this.mEarning = mEarning;
    }

    public int getDistance() {
        return mDistance;
    }

    public void setDistance(int mDistance) {
        this.mDistance = mDistance;
    }

    public String getDuration() {
        return mDuration;
    }

    public void setDuration(String mDuration) {
        this.mDuration = mDuration;
    }

    public String getMonth() {
        return mMonth;
    }

    public void setMonth(String mMonth) {
        this.mMonth = mMonth;
    }

    public int getRideId() {
        return mRideId;
    }

    public void setRideId(int mRideId) {
        this.mRideId = mRideId;
    }


    public String getUserId() {
        return mUserId;
    }

    public void setUserId(String nUserId) {
        this.mUserId = mUserId;
    }

}
