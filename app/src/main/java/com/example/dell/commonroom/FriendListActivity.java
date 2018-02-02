package com.example.dell.commonroom;

import android.app.ListActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;


public class FriendListActivity extends AppCompatActivity {

    private HashMap<String, String> friendList;
    ArrayList<String> names, numbers ;
    private ListView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_list);
        listView = findViewById(R.id.friend_list_view);
        friendList = new HashMap<>();
        numbers = new ArrayList<>();
        names = new ArrayList<>();
        getFriendList();
            refrestListView();

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    String number = numbers.get(i);
                    Intent intent = new Intent(FriendListActivity.this, TalkActivity.class);
                    intent.putExtra("RecipientNumber", number);
                    startActivity(intent);
                }
            });
    }

    private void refrestListView()
    {
        FriendListAdapter adapter = new FriendListAdapter(this, names);//new ArrayList(friendList.values()));
        listView.setAdapter(adapter);
    }

    private void getFriendList()
    {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("users");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot: dataSnapshot.getChildren())
                {
                    if(!snapshot.getKey().equals(getuserPhoneNumber()))
                    {
                        friendList.put(snapshot.getKey(),snapshot.getValue().toString());
                        numbers.add(snapshot.getKey());
                        names.add(snapshot.getValue().toString());
                    }


                }
                Log.e("App", "Usernum: " + getuserPhoneNumber());
                refrestListView();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private String getuserPhoneNumber()
    {
        SharedPreferences settings = getApplicationContext().getSharedPreferences("Shared", 0);
        Log.e("App", "Userphone: " + settings.getString("phoneNumber", null));
        return settings.getString("phoneNumber", null);
    }
}
