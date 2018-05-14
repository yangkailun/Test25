package com.example.hp.test25.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.daimajia.numberprogressbar.NumberProgressBar;
import com.example.hp.test25.R;
import com.example.hp.test25.object.Budget;
import com.example.hp.test25.object.Deal;
import com.example.hp.test25.util.TimeUti;

import org.litepal.crud.DataSupport;

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
        CardView budgetCardView;

        public ViewHolder(View view){
            super(view);
            startTimeView = view.findViewById(R.id.start_time);
            endTimeView = view.findViewById(R.id.end_time);
            budgetId = view.findViewById(R.id.budget_id);
            incomeBar = view.findViewById(R.id.income_bar);
            expenseBar = view.findViewById(R.id.expense_bar);
            budgetCardView = view.findViewById(R.id.budget_cardview);
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

        holder.budgetCardView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                new MaterialDialog.Builder(mContext)
                        .title("删除")
                        .content("确定删除吗？")
                        .positiveText("确定")
                        .negativeText("取消")
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                int position = holder.getAdapterPosition();
                                Budget budget = mBudgetList.get(position);
                                DataSupport.deleteAll(Budget.class,"id=?",budget.getId()+"");
                                mBudgetList.remove(position);
                                notifyDataSetChanged();
                            }
                        })
                        .onNegative(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                            }
                        }).show();
                return false;
            }
        });

        holder.budgetCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String budgetStr = "";
                int position = holder.getAdapterPosition();
                Budget budget = mBudgetList.get(position);
                List<Deal> incomeDeals = DataSupport
                        .where("direction=? and time>=? and time<=?"
                                ,Deal.INCOME+""
                                ,budget.getStartTime()+""
                                ,budget.getEndTime()+"")
                        .find(Deal.class);
                float income = 0.0f;
                for(int i = 0; i < incomeDeals.size(); i ++){
                    income = income + incomeDeals.get(i).getMoney();
                }
                List<Deal> expenseDeals = DataSupport
                        .where("direction=? and time>=? and time<=?"
                                ,Deal.EXPENSES+""
                                ,budget.getStartTime()+""
                                ,budget.getEndTime()+"")
                        .find(Deal.class);
                float expense = 0.0f;
                for(int i = 0; i < expenseDeals.size(); i ++){
                    expense = expense + expenseDeals.get(i).getMoney();
                }
                budgetStr="此预算自"+TimeUti.outPutTime(budget.getStartTime())+"至"+TimeUti.outPutTime(budget.getEndTime())
                        +"\n收入预计"+budget.getIncomeBudget()+"元已经完成约"+holder.incomeBar.getProgress()+"%"
                        +"为"+income +"元\n"+"支出预计"+budget.getExpenseBudget()+"元已经完成约"+holder.expenseBar
                        .getProgress()+"%"+"为"+expense+"元";
                new MaterialDialog.Builder(mContext)
                        .title("预算详细")
                        .content(budgetStr)
                        .positiveText("确定")
                        .show();
            }
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position){
        Budget budget = mBudgetList.get(position);
        holder.budgetId.setText(budget.getId()+"");
        holder.startTimeView.setText(TimeUti.outPutTime(budget.getStartTime()));
        holder.endTimeView.setText(TimeUti.outPutTime(budget.getEndTime()));
        List<Deal> incomeDeals = DataSupport
                .where("direction=? and time>=? and time<=?"
                ,Deal.INCOME+""
                ,budget.getStartTime()+""
                ,budget.getEndTime()+"")
                .find(Deal.class);
        float income = 0.0f;
        for(int i = 0; i < incomeDeals.size(); i ++){
            income = income + incomeDeals.get(i).getMoney();
        }
        int scale =(int)(income/budget.getIncomeBudget()*100);  //这里有失真问题
        if(scale <= 100){
            holder.incomeBar.setProgress(scale);
        }else {
            holder.incomeBar.setProgress(100);
        }
        List<Deal> expenseDeals = DataSupport
                .where("direction=? and time>=? and time<=?"
                ,Deal.EXPENSES+""
                ,budget.getStartTime()+""
                ,budget.getEndTime()+"")
                .find(Deal.class);
        float expense = 0.0f;
        for(int i = 0; i < expenseDeals.size(); i ++){
            expense = expense + expenseDeals.get(i).getMoney();
        }
        scale = (int)(expense/budget.getExpenseBudget()*100);
        if(scale<=100) {
            holder.expenseBar.setProgress(scale);
        }else {
            holder.expenseBar.setProgress(100);
        }
    }

    @Override
    public int getItemCount(){
        return mBudgetList.size();
    }
}
