package com.example.gusan.wallet2;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by gusan on 2017. 1. 10..
 */

class MyCursorAdapter extends android.support.v4.widget.CursorAdapter
{
    @SuppressWarnings("drprecation")
    public MyCursorAdapter(Context context, Cursor c)
    {
        super(context,c);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor)
    {
        TextView txt_description = (TextView)view.findViewById(R.id.txt_description_2);
        TextView txt_money = (TextView)view.findViewById(R.id.txt_money_2);

        String description = cursor.getString(cursor.getColumnIndex("description"));
        String money = cursor.getString(cursor.getColumnIndex("money"));
        int inex = cursor.getInt(cursor.getColumnIndex("inex"));

        if(inex==1)
        {
            txt_money.setTextColor(Color.GREEN);
        }
        else if(inex==0)
        {
            txt_money.setTextColor(Color.RED);
        }
        txt_description.setText(description);
        txt_money.setText(money);

    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent)
    {
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.listview_custom, parent, false);
        return v;
    }
}