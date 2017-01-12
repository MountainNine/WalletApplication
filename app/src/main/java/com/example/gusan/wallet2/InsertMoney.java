package com.example.gusan.wallet2;

import android.app.ActionBar;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by gusan on 2017. 1. 4..
 */

public class InsertMoney extends Activity{

    Calendar date = Calendar.getInstance();
    SimpleDateFormat sdfNow = new SimpleDateFormat("yyyy-MM-dd", Locale.KOREA);
    SimpleDateFormat sdfMonth = new SimpleDateFormat("yyyy-MM", Locale.KOREA);
    SimpleDateFormat sdfWeek = new SimpleDateFormat("W번째 주 E요일", Locale.KOREA);
    String formatWeek = sdfWeek.format(date.getTime());
    String formatMonth = sdfMonth.format(date.getTime());
    String formatDate = sdfNow.format(date.getTime());

    EditText txt_description;
    EditText txt_money;

    TextView dateNow;
    TextView txt_week;
    TextView txt_month;
    static ImageView img_check;

    Intent setIntent = new Intent(this, DayTabFragment.class);
    Intent getIntent = getIntent();

    static RadioButton opt_income;
    static RadioButton opt_expedicure;
    RadioGroup group;
    Spinner spi_divide;

    ImageView img_date_up;
    ImageView img_date_down;

    Button btn_today;
    Button btn_calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);

        txt_description = (EditText) findViewById(R.id.txt_description);
        txt_money = (EditText) findViewById(R.id.txt_money);

        opt_income=(RadioButton)findViewById(R.id.opt_income);
        opt_expedicure=(RadioButton)findViewById(R.id.opt_expedicure);
        spi_divide=(Spinner)findViewById(R.id.spi_divide);
        group = (RadioGroup)findViewById(R.id.opt_group);
        dateNow = (TextView) findViewById(R.id.dateNow);

        txt_month=(TextView)findViewById(R.id.txt_month);
        txt_week=(TextView)findViewById(R.id.txt_week);

        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
                    if(checkedId==R.id.opt_income) {
                        ArrayAdapter adapter = ArrayAdapter.createFromResource(InsertMoney.super.getApplicationContext(), R.array.item_income, android.R.layout.simple_spinner_item);
                        adapter.setDropDownViewResource(R.layout.spinner_item);
                        spi_divide.setAdapter(adapter);
                        spi_divide.setOnItemSelectedListener(new MyOnItemSelectedListener());
                    }
                    else if(checkedId==R.id.opt_expedicure) {
                        ArrayAdapter adapter = ArrayAdapter.createFromResource(InsertMoney.super.getApplicationContext(), R.array.item_expedicure, android.R.layout.simple_spinner_item);
                        adapter.setDropDownViewResource(R.layout.spinner_item);
                        spi_divide.setAdapter(adapter);
                        spi_divide.setOnItemSelectedListener(new MyOnItemSelectedListener());
                    }
            }
        });

        img_date_up=(ImageView)findViewById(R.id.img_date_up);
        img_date_up.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                date.add(Calendar.DATE,1);
                dateNow.setText(sdfNow.format(date.getTime()));
                txt_month.setText(sdfMonth.format(date.getTime()));
                txt_week.setText(sdfWeek.format(date.getTime()));
            }
        });

        btn_today=(Button)findViewById(R.id.btn_today);
        btn_today.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                dateNow.setText(sdfNow.format(Calendar.getInstance().getTime()));
                txt_month.setText(sdfMonth.format(Calendar.getInstance().getTime()));
                txt_week.setText(sdfWeek.format(Calendar.getInstance().getTime()));
                date=Calendar.getInstance();
            }
        });

        img_date_down=(ImageView)findViewById(R.id.img_date_down);
        img_date_down.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                date.add(Calendar.DATE,-1);
                dateNow.setText(sdfNow.format(date.getTime()));
                txt_month.setText(sdfMonth.format(date.getTime()));
                txt_week.setText(sdfWeek.format(date.getTime()));
            }
        });

        img_check = (ImageView)findViewById(R.id.img_check);
        img_check.setOnClickListener(new View.OnClickListener() {
                                         @Override
                                         public void onClick(View v) {
                                             String description = txt_description.getText().toString();
                                             int money = Integer.parseInt(txt_money.getText().toString());
                                             boolean inex = opt_income.isChecked();
                                             String divide = spi_divide.getSelectedItem().toString();
                                             String date = dateNow.getText().toString();
                                             String month=txt_month.getText().toString();
                                             String week=txt_week.getText().toString();
                                             String query = String.format("INSERT INTO %s VALUES ( null, '%s', %d, %d, '%s', %s, %s, '%s' );", DayTabFragment.TABLE_NAME, description, money, (inex) ? 1 : 0, divide, date, month, week);
                                             Log.d("insert 확인", query);
                                             DayTabFragment.db.execSQL(query);

                                             DayTabFragment.cursor = DayTabFragment.db.rawQuery(DayTabFragment.querySelectAll, null);
                                             DayTabFragment.myAdapter.changeCursor(DayTabFragment.cursor);
                                             setIntent.putExtra("description", description);
                                             setIntent.putExtra("money", money);

                                             finish();
                                         }
                                     });

        dateNow.setText(formatDate);
        txt_month.setText(formatMonth);
        txt_week.setText(formatWeek);
    }

    private class MyOnItemSelectedListener implements AdapterView.OnItemSelectedListener
    {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id)
        {
            ((TextView)parent.getChildAt(0)).setTextColor(Color.BLACK);
        }
        @Override
        public void onNothingSelected(AdapterView parent)
        {

        }
    }
}
