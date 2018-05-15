package com.example.hp.test25.view;


import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.example.hp.test25.R;
import com.example.hp.test25.object.Deal;
import com.example.hp.test25.util.TimeUti;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.MPPointF;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 */
public class StatisticsFragment extends Fragment {

    protected  String[] mIncome = new String[] {
            "职业收入","投资收入","其他收入"
    };
    protected  String[] mExpense = new String[] {
            "衣","食","住","行","其他"
    };
    protected  String[] mScale = new String[] {
            "收入","支出"
    };
    private PieChart incomePieChart;

    private Button incomeButton, expenseButton, scaleButton,dateButton;

    private TextView dateTextView,balanceTextView;

    private EditText startYear,endYear,startMonth,endMonth,startDay,endDay;

    private int startTime,endTime;

    public StatisticsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_statistics,container,false);

        incomeButton = view.findViewById(R.id.income_button);
        expenseButton = view.findViewById(R.id.expense_button);
        scaleButton = view.findViewById(R.id.scale_button);
        dateButton = view.findViewById(R.id.date_button);
        dateTextView = view.findViewById(R.id.date_textview);
        balanceTextView = view.findViewById(R.id.balance);

        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateDialog();
            }
        });

        incomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initPieChart("收入情况");
                setmIncomeData();
            }
        });

        expenseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initPieChart("支出情况");
                setExpenseData();
            }
        });

        scaleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initPieChart("收支比例");
                setScaleData();
            }
        });

        SharedPreferences pref = getContext().getSharedPreferences("date",MODE_PRIVATE);
        startTime = pref.getInt("startTime",0);
        endTime = pref.getInt("endTime",0);

        dateTextView.setText(TimeUti.outPutTime(startTime)+"到"+TimeUti.outPutTime(endTime));

        incomePieChart = view.findViewById(R.id.income_chart);
        initPieChart("收支比例");
        setScaleData();


        return view;
    }

    private void showDateDialog(){
        MaterialDialog dialog = new MaterialDialog.Builder(getActivity())
                .title("统计起止日期")
                .customView(R.layout.dialog_statistics_date,true)
                .positiveText("确定")
                .negativeText("取消")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        if(TextUtils.isEmpty(startYear.getText())||TextUtils.isEmpty(endYear.getText())
                                ||TextUtils.isEmpty(startMonth.getText())||TextUtils.isEmpty(endMonth.getText())
                                ||TextUtils.isEmpty(startDay.getText())||TextUtils.isEmpty(endDay.getText())
                                ){
                            Toast.makeText(getActivity(),"信息不全",Toast.LENGTH_SHORT).show();
                        }else {
                            int startTimeOne = Integer.parseInt(startYear.getText().toString()) * 10000
                                    + Integer.parseInt(startMonth.getText().toString()) * 100
                                    + Integer.parseInt(startDay.getText().toString());
                            int endTimeOne = Integer.parseInt(endYear.getText().toString()) * 10000
                                    + Integer.parseInt(endMonth.getText().toString()) * 100
                                    + Integer.parseInt(endDay.getText().toString());
                            if (startTimeOne <= endTimeOne) {
                                SharedPreferences.Editor editor = getContext().getSharedPreferences("date",
                                        MODE_PRIVATE).edit();
                                editor.putInt("startTime",startTimeOne);
                                editor.putInt("endTime",endTimeOne);
                                editor.apply();
                                startTime = startTimeOne;
                                endTime = endTimeOne;
                                dateTextView.setText(TimeUti.outPutTime(startTime)+"到"+TimeUti.outPutTime(endTime));
                                initPieChart("收支比例");
                                setScaleData();
                            } else {
                                Toast.makeText(getActivity(), "开始时间大于结束时间，请重新设置", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }).build();
        startYear = dialog.getCustomView().findViewById(R.id.start_year);
        startMonth = dialog.getCustomView().findViewById(R.id.start_month);
        startDay = dialog.getCustomView().findViewById(R.id.start_day);
        endYear = dialog.getCustomView().findViewById(R.id.end_year);
        endMonth = dialog.getCustomView().findViewById(R.id.end_month);
        endDay = dialog.getCustomView().findViewById(R.id.end_day);
        dialog.show();
    }

    private void initPieChart(String centerText){
        incomePieChart.setUsePercentValues(true);   //使用百分比显示
        incomePieChart.getDescription().setEnabled(false);  //设置图表的描述
        incomePieChart.setExtraOffsets(5,10,5,5);  //设置图表上下左右的偏移

        incomePieChart.setDragDecelerationFrictionCoef(0.95f);  //设置图表转动阻力摩擦系数

        //incomePieChart.setCenterTextTypeface(mTfLight);  //设置内部圆字体样式
        incomePieChart.setEntryLabelColor(Color.WHITE);
        incomePieChart.setCenterText(centerText);  //设置内部圆文字
        incomePieChart.setDrawHoleEnabled(true);  //是否显示内部圆环
        incomePieChart.setHoleColor(Color.WHITE);  //设置内部圆的颜色
        incomePieChart.setTransparentCircleColor(Color.WHITE);  //设置内部透明圆与内部圆间距填充颜色
        incomePieChart.setTransparentCircleAlpha(110);  //设置内部透明圆与内部圆间距透明度

        incomePieChart.setHoleRadius(58f);  //设置内部圆的半径
        incomePieChart.setTransparentCircleRadius(61f);  //设置内部透明圆的半径

        incomePieChart.setDrawCenterText(true);  //是否绘制内部圆中心文本
        incomePieChart.setRotationAngle(0);   //设置图表起始角度

        incomePieChart.setRotationEnabled(true);  //设置图表能否手动转动
        incomePieChart.setHighlightPerTapEnabled(true);  //设置图表点击Item高亮是否可用

        incomePieChart.animateY(1400, Easing.EasingOption.EaseInOutQuad);  //设置出场动画

    }

    private void setmIncomeData(){
        List<Deal> deals = DataSupport.where("direction = ?","0").find(Deal.class);
        float profeIncome = 0.0f;
        float investIncome = 0.0f;
        float otherIncome = 0.0f;
        for(int i = 0; i < deals.size(); i++){
            if(deals.get(i).getTime()>=startTime && deals.get(i).getTime()<=endTime) {
                if (deals.get(i).getType() == Deal.PROFESSION) {
                    profeIncome = profeIncome + deals.get(i).getMoney();
                } else if (deals.get(i).getType() == Deal.INVEST) {
                    investIncome = investIncome + deals.get(i).getMoney();
                } else {
                    otherIncome = otherIncome + deals.get(i).getMoney();
                }
            }
        }
        ArrayList<PieEntry> entries = new ArrayList<PieEntry>();

        //判断有没有此类收入如果没有则不显示
        if(Float.compare(profeIncome,0.0f)!=0) {  //Float.compare返回整型
            entries.add(new PieEntry(profeIncome, mIncome[0]+""+profeIncome));
        }
        if(Float.compare(investIncome,0.0f)!=0) {
            entries.add(new PieEntry(investIncome, mIncome[1]+""+investIncome));
        }
        if(Float.compare(otherIncome,0.0f)!=0) {
            entries.add(new PieEntry(otherIncome, mIncome[2]+""+otherIncome));
        }

        PieDataSet dataSet = new PieDataSet(entries,"收入类型");
        dataSet.setDrawIcons(false);
        dataSet.setSliceSpace(3f);
        dataSet.setIconsOffset(new MPPointF(0,40));
        dataSet.setSelectionShift(5f);

        ArrayList<Integer> colors = new ArrayList<Integer>();

//        for(int c : ColorTemplate.VORDIPLOM_COLORS){
//            colors.add(c);
//        }
        for(int c : ColorTemplate.JOYFUL_COLORS){
            colors.add(c);
        }
//        for (int c : ColorTemplate.COLORFUL_COLORS){
//            colors.add(c);
//        }
//        for (int c : ColorTemplate.LIBERTY_COLORS){
//            colors.add(c);
//        }
        for (int c : ColorTemplate.PASTEL_COLORS){
            colors.add(c);
        }
        colors.add(ColorTemplate.getHoloBlue());
        dataSet.setColors(colors);

        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.WHITE);
        //data.setValueTypeface(mTfLight);
        incomePieChart.setData(data);

        incomePieChart.highlightValues(null);
        incomePieChart.invalidate();
    }

    private void setExpenseData(){
        List<Deal> deals = DataSupport.where("direction = ?","1").find(Deal.class);
        float clothes = 0.0f;
        float food = 0.0f;
        float home = 0.0f;
        float walk = 0.0f;
        float other = 0.0f;
        for(int i = 0; i < deals.size(); i ++){
            if(deals.get(i).getTime()>=startTime && deals.get(i).getTime()<=endTime) {
                if (deals.get(i).getType() == Deal.CLOTHES) {
                    clothes = clothes + deals.get(i).getMoney();
                } else if (deals.get(i).getType() == Deal.FOOD) {
                    food = food + deals.get(i).getMoney();
                } else if (deals.get(i).getType() == Deal.HOME) {
                    home = home + deals.get(i).getMoney();
                } else if (deals.get(i).getType() == Deal.WALK) {
                    walk = walk + deals.get(i).getMoney();
                } else {
                    other = other + deals.get(i).getMoney();
                }
            }
        }
        ArrayList<PieEntry> entries = new ArrayList<>();

        if(Float.compare(clothes,0.0f) != 0){
            entries.add(new PieEntry(clothes,mExpense[0]+clothes));
        }
        if(Float.compare(food,0.0f) != 0){
            entries.add(new PieEntry(food,mExpense[1]+food));
        }
        if(Float.compare(home,0.0f) != 0){
            entries.add(new PieEntry(home,mExpense[2]+home));
        }
        if(Float.compare(walk,0.0f) != 0){
            entries.add(new PieEntry(walk,mExpense[3]+walk));
        }
        if(Float.compare(other,0.0f) != 0){
            entries.add(new PieEntry(other,mExpense[4]+other));
        }
        PieDataSet dataSet = new PieDataSet(entries,"支出类型");
        dataSet.setDrawIcons(false);
        dataSet.setSliceSpace(3f);
        dataSet.setIconsOffset(new MPPointF(0,40));
        dataSet.setSelectionShift(5f);

        ArrayList<Integer> colors = new ArrayList<Integer>();

//        for(int c : ColorTemplate.VORDIPLOM_COLORS){
//            colors.add(c);
//        }
//        for(int c : ColorTemplate.JOYFUL_COLORS){
//            colors.add(c);
//        }
//        for (int c : ColorTemplate.COLORFUL_COLORS){
//            colors.add(c);
//        }
//        for (int c : ColorTemplate.LIBERTY_COLORS){
//            colors.add(c);
//        }
        for (int c : ColorTemplate.PASTEL_COLORS){
            colors.add(c);
        }
        colors.add(ColorTemplate.getHoloBlue());
        dataSet.setColors(colors);

        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.WHITE);
        //data.setValueTypeface(mTfLight);
        incomePieChart.setData(data);

        incomePieChart.highlightValues(null);
        incomePieChart.invalidate();
    }

    private void setScaleData() {
        List<Deal> deals = DataSupport.findAll(Deal.class);
        float income = 0.0f;
        float expense = 0.0f;
        for (int i = 0; i < deals.size(); i++) {
            if(deals.get(i).getTime()>=startTime && deals.get(i).getTime()<=endTime) {
                if (deals.get(i).getDirection() == Deal.INCOME) {
                    income = income + deals.get(i).getMoney();
                } else {
                    expense = expense + deals.get(i).getMoney();
                }
            }
        }
        balanceTextView.setText(""+(income-expense));
        ArrayList<PieEntry> entries = new ArrayList<>();

        if(Float.compare(income,0.0f) != 0){
            entries.add(new PieEntry(income,mScale[0]+income));
        }
        if(Float.compare(expense,0.0f) != 0){
            entries.add(new PieEntry(expense,mScale[1]+expense));
        }
        PieDataSet dataSet = new PieDataSet(entries,"收支");
        dataSet.setDrawIcons(false);
        dataSet.setSliceSpace(3f);
        dataSet.setIconsOffset(new MPPointF(0,40));
        dataSet.setSelectionShift(5f);

        ArrayList<Integer> colors = new ArrayList<Integer>();

//        for(int c : ColorTemplate.VORDIPLOM_COLORS){
//            colors.add(c);
//        }
//        for(int c : ColorTemplate.JOYFUL_COLORS){
//            colors.add(c);
//        }
        for (int c : ColorTemplate.COLORFUL_COLORS){
            colors.add(c);
        }
//        for (int c : ColorTemplate.LIBERTY_COLORS){
//            colors.add(c);
//        }
        for (int c : ColorTemplate.PASTEL_COLORS){
            colors.add(c);
        }
        colors.add(ColorTemplate.getHoloBlue());
        dataSet.setColors(colors);

        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.WHITE);
        //data.setValueTypeface(mTfLight);
        incomePieChart.setData(data);

        incomePieChart.highlightValues(null);
        incomePieChart.invalidate();

    }

}
