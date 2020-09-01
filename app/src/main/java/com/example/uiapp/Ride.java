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
    public Ride(int earning, int distance, String duration, String datetime, String uid) {
        mEarning = earning;
        mDuration = duration;
        mDistance = distance;
        mUserId = uid;
        mDatetime = datetime;
    }

    public int getEarning() {
        return mEarning;
    }

    public int getDistance() {
        return mDistance;
    }

    public String getDuration() {
        return mDuration;
    }

    public String getDatetime() {
        return mDatetime;
    }

    public int getRideId() {
        return mRideId;
    }

    public String getUserId() {
        return mUserId;
    }

}
