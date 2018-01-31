package com.example.dell.commonroom;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class TalkActivity extends AppCompatActivity {

    private EditText meditText;
    private DatabaseReference mDatabase;
    private Button mbtn;
   // private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_talk);

        meditText=(EditText)findViewById(R.id.editMassegeE);
       mDatabase= FirebaseDatabase.getInstance().getReference().child("information");
       // mbtn=(Button)findViewById(R.id.buttonClicked);



    }
   public void sendbuttonClicked(View veiw)
   {
      final String data=meditText.getText().toString().trim();
      if(!TextUtils.isEmpty(data)) {
          final DatabaseReference newPost = mDatabase.push();
          newPost.child("content").setValue(data);

      }

   }

}
