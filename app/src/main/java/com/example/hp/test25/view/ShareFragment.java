package com.example.hp.test25.view;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.example.hp.test25.R;
import com.example.hp.test25.adapter.ShareAdapter;
import com.example.hp.test25.object.Share;
import com.example.hp.test25.object.ShareSql;
import com.example.hp.test25.util.HttpUtil;

import org.litepal.LitePal;
import org.litepal.crud.DataSupport;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class ShareFragment extends Fragment {

//    List<Share> shareList = new ArrayList<>();
    List<ShareSql> shareSqlList = new ArrayList<>();
    String shareNum;
    private ShareAdapter adapter;
    private RecyclerView shareRecyclerView;

    public ShareFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_share, container, false);

        //在这里加载原来数据库中的数据可以吗？这要理解Fragment的生命周期。现在(4.15)测试还可以。
        shareSqlList = DataSupport.findAll(ShareSql.class);

        FloatingActionButton addFab = (FloatingActionButton)view.findViewById(R.id.fab);
        addFab.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {


                if(shareSqlList.isEmpty()) {
                    //创建Shares数据库，但是现在还不确定放在这里是不是合适
                    LitePal.getDatabase();
                }

                new MaterialDialog.Builder(getActivity())
                        .title("查询股票")
                        .content("请输入查询股票代码")
                        .inputType(InputType.TYPE_CLASS_TEXT )
                        .input("sh601009", "sh601009", new MaterialDialog.InputCallback() {
                            @Override
                            public void onInput(MaterialDialog dialog, CharSequence input) {
                                // Do something
                                shareNum=input.toString();
                                requestShares(shareNum);
                            }
                        }).show();

            }
        });

        shareRecyclerView = (RecyclerView)view.findViewById(R.id.share_recycler_view);
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(),2);
        shareRecyclerView.setLayoutManager(layoutManager);
        adapter = new ShareAdapter(shareSqlList);
        shareRecyclerView.setAdapter(adapter);

        return view;
    }

    public void requestShares(final String shareNum){
     //   String shareUrl = " http://apis.haoservice.com/lifeservice/stock/hs?gid="+shareNum+"&key=f5de2345f8e3477f8a0653f1ca110d54";
        //由于网上的股票接口稀缺，在本地搭建一个服务器测试
        String shareUrl = "http://192.168.1.100/"+shareNum+".json";
        HttpUtil.sendOkHttpRequest(shareUrl ,new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getActivity(),"股票查询失败！",Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseText = response.body().string();
                final Share share = HttpUtil.handleSharesResponse(responseText);
                if(share!=null && "0".equals(share.error_code)) {

                    //把请求的数据添加到数据库
                    ShareSql shareSql = new ShareSql(share);
                    shareSql.save();
                    shareSqlList.add(shareSql);

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            adapter.notifyDataSetChanged(); //及时刷新界面
                            shareRecyclerView.scrollToPosition(shareSqlList.size()-1);//将RecyclerView定位到最后一行
                        }
                    });

                }else{
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getActivity(),"股票查询失败！",Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

    }



}
