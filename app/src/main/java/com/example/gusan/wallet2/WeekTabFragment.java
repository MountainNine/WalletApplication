package com.example.gusan.wallet2;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.handstudio.android.hzgrapherlib.animation.GraphAnimation;
import com.handstudio.android.hzgrapherlib.graphview.LineGraphView;
import com.handstudio.android.hzgrapherlib.vo.GraphNameBox;
import com.handstudio.android.hzgrapherlib.vo.linegraph.LineGraph;
import com.handstudio.android.hzgrapherlib.vo.linegraph.LineGraphVO;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gusan on 2017. 1. 3..
 */

public class WeekTabFragment extends Fragment
{

    ViewGroup rootView;
    RelativeLayout layoutGraph;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        super.onCreateView(inflater, container, savedInstanceState);

        rootView = (ViewGroup) inflater.inflate(R.layout.week_tab_fragment, container, false);
        layoutGraph = (RelativeLayout) rootView.findViewById(R.id.line_graph);
        setLineGraph();
        return rootView;
    }

    private void setLineGraph()
    {
        LineGraphVO vo = makeLineGraphAllSetting();
        layoutGraph.addView(new LineGraphView(getActivity(), vo));
    }

    private LineGraphVO makeLineGraphAllSetting()
    {
        String[] legendArr = {"1", "2", "3", "4", "5"};
        float[] graph1 = {500, 100, 300, 200, 100};
        float[] graph2 = {000, 100, 200, 100, 200};
        float[] graph3 = {200, 500, 300, 400, 000};

        List<LineGraph> arrGraph = new ArrayList<LineGraph>();
        arrGraph.add(new LineGraph("android", 0xaa66ff33, graph1));
        arrGraph.add(new LineGraph("ios", 0xaa00ffff, graph2));
        arrGraph.add(new LineGraph("tizen", 0xaaff0066, graph3));
        LineGraphVO vo = new LineGraphVO(legendArr, arrGraph);

        vo.setAnimation(new GraphAnimation(GraphAnimation.LINEAR_ANIMATION, GraphAnimation.DEFAULT_DURATION));
        vo.setGraphNameBox(new GraphNameBox());
        return vo;
    }
}
