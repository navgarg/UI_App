package com.example.uiapp;

public class Ride {

    private int mEarning;
    private int mDistance;
    private long mDuration;
    private String mMonth;

    public Ride(int earning, int distance, long duration, String month){
        mEarning = earning;
        mDistance = distance;
        mDuration = duration;
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

    public long getDuration() {
        return mDuration;
    }

    public void setDuration(int mDuration) {
        this.mDuration = mDuration;
    }

    public String getMonth() {
        return mMonth;
    }

    public void setMonth(String mMonth) {
        this.mMonth = mMonth;
    }
}
