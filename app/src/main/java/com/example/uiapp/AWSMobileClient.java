package com.example.uiapp;

import android.content.Context;
import android.util.Log;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.mobile.auth.core.IdentityManager;
import com.amazonaws.mobile.config.AWSConfiguration;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;

public class AWSMobileClient {

    private static final String LOG_TAG = AWSMobileClient.class.getSimpleName();

    private static AWSMobileClient instance;

    private final Context context;

    private AWSConfiguration clientConfiguration;
    private IdentityManager identityManager;
    private AmazonDynamoDBClient dynamoDBClient;
    private DynamoDBMapper dynamoDBMapper;

    public static class Builder {

        private Context applicationContext;
        private String  cognitoIdentityPoolID;
        private Regions cognitoRegion;
        private AWSConfiguration clientConfiguration;
        private IdentityManager identityManager;

        public Builder(final Context context) {
            this.applicationContext = context.getApplicationContext();
        };

        public Builder withCognitoIdentityPoolID(final String cognitoIdentityPoolID) {
            this.cognitoIdentityPoolID = cognitoIdentityPoolID;
            return this;
        };

        public Builder withCognitoRegion(final Regions cognitoRegion) {
            this.cognitoRegion = cognitoRegion;
            return this;
        }

        public Builder withIdentityManager(final IdentityManager identityManager) {
            this.identityManager = identityManager;
            return this;
        }

        public Builder withClientConfiguration(final AWSConfiguration clientConfiguration) {
            this.clientConfiguration = clientConfiguration;
            return this;
        }

        public AWSMobileClient build() {
            return
                    new AWSMobileClient(applicationContext,
                            cognitoIdentityPoolID,
                            cognitoRegion,
                            identityManager,
                            clientConfiguration);
        }
    }

    private AWSMobileClient(final Context context,
                            final String  cognitoIdentityPoolID,
                            final Regions cognitoRegion,
                            final IdentityManager identityManager,
                            final AWSConfiguration clientConfiguration) {

        this.context = context;
        this.identityManager = identityManager;
        this.clientConfiguration = clientConfiguration;

        this.dynamoDBClient = new AmazonDynamoDBClient();
        this.dynamoDBClient.setRegion(Region.getRegion(Regions.DEFAULT_REGION));
        this.dynamoDBMapper = new DynamoDBMapper(dynamoDBClient);
    }

    public static void setDefaultMobileClient(AWSMobileClient client) {
        instance = client;
    }

    public static AWSMobileClient defaultMobileClient() {
        return instance;
    }

    public static void initializeMobileClientIfNecessary(final Context context) {
        if (AWSMobileClient.defaultMobileClient() == null) {
            Log.d(LOG_TAG, "Initializing AWS Mobile Client...");
            final AWSConfiguration clientConfig = new AWSConfiguration(context);
            final IdentityManager identityManager = new IdentityManager(context, clientConfig);
            final AWSMobileClient awsClient =
                    new AWSMobileClient.Builder(context)
                            .withCognitoRegion(Regions.DEFAULT_REGION)
                            .withCognitoIdentityPoolID("")
                            .withIdentityManager(identityManager)
                            .withClientConfiguration(clientConfig)
                            .build();

            AWSMobileClient.setDefaultMobileClient(awsClient);
        }
        Log.d(LOG_TAG, "AWS Mobile Client is OK");
    }

}