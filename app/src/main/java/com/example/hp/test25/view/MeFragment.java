package com.example.hp.test25.view;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.hp.test25.R;
import com.example.hp.test25.util.HttpUtil;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class MeFragment extends Fragment {

    private ImageView bingPicImg;
    private SwipeRefreshLayout refreshLayout;

    public MeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_me, container, false);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        bingPicImg = view.findViewById(R.id.bing_pic_img);
        String bingPic = prefs.getString("bing_pic",null);
        if(bingPic != null){
            Glide.with(getActivity()).load(bingPic).into(bingPicImg);
        }else {
            loadBingPic(false);
        }

        refreshLayout = view.findViewById(R.id.swipe_refresh_me);
        refreshLayout.setColorSchemeResources(R.color.orange);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefresh();
            }
        });

        return view;
    }

    /**
     * 加载必应每日一图
     */
    private void loadBingPic(final boolean isRefresh){
        String requestBingPic = "http://guolin.tech/api/bing_pic";
        HttpUtil.sendOkHttpRequest(requestBingPic, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String bingPic = response.body().string();
                SharedPreferences.Editor editor = PreferenceManager
                        .getDefaultSharedPreferences(getActivity()).edit();
                editor.putString("bing_pic",bingPic);
                editor.apply();
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Glide.with(getActivity()).load(bingPic).into(bingPicImg);
                        if(isRefresh){
                            refreshLayout.setRefreshing(false);
                        }
                    }
                });
            }
        });
    }

    private void swipeRefresh(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    Thread.sleep(2000);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
                loadBingPic(true);

            }
        }).start();
    }

}
