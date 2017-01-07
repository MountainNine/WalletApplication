package com.example.gusan.wallet2;

import android.app.ActionBar;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by gusan on 2017. 1. 4..
 */

public class InsertMoney extends Activity{

    long now = System.currentTimeMillis();
    Date date = new Date(now);
    SimpleDateFormat sdfNow = new SimpleDateFormat("yyyy-MM-dd");
    String formatDate = sdfNow.format(date);

    EditText txt_description;
    EditText txt_money;

    TextView dateNow;
    static ImageView img_check;

    Intent intent = new Intent(this, DayTabFragment.class);

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);

        txt_description = (EditText) findViewById(R.id.txt_description);
        txt_money = (EditText) findViewById(R.id.txt_money);


        img_check = (ImageView)findViewById(R.id.img_check);
        img_check.setOnClickListener(new View.OnClickListener() {
                                         @Override
                                         public void onClick(View v) {
                                             String description = txt_description.getText().toString();
                                             int money = Integer.parseInt(txt_money.getText().toString());

                                             intent.putExtra("description", description);
                                             intent.putExtra("money", money);
                                             finish();
                                         }
                                     });

        dateNow = (TextView) findViewById(R.id.dateNow);
        dateNow.setText(formatDate);
    }


}
