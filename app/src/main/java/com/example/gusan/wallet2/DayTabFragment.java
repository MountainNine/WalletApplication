package com.example.gusan.wallet2;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Color;
import android.icu.util.Calendar;
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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.text.SimpleDateFormat;

/**
 * Created by gusan on 2017. 1. 3..
 */

public class DayTabFragment extends Fragment {

    ViewGroup rootView;
    Intent intent;
    ListView listView;

    DataBaseFunc mHelper;
    static SQLiteDatabase db;
    static Cursor cursor;
    static MyCursorAdapter myAdapter;

    java.util.Calendar date = java.util.Calendar.getInstance();
    SimpleDateFormat sdfNow = new SimpleDateFormat("yyyy-MM-dd");
    String formatDate = sdfNow.format(date.getTime());
    TextView dateNow;
    ImageView img_date_up;
    ImageView img_date_down;
    Button btn_today;

    final static String KEY_ID = "_id";
    final static String KEY_DESCRIPTION = "description";
    final static String KEY_MONEY = "money";
    final static String KEY_INEX="inex";
    final static String KEY_DIVIDE="divide";
    final static String KEY_DATE="date";

    final static String TABLE_NAME = "myTable";
    final static String querySelectAll = String.format( "SELECT * FROM %s", TABLE_NAME);

    TextView txt_income;
    TextView txt_expedicure;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        rootView = (ViewGroup) inflater.inflate(R.layout.day_tab_fragment, container, false);
        listView = (ListView) getActivity().findViewById(R.id.lv_description_money);

        FloatingActionButton cmd_insert_money = (FloatingActionButton) rootView.findViewById(R.id.insert_money);
        cmd_insert_money.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), InsertMoney.class);
                startActivity(intent);
            }
        });

        FloatingActionButton cmd_calendar = (FloatingActionButton)rootView.findViewById(R.id.calendar);
        cmd_calendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(getActivity(), CalendarActivity.class);
                startActivity(intent);
            }
        });


        return rootView;
    }

    @Override
    public void onResume()
    {
        super.onResume();

        listView = (ListView) getActivity().findViewById(R.id.lv_description_money);

        mHelper = new DataBaseFunc(getActivity());
        db=mHelper.getWritableDatabase();

        cursor = db.rawQuery(querySelectAll, null);
        myAdapter = new MyCursorAdapter(getActivity(),cursor);

        dateNow = (TextView) rootView.findViewById(R.id.dateNow);

        listView.setAdapter(myAdapter);

        txt_income = (TextView) getActivity().findViewById(R.id.txt_income);
        txt_expedicure = (TextView)getActivity().findViewById(R.id.txt_expedicure);

        img_date_up=(ImageView)rootView.findViewById(R.id.img_date_up);
        img_date_up.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                date.add(java.util.Calendar.DATE,1);
                dateNow.setText(sdfNow.format(date.getTime()));
                Log.d("dateNow 확인", sdfNow.format(date.getTime()));
                cursor=db.rawQuery(String.format("SELECT * FROM myTable WHERE date = %s", dateNow.getText()), null);
                Cursor cursor2 = db.rawQuery(String.format("SELECT SUM(money) FROM myTable WHERE inex = 1 AND date = %s", dateNow.getText()), null);
                while(cursor2.moveToNext())
                {
                    String sumIncome = cursor2.getString(0);
                    txt_income.setText(sumIncome);
                }
                Cursor cursor3 = db.rawQuery(String.format("SELECT SUM(money) FROM myTable WHERE inex = 0 AND date = %s", dateNow.getText()), null);
                while(cursor3.moveToNext())
                {
                    String sumExpedicure = cursor3.getString(0);
                    txt_expedicure.setText(sumExpedicure);
                }
                myAdapter.changeCursor(cursor);
            }
        });

        btn_today=(Button)rootView.findViewById(R.id.btn_today);
        btn_today.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                dateNow.setText(sdfNow.format(java.util.Calendar.getInstance().getTime()));
                date= java.util.Calendar.getInstance();
                cursor=db.rawQuery(String.format("SELECT * FROM myTable WHERE date = %s", dateNow.getText()), null);
                Cursor cursor2 = db.rawQuery(String.format("SELECT SUM(money) FROM myTable WHERE inex = 1 AND date = %s", dateNow.getText()), null);
                while(cursor2.moveToNext())
                {
                    String sumIncome = cursor2.getString(0);
                    txt_income.setText(sumIncome);
                }
                Cursor cursor3 = db.rawQuery(String.format("SELECT SUM(money) FROM myTable WHERE inex = 0 AND date = %s", dateNow.getText()), null);
                while(cursor3.moveToNext())
                {
                    String sumExpedicure = cursor3.getString(0);
                    txt_expedicure.setText(sumExpedicure);
                }
                myAdapter.changeCursor(cursor);
            }
        });

        img_date_down=(ImageView)rootView.findViewById(R.id.img_date_down);
        img_date_down.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                date.add(java.util.Calendar.DATE,-1);
                dateNow.setText(sdfNow.format(date.getTime()));
                Log.d("dateNow 확인", sdfNow.format(date.getTime()));
                cursor=db.rawQuery(String.format("SELECT * FROM myTable WHERE date = %s", dateNow.getText()), null);
                Cursor cursor2 = db.rawQuery(String.format("SELECT SUM(money) FROM myTable WHERE inex = 1 AND date = %s", dateNow.getText()), null);
                while(cursor2.moveToNext())
                {
                    String sumIncome = cursor2.getString(0);
                    txt_income.setText(sumIncome);
                }
                Cursor cursor3 = db.rawQuery(String.format("SELECT SUM(money) FROM myTable WHERE inex = 0 AND date = %s", dateNow.getText()), null);
                while(cursor3.moveToNext())
                {
                    String sumExpedicure = cursor3.getString(0);
                    txt_expedicure.setText(sumExpedicure);
                }
                myAdapter.changeCursor(cursor);
            }
        });

        dateNow.setText(formatDate);
        cursor=db.rawQuery(String.format("SELECT * FROM myTable WHERE date = %s", dateNow.getText()), null);
        Cursor cursor2 = db.rawQuery(String.format("SELECT SUM(money) FROM myTable WHERE inex = 1 AND date = %s", dateNow.getText()), null);
        while(cursor2.moveToNext())
        {
            String sumIncome = cursor2.getString(0);
            txt_income.setText(sumIncome);
        }
        Cursor cursor3 = db.rawQuery(String.format("SELECT SUM(money) FROM myTable WHERE inex = 0 AND date = %s", dateNow.getText()), null);
        while(cursor3.moveToNext())
        {
            String sumExpedicure = cursor3.getString(0);
            txt_expedicure.setText(sumExpedicure);
        }
        myAdapter.changeCursor(cursor);


    }
}

