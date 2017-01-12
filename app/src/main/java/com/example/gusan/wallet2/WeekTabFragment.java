package com.example.gusan.wallet2;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.handstudio.android.hzgrapherlib.animation.GraphAnimation;
import com.handstudio.android.hzgrapherlib.graphview.LineGraphView;
import com.handstudio.android.hzgrapherlib.vo.GraphNameBox;
import com.handstudio.android.hzgrapherlib.vo.linegraph.LineGraph;
import com.handstudio.android.hzgrapherlib.vo.linegraph.LineGraphVO;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/**
 * Created by gusan on 2017. 1. 3..
 */

public class WeekTabFragment extends Fragment {
    ViewGroup rootView;
    RelativeLayout layoutGraph;
    Calendar date = Calendar.getInstance();
    SimpleDateFormat sdfNow = new SimpleDateFormat("MM월 W번째 주");
    SimpleDateFormat sdfWeek = new SimpleDateFormat("W번째 주");
    String formatDate = sdfNow.format(date.getTime());
    String formatWeek = sdfWeek.format(date.getTime());
    TextView dateNow;
    ImageView img_date_up;
    ImageView img_date_down;
    Button btn_today;
    TextView txt_formatWeek;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        rootView = (ViewGroup) inflater.inflate(R.layout.week_tab_fragment, container, false);
        layoutGraph = (RelativeLayout) rootView.findViewById(R.id.line_graph);

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();

