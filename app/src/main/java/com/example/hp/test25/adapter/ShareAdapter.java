package com.example.hp.test25.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.example.hp.test25.R;
import com.example.hp.test25.object.ShareSql;

import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * Created by HP on 2018-04-14.
 */

public class ShareAdapter extends RecyclerView.Adapter<ShareAdapter.ViewHolder> {
    private Context mContext;

    private List<ShareSql> mShareSqlList;  //尝试替换Share类，只让Share类负责与GSON转换，现在（4.15）是测试成功了

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
    public ShareAdapter(List<ShareSql> shareSqlList){
        mShareSqlList = shareSqlList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        if(mContext == null){
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.share_item,parent,false);
        //加入长按删除股票信息功能
        final ViewHolder holder = new ViewHolder(view);
        holder.shareInfo.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                new MaterialDialog.Builder(mContext)
                        .title("删除")
                        .content("确定删除吗？")
                        .positiveText("确定")
                        .negativeText("取消")
                        .onPositive(new MaterialDialog.SingleButtonCallback(){
                            @Override
                            public void onClick(MaterialDialog dialog, DialogAction which){
                                int position = holder.getAdapterPosition();
                                ShareSql shareSql= mShareSqlList.get(position);
                                DataSupport.deleteAll(ShareSql.class,"shareId=?",shareSql.getShareId());
                                mShareSqlList.remove(position);
                                notifyDataSetChanged();
                            }
                        })
                        .onNegative(new MaterialDialog.SingleButtonCallback(){
                            @Override
                            public void onClick(MaterialDialog dialog, DialogAction which){

                            }
                        })
                        .show();
                return true;
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position){

        ShareSql shareSql = mShareSqlList.get(position);
        holder.shareInfo.setText(idTitleString+shareSql.getShareId()+"\n"
        +nameTitleString+shareSql.getName()+"\n"
        +nowPriTitleString+shareSql.getNowPri()+"\n"
        +todayStartPriTitleString+shareSql.getTodayStartPri()+"\n"
        +yestodEndPriTitleString+shareSql.getYestodEndPri());

    }

    @Override
    public int getItemCount(){
        return mShareSqlList.size();
    }
}

