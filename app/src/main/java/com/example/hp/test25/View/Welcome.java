package com.example.hp.test25.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.hp.test25.R;

public class Welcome extends AppCompatActivity implements Runnable{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        ImageView imageView = (ImageView)findViewById(R.id.image);
        Glide.with(this).load(R.drawable.charlie).into(imageView);  //Glide的效果确实好了不少
        new Thread(this).start();
    }

    @Override
    public void run() {
        try{
            Thread.sleep(4000);
//            SharedPreferences preferences = getSharedPreferences("count",0);
//            int count = preferences.getInt("count",0);
        }catch (Exception e){
            e.printStackTrace();
        }
        Intent intent = new Intent();
        intent.setClass(Welcome.this,MainActivity.class);
        startActivity(intent);
        this.finish();
    }
}
