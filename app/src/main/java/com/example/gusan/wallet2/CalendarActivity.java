package com.example.gusan.wallet2;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by gusan on 2017. 1. 7..
 */

public class CalendarActivity extends Activity {
    private GridAdapter gridAdapter;
    private ArrayList<String> dayList;
    private GridView gridView;
    private Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        gridView=(GridView)findViewById(R.id.gv_calendar);

        long now = System.currentTimeMillis();
        final Date date = new Date(now);
        final SimpleDateFormat curYearFormat = new SimpleDateFormat("yyyy", Locale.KOREA);
        final SimpleDateFormat curMonthFormat = new SimpleDateFormat("MM", Locale.KOREA);
        final SimpleDateFormat curDayFormat = new SimpleDateFormat("dd", Locale.KOREA);

        dayList = new ArrayList<String>();
        dayList.add("일");
        dayList.add("월");
        dayList.add("화");
        dayList.add("수");
        dayList.add("목");
        dayList.add("금");
        dayList.add("토");

        calendar = Calendar.getInstance();
        calendar.set(Integer.parseInt(curYearFormat.format(date)), Integer.parseInt(curMonthFormat.format(date))-1, 1);
        int dayNum = calendar.get(Calendar.DAY_OF_WEEK);
        for(int i=1; i<dayNum; i++)
        {
            dayList.add("");
        }
        setCalendarDate(calendar.get(Calendar.MONTH)+1);

        gridAdapter=new GridAdapter(getApplicationContext(), dayList);
        gridView.setAdapter(gridAdapter);
    }

    private void setCalendarDate(int month)
    {
        calendar.set(Calendar.MONTH, month-1);

        for(int i=0; i<calendar.getActualMaximum(Calendar.DAY_OF_MONTH); i++)
        {
            dayList.add(""+(i+1));
        }
    }

    private class GridAdapter extends BaseAdapter
    {
        private final List<String> list;
        private final LayoutInflater inflater;

        public GridAdapter(Context context, List<String> list)
        {
            this.list = list;
            this.inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount()
        {
            return list.size();
        }

        @Override
        public String getItem(int position)
        {
            return list.get(position);
        }

        @Override
        public long getItemId(int position)
        {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {
            ViewHolder holder = null;
            if(convertView==null)
            {
                convertView=inflater.inflate(R.layout.item_calendar_gridview,parent,false);
                holder=new ViewHolder();

                holder.tvItemGridView=(TextView)convertView.findViewById(R.id.txt_item_gridview);
                convertView.setTag(holder);
            }
            else
            {
                holder=(ViewHolder)convertView.getTag();
            }
            holder.tvItemGridView.setText("" + getItem(position));

            calendar = Calendar.getInstance();
            Integer today = calendar.get(Calendar.DAY_OF_MONTH);
            String sToday = String.valueOf(today);
            if(sToday.equals(getItem(position)))
            {
                holder.tvItemGridView.setTextColor(Color.BLACK);
            }
            return convertView;
        }
    }

    private class ViewHolder
    {
        TextView tvItemGridView;
    }
}
