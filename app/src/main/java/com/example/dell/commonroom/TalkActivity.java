package com.example.dell.commonroom;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Handler;
import android.provider.ContactsContract;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.github.bassaer.chatmessageview.model.Message;
import com.github.bassaer.chatmessageview.util.ChatBot;
import com.github.bassaer.chatmessageview.util.ITimeFormatter;
import com.github.bassaer.chatmessageview.view.*;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Random;
import java.util.TimeZone;

public class TalkActivity extends AppCompatActivity {

    private EditText meditText;
    private DatabaseReference mDatabase;
    private Button mbtn;
    private String recipientNumber;

    //for message view
    private ChatView mChatView;


    //User id
    int myId = 0;
    //User icon
    Bitmap myIcon;
    //User name
    String myName = "Michael";

    int yourId = 1;
    Bitmap yourIcon;
    String yourName = "Emily";

    User me;
    User you;
    
   // private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_talk);
        getData();
        myIcon = BitmapFactory.decodeResource(getResources(), R.drawable.face_2);
        me = new User(myId, myName, myIcon);
        you = new User(yourId, yourName, yourIcon);
        yourIcon = BitmapFactory.decodeResource(getResources(), R.drawable.face_1);
        init_ChatActivity();
        previous_messages();
        mDatabase= FirebaseDatabase.getInstance().getReference().child("information");
       // mbtn=(Button)findViewById(R.id.buttonClicked);




    }

    private void getData()
    {
        Intent intent = getIntent();
        myName = intent.getStringExtra("MyName");
        yourName = intent.getStringExtra("RecipientName");
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
       MessagesClass message = new MessagesClass(data, recipientNumber, userNum);
       if(userNum != null)
       {
           FirebaseDatabase.getInstance().getReference().child("messages").child(userNum).child(String.valueOf(System.currentTimeMillis() / 1000L)).setValue(message);
           FirebaseDatabase.getInstance().getReference().child("messages").child(recipientNumber).child(String.valueOf(System.currentTimeMillis() / 1000L)).setValue(message);

       }
   }
   private void getMessageList()
   {

   }
   //** new command starts here


   public void init_ChatActivity()
    {




        mChatView = (ChatView)findViewById(R.id.chat_view);

        //Set UI parameters if you need
        mChatView.setRightBubbleColor(ContextCompat.getColor(this, R.color.green500));
        mChatView.setLeftBubbleColor(Color.WHITE);
        mChatView.setBackgroundColor(ContextCompat.getColor(this, R.color.blueGray500));
        mChatView.setSendButtonColor(ContextCompat.getColor(this, R.color.cyan900));
        mChatView.setSendIcon(R.drawable.ic_action_send);
        mChatView.setRightMessageTextColor(Color.WHITE);
        mChatView.setLeftMessageTextColor(Color.BLACK);
        mChatView.setUsernameTextColor(Color.WHITE);
        mChatView.setSendTimeTextColor(Color.WHITE);
        mChatView.setDateSeparatorColor(Color.WHITE);
        mChatView.setInputTextHint("new message...");
        mChatView.setMessageMarginTop(5);
        mChatView.setMessageMarginBottom(5);

        //Click Send Button
        mChatView.setOnClickSendButtonListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                //new message
//                Message message = new Message.Builder()
//                        .setUser(me)
//                        .setRightMessage(true)
//                        .setMessageText(mChatView.getInputText())
//                        .hideIcon(true)
//                        .build();
//                //Set to chat view
//                mChatView.send(message);
                //Reset edit text
                sendMessage(mChatView.getInputText());
                mChatView.setInputText("");

//                //Receive message
//                final Message receivedMessage = new Message.Builder()
//                        .setUser(you)
//                        .setRightMessage(false)
//                        .setMessageText(message.getMessageText())
//                        .build();
//
//                // This is a demo bot
//                // Return within 3 seconds
//                int sendDelay = (new Random().nextInt(4) + 1) * 1000;
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        mChatView.receive(receivedMessage);
//                    }
//                }, sendDelay);
            }

        });
    }

    private void previous_messages()
    {


        ChildEventListener childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    Log.e("APP", "" + dataSnapshot.getValue() + "     " + dataSnapshot.getKey());
                    MessagesClass messagesClass = dataSnapshot.getValue(MessagesClass.class);
                    final long unixTimeStamp = Long.valueOf(dataSnapshot.getKey());
                    Log.e("App time stamp: ", "    :    " + unixTimeStamp);




                if(messagesClass.getFromNum().equals(getuserPhoneNumber())) {
                    Message message = new Message.Builder()
                            .setUser(me)
                            .setRightMessage(true)
                            .setMessageText(messagesClass.getMessage())
                            .setSendTimeFormatter(new ITimeFormatter() {
                                @NotNull
                                @Override
                                public String getFormattedTimeText(Calendar calendar) {
                                    return getFormattedDate(unixTimeStamp);
                                }
                            })
                            .hideIcon(true)
                            .build();
                    mChatView.send(message);
                    Log.e("APP", "to num from send: " + messagesClass.getFromNum());
                }
                else {
                    Message message = new Message.Builder()
                            .setUser(you)
                            .setRightMessage(false)
                            .setMessageText(messagesClass.getMessage())
                            .setSendTimeFormatter(new ITimeFormatter() {
                                @NotNull
                                @Override
                                public String getFormattedTimeText(Calendar calendar) {
                                    return getFormattedDate(unixTimeStamp);
                                }
                            })
                            .hideIcon(true)
                            .build();
                    mChatView.receive(message);
                    Log.e("APP", "to num from recieve: " + messagesClass.getFromNum());
                }



            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        DatabaseReference database = FirebaseDatabase.getInstance().getReference().child("messages").child(getuserPhoneNumber());

//        database.addListenerForSingleValueEvent(valueEventListener);
        database.addChildEventListener(childEventListener);
    }

    private String getFormattedDate(long unixTimeSTamp)
    {
        Date date = new Date(unixTimeSTamp*1000);

        Log.e("App time stamp: ", "    date:    " + date.toString());
        SimpleDateFormat jdf = new SimpleDateFormat("hh:mm a", Locale.ENGLISH);
        jdf.setTimeZone(TimeZone.getTimeZone("GMT+0530"));
        String java_date = jdf.format(date);

        Log.e("App time stamp: ", "    formatted date:    " + java_date);

        return java_date;
    }


   private String getuserPhoneNumber()
   {
        SharedPreferences settings = getApplicationContext().getSharedPreferences("Shared", 0);
       Log.e("App", "Userphone: " + settings.getString("phoneNumber", null));
        return settings.getString("phoneNumber", null);
   }

}
