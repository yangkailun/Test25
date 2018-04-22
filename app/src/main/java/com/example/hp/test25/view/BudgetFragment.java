package com.example.hp.test25.view;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.example.hp.test25.R;
import com.example.hp.test25.adapter.BudgetAdapter;
import com.example.hp.test25.object.Budget;

import org.litepal.LitePal;
import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class BudgetFragment extends Fragment {

    public static BudgetAdapter adapter;
    private List<Budget> budgetList = new ArrayList<>();
    private FloatingActionButton budgetAdd;
    private EditText startYear,endYear,startMonth,endMonth,startDay,endDay,incomeBudget,expenseBudget;

    public BudgetFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_budget, container, false);

        budgetList = DataSupport.findAll(Budget.class);

        RecyclerView recyclerView = view.findViewById(R.id.budget_recycler);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(manager);
        adapter = new BudgetAdapter(budgetList);
        recyclerView.setAdapter(adapter);


        budgetAdd = view.findViewById(R.id.budget_add);
        budgetAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(budgetList.isEmpty()){
                    LitePal.getDatabase();
                }
                showBudgetDialog(adapter);
            }
        });

        return view;
    }

    private void showBudgetDialog(final BudgetAdapter adapter){
        final Budget budget = new Budget();
        MaterialDialog dialog = new MaterialDialog.Builder(getActivity())
                .title("预算")
                .customView(R.layout.dialog_budget,true)
                .positiveText("确定")
                .negativeText("取消")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        if(TextUtils.isEmpty(startYear.getText())||TextUtils.isEmpty(endYear.getText())
                                ||TextUtils.isEmpty(startMonth.getText())||TextUtils.isEmpty(endMonth.getText())
                                ||TextUtils.isEmpty(startDay.getText())||TextUtils.isEmpty(endDay.getText())
                                ||TextUtils.isEmpty(incomeBudget.getText())||TextUtils.isEmpty(expenseBudget.getText())){
                            Toast.makeText(getActivity(),"信息不全",Toast.LENGTH_SHORT).show();
                        }else {
                            int startTime = Integer.parseInt(startYear.getText().toString())*10000
                                    +Integer.parseInt(startMonth.getText().toString())*100
                                    +Integer.parseInt(startDay.getText().toString());
                            int endTime = Integer.parseInt(endYear.getText().toString())*10000
                                    +Integer.parseInt(endMonth.getText().toString())*100
                                    +Integer.parseInt(endDay.getText().toString());
                            if(startTime < endTime){
                                budget.setStartTime(startTime);
                                budget.setEndTime(endTime);
                                budget.setIncomeBudget(Float.parseFloat(incomeBudget.getText().toString()));
                                budget.setExpenseBudget(Float.parseFloat(expenseBudget.getText().toString()));

                                //添加唯一主键
                                SharedPreferences pref = getActivity().getSharedPreferences("budget_id", Context.MODE_PRIVATE);
                                int budgetId = pref.getInt("id",0);
                                budget.setId(budgetId);
                                budget.save();
                                budgetList.add(budget);
                                adapter.notifyDataSetChanged();

                                SharedPreferences.Editor editor = pref.edit();  //主键加1
                                editor.putInt("id",++budgetId);
                                editor.apply();
                            }else {
                                Toast.makeText(getActivity(), "开始时间大于结束时间，请重新设置", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                })
                .build();
        startYear = dialog.getCustomView().findViewById(R.id.start_year);
        startMonth = dialog.getCustomView().findViewById(R.id.start_month);
        startDay = dialog.getCustomView().findViewById(R.id.start_day);
        endYear = dialog.getCustomView().findViewById(R.id.end_year);
        endMonth = dialog.getCustomView().findViewById(R.id.end_month);
        endDay = dialog.getCustomView().findViewById(R.id.end_day);
        incomeBudget = dialog.getCustomView().findViewById(R.id.income_budget);
        expenseBudget = dialog.getCustomView().findViewById(R.id.expense_budget);

        dialog.show();

    }

}
