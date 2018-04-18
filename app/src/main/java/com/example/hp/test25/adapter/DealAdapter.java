package com.example.hp.test25.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hp.test25.R;
import com.example.hp.test25.object.Deal;
import com.example.hp.test25.util.TimeUti;

import java.util.List;

/**
 * Created by HP on 2018-04-16.
 */

public class DealAdapter extends RecyclerView.Adapter<DealAdapter.ViewHolder> {

    private List<Deal> mDealList;

    static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView dealImage,typeImage;
        TextView money,time;

        public ViewHolder(View view){
            super(view);
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
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.deal,parent,false);
        ViewHolder holder = new ViewHolder(view);



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
