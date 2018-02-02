package com.example.dell.commonroom;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Dell on 01-02-2018.
 */

public class FriendListAdapter extends ArrayAdapter<String> {

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View listView = convertView;
        if(listView == null)
        {
            listView = LayoutInflater.from(getContext()).inflate((R.layout.friend_list_item),parent,false);

        }
        String value = getItem(position);
        TextView textView = listView.findViewById(R.id.nameDisplay);
        textView.setText(value);
        return listView;
    }

    public FriendListAdapter(Activity context, ArrayList<String> names) {super(context, 0 , names);}


}
