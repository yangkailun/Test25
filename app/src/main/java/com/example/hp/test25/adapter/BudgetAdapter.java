package com.example.hp.test25.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.daimajia.numberprogressbar.NumberProgressBar;
import com.example.hp.test25.R;
import com.example.hp.test25.object.Budget;

import java.util.List;

/**
 * Created by HP on 2018-04-22.
 */

public class BudgetAdapter extends RecyclerView.Adapter<BudgetAdapter.ViewHolder> {
    private Context mContext;
    private List<Budget> mBudgetList;

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView startTimeView,endTimeView,budgetId;
        NumberProgressBar incomeBar, expenseBar;

        public ViewHolder(View view){
            super(view);
            startTimeView = view.findViewById(R.id.start_time);
            endTimeView = view.findViewById(R.id.end_time);
            budgetId = view.findViewById(R.id.budget_id);
            incomeBar = view.findViewById(R.id.income_bar);
            expenseBar = view.findViewById(R.id.expense_bar);
        }

    }
    public BudgetAdapter(List<Budget> budgets){
        mBudgetList = budgets;
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType){
        if(mContext == null){
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.budget_item,parent,false);
        final ViewHolder holder = new ViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position){
        Budget budget = mBudgetList.get(position);
        holder.budgetId.setText(budget.getId()+"");
        holder.startTimeView.setText("2018.4.10");
        holder.endTimeView.setText("2018.4.22");
        holder.incomeBar.setProgress(50);
        holder.expenseBar.setProgress(20);
    }

    @Override
    public int getItemCount(){
        return mBudgetList.size();
    }
}
