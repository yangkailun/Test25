package com.example.hp.test25.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.example.hp.test25.R;
import com.example.hp.test25.object.Deal;
import com.example.hp.test25.util.TimeUti;
import com.example.hp.test25.view.BudgetFragment;

import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * Created by HP on 2018-04-16.
 */

public class DealAdapter extends RecyclerView.Adapter<DealAdapter.ViewHolder> {

    private Context mContext;
    private List<Deal> mDealList;

    static class ViewHolder extends RecyclerView.ViewHolder{
        CardView dealCardView;
        ImageView dealImage,typeImage;
        TextView money,time;

        public ViewHolder(View view){
            super(view);
            dealCardView = view.findViewById(R.id.deal_cardview);
            dealImage = view.findViewById(R.id.deal_image);
            money = view.findViewById(R.id.money_item);
            typeImage = view.findViewById(R.id.type_image);
            time = view.findViewById(R.id.time);
        }
    }

    public DealAdapter(List<Deal> dealList){
        mDealList = dealList;
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType){
        if(mContext == null){
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.deal,parent,false);
        final ViewHolder holder = new ViewHolder(view);

        holder.dealCardView.setOnLongClickListener(new View.OnLongClickListener() {
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
                                Deal deal = mDealList.get(position);
                                DataSupport.deleteAll(Deal.class,"id=?",deal.getId()+"");
                                mDealList.remove(position);
                                notifyDataSetChanged();
                                BudgetFragment.adapter.notifyDataSetChanged(); //在删除收支时刷新预算
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

        holder.dealCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                Deal deal = mDealList.get(position);
                boolean wrapInScrollView = true;
                MaterialDialog dialog = new MaterialDialog.Builder(parent.getContext())
                        .title("收支详细")
                        .customView(R.layout.detail_deal,wrapInScrollView)
                        .positiveText("确定")
                        .build();
                TextView detail_direction = dialog.getCustomView().findViewById(R.id.detail_direction);
                TextView detail_money = dialog.getCustomView().findViewById(R.id.detail_money);
                TextView detail_type = dialog.getCustomView().findViewById(R.id.detail_type);
                TextView detail_remark = dialog.getCustomView().findViewById(R.id.detail_remark);
                TextView detail_time = dialog.getCustomView().findViewById(R.id.detail_time);
                detail_direction.setText(deal.getDirection()==Deal.EXPENSES?"支出":"收入");
                detail_money.setText(deal.getMoney()+"");
                switch (deal.getType()){
                    case 2:
                        detail_type.setText("衣");
                        break;
                    case 3:
                        detail_type.setText("食");
                        break;
                    case 4:
                        detail_type.setText("住");
                        break;
                    case 5:
                        detail_type.setText("行");
                        break;
                    case 6:
                        detail_type.setText("其他支出");
                        break;
                    case 7:
                        detail_type.setText("职业收入");
                        break;
                    case 8:
                        detail_type.setText("投资收入");
                        break;
                    case 9:
                        detail_type.setText("其他收入");
                        break;
                    default:
                }
                detail_remark.setText(deal.getRemark());
                detail_time.setText(TimeUti.outPutTime(deal.getTime()));
                dialog.show();
            }
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position){
        Deal deal = mDealList.get(position);
        if(deal.getDirection()==deal.INCOME){
            holder.dealImage.setImageResource(R.mipmap.ic_income);
        }else if(deal.getDirection()==deal.EXPENSES){
            holder.dealImage.setImageResource(R.mipmap.ic_expense);
        }
        holder.money.setText(deal.getMoney()+"");   //只能String类型
        switch (deal.getType()){  //打脸了? --自己的思考：自然or不自然
            case 2:
                holder.typeImage.setImageResource(R.mipmap.ic_clothes);
                break;
            case 3:
                holder.typeImage.setImageResource(R.mipmap.ic_food);
                break;
            case 4:
                holder.typeImage.setImageResource(R.mipmap.ic_home);
                break;
            case 5:
                holder.typeImage.setImageResource(R.mipmap.ic_walk);
                break;
            case 6:
                holder.typeImage.setImageResource(R.mipmap.ic_other);
                break;
            case 7:
                holder.typeImage.setImageResource(R.mipmap.ic_profession);
                break;
            case 8:
                holder.typeImage.setImageResource(R.mipmap.ic_invest);
                break;
            case 9:
                holder.typeImage.setImageResource(R.mipmap.ic_other);
            default:

        }
        holder.time.setText(TimeUti.outPutTime(deal.getTime()));
    }

    @Override
    public int getItemCount(){
        return mDealList.size();
    }
}
