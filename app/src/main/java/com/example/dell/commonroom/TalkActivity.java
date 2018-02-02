package com.example.dell.commonroom;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
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
    private String recipientNumber;

   // private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_talk);

        meditText=(EditText)findViewById(R.id.editMassegeE);
       mDatabase= FirebaseDatabase.getInstance().getReference().child("information");
       // mbtn=(Button)findViewById(R.id.buttonClicked);

        Intent intent = getIntent();
        recipientNumber = intent.getStringExtra("RecipientNumber");


    }
   public void sendbuttonClicked(View veiw)
   {
      final String data=meditText.getText().toString().trim();
      if(!TextUtils.isEmpty(data)) {
//          final DatabaseReference newPost = mDatabase.push();
//          newPost.child().setValue(data);
sendMessage(data);

      }

   }

   private void sendMessage(String data)
   {
       String userNum = getuserPhoneNumber();
       Message message = new Message(data, recipientNumber, userNum);
       if(userNum != null)
       {
           FirebaseDatabase.getInstance().getReference().child("messages").child(userNum).child("sent").child(String.valueOf(System.currentTimeMillis() / 1000L)).setValue(message);
           FirebaseDatabase.getInstance().getReference().child("messages").child(recipientNumber).child("recieved").child(String.valueOf(System.currentTimeMillis() / 1000L)).setValue(message);

       }
   }
   private void getMessageList()
   {

   }



   private String getuserPhoneNumber()
   {
        SharedPreferences settings = getApplicationContext().getSharedPreferences("Shared", 0);
       Log.e("App", "Userphone: " + settings.getString("phoneNumber", null));
        return settings.getString("phoneNumber", null);
   }

}
