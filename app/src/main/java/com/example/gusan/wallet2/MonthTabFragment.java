package com.example.gusan.wallet2;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.handstudio.android.hzgrapherlib.animation.GraphAnimation;
import com.handstudio.android.hzgrapherlib.graphview.CircleGraphView;
import com.handstudio.android.hzgrapherlib.vo.Graph;
import com.handstudio.android.hzgrapherlib.vo.GraphNameBox;
import com.handstudio.android.hzgrapherlib.vo.circlegraph.CircleGraph;
import com.handstudio.android.hzgrapherlib.vo.circlegraph.CircleGraphVO;

import java.util.ArrayList;
import java.util.List;

import static com.handstudio.android.hzgrapherlib.vo.Graph.DEFAULT_MARGIN_RIGHT;
import static com.handstudio.android.hzgrapherlib.vo.Graph.DEFAULT_MARGIN_TOP;
import static com.handstudio.android.hzgrapherlib.vo.Graph.DEFAULT_PADDING;

/**
 * Created by gusan on 2017. 1. 3..
 */

public class MonthTabFragment extends Fragment
{
    RelativeLayout layoutGraph;
    ViewGroup rootView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        super.onCreateView(inflater, container, savedInstanceState);

        rootView = (ViewGroup)inflater.inflate(R.layout.month_tab_fragment, container, false);
        layoutGraph=(RelativeLayout)rootView.findViewById(R.id.pie_graph);
        setCircleGraph();
        return rootView;
    }

    private void setCircleGraph()
    {
        CircleGraphVO vo = makeLineGraphAllSetting();
        layoutGraph.addView(new CircleGraphView(getActivity(), vo));
    }

    private CircleGraphVO makeLineGraphAllSetting()
    {
        int radius = 130;

        List<CircleGraph> arrGraph = new ArrayList<CircleGraph>();
        arrGraph.add(new CircleGraph("android", Color.parseColor("#3366CC"), 1));
        arrGraph.add(new CircleGraph("IOS", Color.parseColor("#DC3912"), 1));
        arrGraph.add(new CircleGraph("tizen", Color.parseColor("#FF9900"), 1));
        arrGraph.add(new CircleGraph("HTML", Color.parseColor("#109618"), 1));
        arrGraph.add(new CircleGraph("C", Color.parseColor("#990099"), 3));

        CircleGraphVO vo = new CircleGraphVO(DEFAULT_PADDING, DEFAULT_PADDING, DEFAULT_PADDING, DEFAULT_PADDING,
                DEFAULT_MARGIN_TOP, DEFAULT_MARGIN_RIGHT, radius, arrGraph);
        vo.setLineColor(Color.WHITE);
        vo.setTextColor(Color.WHITE);
        vo.setTextSize(20);

        vo.setCenterX(0);
        vo.setCenterY(0);

        vo.setAnimation(new GraphAnimation(GraphAnimation.LINEAR_ANIMATION, 2000));
        vo.setPieChart(true);

        GraphNameBox graphNameBox = new GraphNameBox();
        graphNameBox.setNameboxMarginTop(25);
        graphNameBox.setNameboxMarginRight(25);

        vo.setGraphNameBox(graphNameBox);

        return vo;

    }
}

