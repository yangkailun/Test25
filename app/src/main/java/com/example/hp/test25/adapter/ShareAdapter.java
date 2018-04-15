package com.example.hp.test25.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.hp.test25.R;
import com.example.hp.test25.object.Share;

import java.util.List;

/**
 * Created by HP on 2018-04-14.
 */

public class ShareAdapter extends RecyclerView.Adapter<ShareAdapter.ViewHolder> {
    private Context mContext;
    private List<Share> mShareList;
    private final String idTitleString     =      "股票代码  : ";
    private final String nameTitleString   =      "股票名称  : ";
    private final String nowPriTitleString       ="当前价格  : ";
    private final String todayStartPriTitleString="今日开盘价: ";
    private final String yestodEndPriTitleString ="昨日收盘价: ";
    static class ViewHolder extends RecyclerView.ViewHolder{
        CardView cardView;
        TextView shareInfo;

        public ViewHolder(View view){
            super(view);
            cardView = (CardView)view;
            shareInfo = (TextView)view.findViewById(R.id.share_info);
        }
    }
    public ShareAdapter(List<Share> shareList){
        mShareList = shareList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        if(mContext == null){
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.share_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position){
        Share share = mShareList.get(position);
        holder.shareInfo.setText(idTitleString+share.resultData.dataInfo.shareId+"\n"
                +nameTitleString+share.resultData.dataInfo.name+"\n"
                +nowPriTitleString+share.resultData.dataInfo.nowPrice+"\n"
                +todayStartPriTitleString+share.resultData.dataInfo.todayStartPri+"\n"
                +yestodEndPriTitleString+share.resultData.dataInfo.yestodEndPri);

    }

    @Override
    public int getItemCount(){
        return mShareList.size();
    }
}

