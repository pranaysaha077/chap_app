package com.example.dell.commonroom;

import android.app.Application;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Pranay on 2/6/2018.
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        DatabaseReference scoresRef = FirebaseDatabase.getInstance().getReference("scores");
    }
}
