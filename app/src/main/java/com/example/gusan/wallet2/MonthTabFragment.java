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
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.handstudio.android.hzgrapherlib.animation.GraphAnimation;
import com.handstudio.android.hzgrapherlib.graphview.CircleGraphView;
import com.handstudio.android.hzgrapherlib.vo.Graph;
import com.handstudio.android.hzgrapherlib.vo.GraphNameBox;
import com.handstudio.android.hzgrapherlib.vo.circlegraph.CircleGraph;
import com.handstudio.android.hzgrapherlib.vo.circlegraph.CircleGraphVO;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import static com.handstudio.android.hzgrapherlib.vo.Graph.DEFAULT_MARGIN_RIGHT;
import static com.handstudio.android.hzgrapherlib.vo.Graph.DEFAULT_MARGIN_TOP;
import static com.handstudio.android.hzgrapherlib.vo.Graph.DEFAULT_PADDING;

/**
 * Created by gusan on 2017. 1. 3..
 */

public class MonthTabFragment extends Fragment {
    RelativeLayout layoutGraph;
    ViewGroup rootView;
    java.util.Calendar date = java.util.Calendar.getInstance();
    SimpleDateFormat sdfNow = new SimpleDateFormat("yyyy-MM", Locale.KOREA);
    String formatDate = sdfNow.format(date.getTime());
    TextView dateNow;
    ImageView img_date_up;
    ImageView img_date_down;
    Button btn_today;
    ToggleButton btn_inex;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        super.onCreateView(inflater, container, savedInstanceState);

        MyCursorAdapter myAdapter;

        rootView = (ViewGroup) inflater.inflate(R.layout.month_tab_fragment, container, false);

        layoutGraph = (RelativeLayout) rootView.findViewById(R.id.pie_graph);
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();

        dateNow = (TextView) rootView.findViewById(R.id.dateNow);