        txt_formatWeek=(TextView)rootView.findViewById(R.id.txt_formatWeek);
        dateNow = (TextView) rootView.findViewById(R.id.dateNow);
        img_date_up = (ImageView) rootView.findViewById(R.id.img_date_up);
        img_date_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                date.add(java.util.Calendar.WEEK_OF_MONTH, 1);
                dateNow.setText(sdfNow.format(date.getTime()));
                txt_formatWeek.setText(sdfWeek.format(date.getTime()));
                Log.d("dateNow 확인", sdfNow.format(date.getTime()));
                layoutGraph.removeAllViews();
                setLineGraph();
            }
        });

        img_date_down = (ImageView) rootView.findViewById(R.id.img_date_down);
        img_date_down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                date.add(Calendar.WEEK_OF_MONTH, -1);
                dateNow.setText(sdfNow.format(date.getTime()));
                txt_formatWeek.setText(sdfWeek.format(date.getTime()));
                Log.d("dateNow 확인", sdfNow.format(date.getTime()));
                layoutGraph.removeAllViews();
                setLineGraph();
            }
        });

        btn_today = (Button) rootView.findViewById(R.id.btn_today);
        btn_today.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dateNow.setText(sdfNow.format(java.util.Calendar.getInstance().getTime()));
                txt_formatWeek.setText(sdfWeek.format(Calendar.getInstance().getTime()));
                date = java.util.Calendar.getInstance();
                layoutGraph.removeAllViews();
                setLineGraph();

            }
        });

        txt_formatWeek.setText(formatWeek);
        dateNow.setText(formatDate);
        setLineGraph();

    }

    public void setLineGraph()
    {
        LineChart lineChart = MakeLineGraph();
        lineChart.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        layoutGraph.addView(lineChart);
    }
    public LineChart MakeLineGraph()
    {
        SQLiteDatabase db;
        DataBaseFunc mHelper;

        mHelper = new DataBaseFunc(getActivity());
        db=mHelper.getWritableDatabase();

        LineChart chart = new LineChart(getActivity());

        ArrayList<Entry> entries = new ArrayList<>();
        ArrayList<Entry> entries2 = new ArrayList<>();

        Cursor cursor_sun_income = db.rawQuery(String.format("SELECT SUM(money) FROM myTable WHERE inex = 1 AND week = '%s 일요일'", txt_formatWeek.getText()), null);
        while(cursor_sun_income.moveToNext())
        {
            entries.add(new Entry(0, cursor_sun_income.getFloat(0)));
        }
        Cursor cursor_mon_income = db.rawQuery(String.format("SELECT SUM(money) FROM myTable WHERE inex=1 AND week = '%s 월요일'", txt_formatWeek.getText()), null);
        while(cursor_mon_income.moveToNext())
        {
            entries.add(new Entry(1, cursor_mon_income.getFloat(0)));
        }
        Cursor cursor_tus_income = db.rawQuery(String.format("SELECT SUM(money) FROM myTable WHERE inex=1 AND week = '%s 화요일'", txt_formatWeek.getText()), null);
        while(cursor_tus_income.moveToNext())
        {
            entries.add(new Entry(2, cursor_tus_income.getFloat(0)));
        }
        Cursor cursor_wed_income = db.rawQuery(String.format("SELECT SUM(money) FROM myTable WHERE inex=1 AND week = '%s 수요일'", txt_formatWeek.getText()), null);
        while(cursor_wed_income.moveToNext())
        {
            entries.add(new Entry(3, cursor_wed_income.getFloat(0)));
        }
        Cursor cursor_tur_income = db.rawQuery(String.format("SELECT SUM(money) FROM myTable WHERE inex=1 AND week = '%s 목요일'", txt_formatWeek.getText()), null);
        while(cursor_tur_income.moveToNext())
        {
            entries.add(new Entry(4, cursor_tur_income.getFloat(0)));
        }
        Cursor cursor_fri_income = db.rawQuery(String.format("SELECT SUM(money) FROM myTable WHERE inex=1 AND week = '%s 금요일'", txt_formatWeek.getText()), null);
        while(cursor_fri_income.moveToNext())
        {
            entries.add(new Entry(5, cursor_fri_income.getFloat(0)));
        }
        Cursor cursor_sat_income = db.rawQuery(String.format("SELECT SUM(money) FROM myTable WHERE inex=1 AND week = '%s 토요일'", txt_formatWeek.getText()), null);
        while(cursor_sat_income.moveToNext())
        {
            entries.add(new Entry(6, cursor_sat_income.getFloat(0)));
        }


        Cursor cursor_sun_ex = db.rawQuery(String.format("SELECT SUM(money) FROM myTable WHERE inex = 0 AND week = '%s 일요일'", txt_formatWeek.getText()), null);
        while(cursor_sun_ex.moveToNext())
        {
            entries2.add(new Entry(0, cursor_sun_ex.getFloat(0)));
        }
        Cursor cursor_mon_ex = db.rawQuery(String.format("SELECT SUM(money) FROM myTable WHERE inex=0 AND week = '%s 월요일'", txt_formatWeek.getText()), null);
        while(cursor_mon_ex.moveToNext())
        {
            entries2.add(new Entry(1, cursor_mon_ex.getFloat(0)));
        }
        Cursor cursor_tus_ex = db.rawQuery(String.format("SELECT SUM(money) FROM myTable WHERE inex=0 AND week = '%s 화요일'", txt_formatWeek.getText()), null);
        while(cursor_tus_ex.moveToNext())
        {
            entries2.add(new Entry(2, cursor_tus_ex.getFloat(0)));
        }
        Cursor cursor_wed_ex = db.rawQuery(String.format("SELECT SUM(money) FROM myTable WHERE inex=0 AND week = '%s 수요일'", txt_formatWeek.getText()), null);
        while(cursor_wed_ex.moveToNext())
        {
            entries2.add(new Entry(3, cursor_wed_ex.getFloat(0)));
        }
        Cursor cursor_tur_ex = db.rawQuery(String.format("SELECT SUM(money) FROM myTable WHERE inex=0 AND week = '%s 목요일'", txt_formatWeek.getText()), null);
        while(cursor_tur_ex.moveToNext())
        {
            entries2.add(new Entry(4, cursor_tur_ex.getFloat(0)));
        }
        Cursor cursor_fri_ex = db.rawQuery(String.format("SELECT SUM(money) FROM myTable WHERE inex=0 AND week = '%s 금요일'", txt_formatWeek.getText()), null);
        while(cursor_fri_ex.moveToNext())
        {
            entries2.add(new Entry(5, cursor_fri_ex.getFloat(0)));
        }
        Cursor cursor_sat_ex = db.rawQuery(String.format("SELECT SUM(money) FROM myTable WHERE inex=0 AND week = '%s 토요일'", txt_formatWeek.getText()), null);
        while(cursor_sat_ex.moveToNext())
        {
            entries2.add(new Entry(6, cursor_sat_ex.getFloat(0)));
        }


        LineDataSet dataSet = new LineDataSet(entries, "수입");
        LineDataSet dataSet2 = new LineDataSet(entries2, "지출");
        dataSet.setColor(Color.GREEN);
        dataSet2.setColor(Color.RED);

        List<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(dataSet);
        dataSets.add(dataSet2);

        final String[] quarters = new String[] {"일", "월", "화", "수", "목", "금", "토"};
        IAxisValueFormatter formatter = new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return quarters[(int) value];
            }
        };

        XAxis xAxis = chart.getXAxis();
        xAxis.setGranularity(0);
        xAxis.setValueFormatter(formatter);

        LineData data = new LineData(dataSets);
        chart.setData(data);

        return chart;
    }
}