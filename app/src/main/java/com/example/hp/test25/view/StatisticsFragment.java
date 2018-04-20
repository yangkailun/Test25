package com.example.hp.test25.view;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hp.test25.R;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.MPPointF;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class StatisticsFragment extends Fragment {

    private PieChart incomePieChart;

    public StatisticsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_statistics,container,false);

        incomePieChart = view.findViewById(R.id.income_chart);
        incomePieChart.setUsePercentValues(true);
        incomePieChart.getDescription().setEnabled(false);
        incomePieChart.setExtraOffsets(5,10,5,5);

        incomePieChart.setDragDecelerationFrictionCoef(0.95f);

        //incomePieChart.setCenterTextTypeface(mTfLight);
        incomePieChart.setCenterText("收支");
        incomePieChart.setDrawHoleEnabled(true);
        incomePieChart.setHoleColor(Color.WHITE);
        incomePieChart.setTransparentCircleColor(Color.WHITE);
        incomePieChart.setTransparentCircleAlpha(110);

        incomePieChart.setHoleRadius(58f);
        incomePieChart.setTransparentCircleRadius(61f);

        incomePieChart.setDrawCenterText(true);
        incomePieChart.setRotationAngle(0);

        incomePieChart.setRotationEnabled(true);
        incomePieChart.setHighlightPerTapEnabled(true);

        setData(4,100);


        return view;
    }

    private void setData(int count, float range){
        float mult = range;
        ArrayList<PieEntry> entries = new ArrayList<PieEntry>();
        entries.add(new PieEntry((float)(Math.random()*mult)+mult/5));
        PieDataSet dataSet = new PieDataSet(entries,"Election Results");
        dataSet.setDrawIcons(false);
        dataSet.setSliceSpace(3f);
        dataSet.setIconsOffset(new MPPointF(0,40));
        dataSet.setSelectionShift(5f);

        ArrayList<Integer> colors = new ArrayList<Integer>();

        for(int c : ColorTemplate.VORDIPLOM_COLORS){
            colors.add(c);
        }
        for(int c : ColorTemplate.JOYFUL_COLORS){
            colors.add(c);
        }
        for (int c : ColorTemplate.COLORFUL_COLORS){
            colors.add(c);
        }
        for (int c : ColorTemplate.LIBERTY_COLORS){
            colors.add(c);
        }
        for (int c : ColorTemplate.PASTEL_COLORS){
            colors.add(c);
        }
        colors.add(ColorTemplate.getHoloBlue());
        dataSet.setColors(colors);

        //PieData data
    }

}
