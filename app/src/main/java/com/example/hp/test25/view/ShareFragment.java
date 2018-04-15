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
import com.example.hp.test25.util.HttpUtil;

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

    List<Share> shareList = new ArrayList<>();
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

        FloatingActionButton addFab = (FloatingActionButton)view.findViewById(R.id.fab);
        addFab.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
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
        adapter = new ShareAdapter(shareList);
        shareRecyclerView.setAdapter(adapter);

        return view;
    }

    public void requestShares(final String shareNum){
        String shareUrl = " http://apis.haoservice.com/lifeservice/stock/hs?gid="+shareNum+"&key=f5de2345f8e3477f8a0653f1ca110d54";
        HttpUtil.sendOkHttpRequest(shareUrl ,new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                //暂时不处理
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
                    shareList.add(share);
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //adapter.notifyItemInserted(shareList.size()-1);  //及时刷新RecyclerView
                            //shareRecyclerView.scrollToPosition(shareList.size()-1);//将RecyclerView定位到最后一行
                            adapter.notifyDataSetChanged();
                        }
                    });

                }else{
                    //暂不处理
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
