package com.example.dell.commonroom;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {


    private FirebaseAuth mAuth;

    private Button mlogout;
private Toolbar mtoolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //mtoolbar=(Toolbar)findViewById(R.id.main_page_)
        mAuth = FirebaseAuth.getInstance();

        mlogout=(Button)findViewById(R.id.logoutbtn);

        mlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                sendToAuth();
            }
        });

    }

    private void sendToAuth() {

        Intent authIntent=new Intent(MainActivity.this,AuthActivity.class);
        startActivity(authIntent);
        finish();
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if(currentUser==null)
        {
                Intent mainIntent=new Intent(MainActivity.this,AuthActivity.class);
                startActivity(mainIntent);
                finish();
        }
        else
        {
            Intent mainIntent=new Intent(MainActivity.this, FriendListActivity.class);
            startActivity(mainIntent);
            finish();
        }

    }
}
