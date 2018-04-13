package com.example.hp.test25;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by HP on 2018-04-13.
 */

public class BaseFragment extends Fragment {
    public static Fragment newInstance(String info){
        Bundle args = new Bundle();
        BaseFragment fragment = new BaseFragment();
        args.putString("info",info);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_base,null);
        TextView tvInfo = (TextView)view.findViewById(R.id.textView);
        tvInfo.setText(getArguments().getString("info"));
        tvInfo.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Snackbar.make(v,"Don't click me. Please!",Snackbar.LENGTH_SHORT).show();
            }
        });
        return view;
    }
}
