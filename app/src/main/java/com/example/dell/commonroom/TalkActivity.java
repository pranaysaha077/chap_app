package com.example.dell.commonroom;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class TalkActivity extends AppCompatActivity {

    private EditText meditText;
    private DatabaseReference mDatabase;
    


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_talk);

        meditText=(EditText)findViewById(R.id.editMassegeE);
        mDatabase= FirebaseDatabase.getInstance().getReference().child("Masseges");



    }


}
