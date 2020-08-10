package com.example.uiapp;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBAttribute;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBHashKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBTable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@DynamoDBTable(tableName = "TripEarnings")
public class Ride {

    @DynamoDBHashKey(attributeName = "Trip_ID")
    private int mRideId;
    @DynamoDBAttribute(attributeName = "Trip_Earning")
    private int mEarning;
    @DynamoDBAttribute(attributeName = "Trip_Distance")
    private int mDistance;
    @DynamoDBAttribute(attributeName = "Trip_Duration")
    private String mDuration;
    @DynamoDBAttribute(attributeName = "Trip_Month")
    private String mMonth;
    @DynamoDBAttribute(attributeName = "User_ID")
    private String mUserId;

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