        img_date_up = (ImageView) rootView.findViewById(R.id.img_date_up);
        img_date_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                date.add(java.util.Calendar.MONTH, 1);
                dateNow.setText(sdfNow.format(date.getTime()));
                Log.d("dateNow 확인", sdfNow.format(date.getTime()));
                layoutGraph.removeAllViews();
                setCircleGraph_In();
                btn_inex.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked == true) {
                            btn_inex.setText("수입");
                            layoutGraph.removeAllViews();
                            setCircleGraph_Ex();
                        } else {
                            btn_inex.setText("지출");
                            layoutGraph.removeAllViews();
                            setCircleGraph_In();
                        }
                    }
                });
            }
        });

        img_date_down = (ImageView) rootView.findViewById(R.id.img_date_down);
        img_date_down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                date.add(Calendar.MONTH, -1);
                dateNow.setText(sdfNow.format(date.getTime()));
                Log.d("dateNow 확인", sdfNow.format(date.getTime()));
                layoutGraph.removeAllViews();
                setCircleGraph_In();
                btn_inex.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked == true) {
                            btn_inex.setText("수입");
                            layoutGraph.removeAllViews();
                            setCircleGraph_Ex();
                        } else {
                            btn_inex.setText("지출");
                            layoutGraph.removeAllViews();
                            setCircleGraph_In();
                        }
                    }
                });
            }
        });
        btn_today = (Button) rootView.findViewById(R.id.btn_today);
        btn_today.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dateNow.setText(sdfNow.format(java.util.Calendar.getInstance().getTime()));
                date = java.util.Calendar.getInstance();
                layoutGraph.removeAllViews();
                setCircleGraph_In();
                btn_inex.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked == true) {
                            btn_inex.setText("수입");
                            layoutGraph.removeAllViews();
                            setCircleGraph_Ex();
                        } else {
                            btn_inex.setText("지출");
                            layoutGraph.removeAllViews();
                            setCircleGraph_In();
                        }
                    }
                });

            }
        });

        dateNow.setText(formatDate);

        setCircleGraph_In();
        btn_inex = (ToggleButton) rootView.findViewById(R.id.btn_inex);
        btn_inex.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked == true) {
                    btn_inex.setText("수입");
                    layoutGraph.removeAllViews();
                    setCircleGraph_Ex();
                } else {
                    btn_inex.setText("지출");
                    layoutGraph.removeAllViews();
                    setCircleGraph_In();
                }
            }
        });

    }

    private void setCircleGraph_In() {
        CircleGraphVO vo = makeLineGraphAllSetting_Income();
        layoutGraph.addView(new CircleGraphView(getActivity(), vo));
    }

    private void setCircleGraph_Ex() {
        CircleGraphVO vo = makeLineGraphAllSetting_Ex();
        layoutGraph.addView(new CircleGraphView(getActivity(), vo));
    }

    private CircleGraphVO makeLineGraphAllSetting_Income() {
        SQLiteDatabase db;
        DataBaseFunc mHelper;

        mHelper = new DataBaseFunc(getActivity());
        db = mHelper.getWritableDatabase();

        Cursor cursor_pin;
        Cursor cursor_salary;
        Cursor cursor_holdover;
        Cursor cursor_else;

        List<CircleGraph> arrGraph = new ArrayList<CircleGraph>();

        cursor_pin = db.rawQuery(String.format("SELECT SUM(money) FROM myTable WHERE divide = '용돈' AND month=%s", dateNow.getText()), null);
        while (cursor_pin.moveToNext()) {
            arrGraph.add(new CircleGraph("용돈", Color.parseColor("#3366CC"), cursor_pin.getInt(0)));
        }
        cursor_salary = db.rawQuery(String.format("SELECT SUM(money) FROM myTable WHERE divide = '월급' AND month=%s", dateNow.getText()), null);
        while (cursor_salary.moveToNext()) {
            arrGraph.add(new CircleGraph("월급", Color.parseColor("#DC3912"), cursor_salary.getInt(0)));
        }
        cursor_holdover = db.rawQuery(String.format("SELECT SUM(money) FROM myTable WHERE divide = '이월' AND month=%s", dateNow.getText()), null);
        while (cursor_holdover.moveToNext()) {
            arrGraph.add(new CircleGraph("이월", Color.parseColor("#FF9900"), cursor_holdover.getInt(0)));
        }
        cursor_else = db.rawQuery(String.format("SELECT SUM(money) FROM myTable WHERE divide = '기타' AND inex = 1 AND month=%s", dateNow.getText()), null);
        while (cursor_else.moveToNext()) {
            arrGraph.add(new CircleGraph("기타", Color.parseColor("#109618"), cursor_else.getInt(0)));
        }

        int radius = 130;

        CircleGraphVO vo = new CircleGraphVO(DEFAULT_PADDING, DEFAULT_PADDING, DEFAULT_PADDING, DEFAULT_PADDING,
                DEFAULT_MARGIN_TOP, DEFAULT_MARGIN_RIGHT, radius, arrGraph);
        vo.setLineColor(Color.WHITE);
        vo.setTextColor(Color.WHITE);
        vo.setTextSize(20);

        vo.setCenterX(0);
        vo.setCenterY(0);

        vo.setAnimation(new GraphAnimation(GraphAnimation.LINEAR_ANIMATION, 1000));
        vo.setPieChart(true);

        GraphNameBox graphNameBox = new GraphNameBox();
        graphNameBox.setNameboxMarginTop(25);
        graphNameBox.setNameboxMarginRight(25);

        vo.setGraphNameBox(graphNameBox);

        return vo;

    }

    public CircleGraphVO makeLineGraphAllSetting_Ex() {

        SQLiteDatabase db;
        DataBaseFunc mHelper;

        mHelper = new DataBaseFunc(getActivity());
        db = mHelper.getWritableDatabase();

        Cursor cursor_eat;
        Cursor cursor_trans;
        Cursor cursor_cul;
        Cursor cursor_need;

        List<CircleGraph> arrGraph = new ArrayList<CircleGraph>();

        cursor_eat = db.rawQuery(String.format("SELECT SUM(money) FROM myTable WHERE divide = '식비' AND month=%s", dateNow.getText()), null);
        while (cursor_eat.moveToNext()) {
            arrGraph.add(new CircleGraph("식비", Color.parseColor("#3366CC"), cursor_eat.getInt(0)));
        }
        cursor_trans = db.rawQuery(String.format("SELECT SUM(money) FROM myTable WHERE divide = '교통비' AND month=%s", dateNow.getText()), null);
        while (cursor_trans.moveToNext()) {
            arrGraph.add(new CircleGraph("교통비", Color.parseColor("#DC3912"), cursor_trans.getInt(0)));
        }
        cursor_cul = db.rawQuery(String.format("SELECT SUM(money) FROM myTable WHERE divide = '문화생활' AND month=%s", dateNow.getText()), null);
        while (cursor_cul.moveToNext()) {
            arrGraph.add(new CircleGraph("문화생활", Color.parseColor("#FF9900"), cursor_cul.getInt(0)));
        }
        cursor_need = db.rawQuery(String.format("SELECT SUM(money) FROM myTable WHERE divide = '생필품' AND month=%s", dateNow.getText()), null);
        while (cursor_need.moveToNext()) {
            arrGraph.add(new CircleGraph("생필품", Color.parseColor("#109618"), cursor_need.getInt(0)));
        }
        Cursor cursor_charge = db.rawQuery(String.format("SELECT SUM(money) FROM myTable WHERE divide = '저축' AND month=%s", dateNow.getText()), null);
        while (cursor_charge.moveToNext()) {
            arrGraph.add(new CircleGraph("저축", Color.parseColor("#FFFF33"), cursor_charge.getInt(0)));
        }
        Cursor cursor_tax = db.rawQuery(String.format("SELECT SUM(money) FROM myTable WHERE divide = '공과금' AND month=%s", dateNow.getText()), null);
        while (cursor_tax.moveToNext()) {
            arrGraph.add(new CircleGraph("공과금", Color.parseColor("#6600CC"), cursor_tax.getInt(0)));
        }
        Cursor cursor_else = db.rawQuery(String.format("SELECT SUM(money) FROM myTable WHERE divide = '기타' AND inex=0 AND month=%s", dateNow.getText()), null);
        while (cursor_else.moveToNext()) {
            arrGraph.add(new CircleGraph("기타", Color.parseColor("#33FF33"), cursor_else.getInt(0)));
        }

        int radius = 130;

        CircleGraphVO vo = new CircleGraphVO(DEFAULT_PADDING, DEFAULT_PADDING, DEFAULT_PADDING, DEFAULT_PADDING,
                DEFAULT_MARGIN_TOP, DEFAULT_MARGIN_RIGHT, radius, arrGraph);
        vo.setLineColor(Color.WHITE);
        vo.setTextColor(Color.WHITE);
        vo.setTextSize(20);

        vo.setCenterX(0);
        vo.setCenterY(0);

        vo.setAnimation(new GraphAnimation(GraphAnimation.LINEAR_ANIMATION, 1000));
        vo.setPieChart(true);

        GraphNameBox graphNameBox = new GraphNameBox();
        graphNameBox.setNameboxMarginTop(25);
        graphNameBox.setNameboxMarginRight(25);

        vo.setGraphNameBox(graphNameBox);

        return vo;
    }
}