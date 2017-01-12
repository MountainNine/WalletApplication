package com.example.gusan.wallet2;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by gusan on 2017. 1. 10..
 */

public class DataBaseFunc extends SQLiteOpenHelper
{
    public DataBaseFunc(Context context) {
        super(context, "MyData.db", null, 2);
    }

    public void onCreate(SQLiteDatabase db)
    {
        String query = String.format("CREATE TABLE %s ("
                + "%s INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "%s TEXT, "
                + "%s INTEGER, "
                + "%s INTEGER, "
                + "%s TEXT, "
                + "%s TEXT, "
                + "%s TEXT, "
                + "%s TEXT );", "myTable", "_id", "description", "money", "inex", "divide", "date", "month", "week" );
        String query_sum = String.format("CREATE TABLE %s ("
        + "%s INTEGER PRIMARY KEY AUTOINCREMENT, "
        + "%s INTEGER,"
        + "%s INTEGER);", "sumTable", "_id", "sumMoneyIn", "sumMoneyEx");
        db.execSQL( query );
        db.execSQL(query_sum);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        if(oldVersion!=2)
        {
            String query = String.format("ALTER TABLE %s ADD COLUMN %s INTEGER"
                    + "ADD COLUMN %s TEXT"
                    + "ADD COLUMN %s TEXT;", "myTable", "inex", "divide", "date");
            db.execSQL(query);
            onCreate(db);
        }
    }
}