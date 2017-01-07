package com.example.gusan.wallet2;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.CursorAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Created by gusan on 2017. 1. 3..
 */

public class DayTabFragment extends Fragment {
    ViewGroup rootView;
    Intent intent;
    ListView listView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        rootView = (ViewGroup) inflater.inflate(R.layout.day_tab_fragment, container, false);
        listView = (ListView) rootView.findViewById(R.id.lv_description_money);


        FloatingActionButton button = (FloatingActionButton) rootView.findViewById(R.id.insert_money);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), InsertMoney.class);
                startActivity(intent);
            }
        });
        return rootView;
    }
}