package com.example.uiapp;

public class Ride {

    private int mRideId;
    private int mEarning;
    private int mDistance;
    private String mDuration;
    private String mDatetime;
    private String mUserId;

    public Ride(int earning, int distance, String duration, String datetime, String uid, int rideid) {
        mRideId = rideid;
        mEarning = earning;
        mDuration = duration;
        mDistance = distance;
        mUserId = uid;
        mDatetime = datetime;
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

    public String getDatetime() {
        return mDatetime;
    }

    public void setDatetime(String datetime) {
        this.mDatetime = datetime;
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
