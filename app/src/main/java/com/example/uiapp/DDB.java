package com.example.uiapp;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import android.content.Context;
import android.util.Log;

import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.mobileconnectors.dynamodbv2.document.Table;
import com.amazonaws.mobileconnectors.dynamodbv2.document.datatype.Document;
import com.amazonaws.mobileconnectors.dynamodbv2.document.datatype.Primitive;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;
import java.util.Random;

public class DDB {
    private static final String COGNITO_POOL_ID = "ap-south-1:1111aaaa-111a-1a11-11aa-a1111111111a";
    private AmazonDynamoDBClient dbClient;
    private Table dbTable;
    private Context context;
    private static Ride ride;
    private final String DYNAMODB_TABLE = "Trip_Earnings";
    CognitoCachingCredentialsProvider credentialsProvider;

    private  DDB (Context context) {
        this.context = context;
    }

    public void main(String[] args){

        credentialsProvider = new CognitoCachingCredentialsProvider (this.context, COGNITO_POOL_ID, Regions.DEFAULT_REGION);
        dbClient = new AmazonDynamoDBClient(credentialsProvider);
        dbClient.setRegion(Region.getRegion(Regions.AP_SOUTH_1));
        dbTable = Table.loadTable(dbClient, DYNAMODB_TABLE);

        DynamoDBMapper mapper = new DynamoDBMapper(dbClient);

        //create(mapper, ride);
    }
    public void create(DynamoDBMapper mapper){
        //create a new ride and save
        //does not take ride as parameter as there is no implementation in the app.
        Ride r1 = new Ride();
        r1.setUserId(FirebaseAuth.getInstance().getCurrentUser().getUid());
        r1.setMonth("August");
        r1.setDuration("5h 55m");
        r1.setDistance(6);
        r1.setEarning(40000);
        r1.setRideId(new Random().nextInt(1000000000));
        mapper.save(r1);
    }
    public List<Ride> query(DynamoDBMapper mapper, Ride r){
        //get back a list of all the rides which have the month of the current ride object and print to stdout
        DynamoDBQueryExpression<Ride> queryExpression = new DynamoDBQueryExpression<Ride>().withHashKeyValues(r).withLimit(10);
        List<Ride> result = mapper.query(Ride.class, queryExpression);
        return result;
    }
    public void update(DynamoDBMapper mapper, Ride r) {
        //Updating the earning of the current ride
        Ride r1 = mapper.load(Ride.class, r.getRideId());
        r1.setEarning(10000);
        mapper.save(r1);
    }
    public void delete(DynamoDBMapper mapper, Ride r){
        //delete current ride
        mapper.delete(r);
        Log.d("DDB", "Ride deleted");
    }
}
