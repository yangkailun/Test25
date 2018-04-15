package com.example.hp.test25.util;

import android.app.Application;
import android.content.Context;

import org.litepal.LitePal;

/**
 * Created by HP on 2018-04-15.
 */

public class MyApplicatiion extends Application {
    private static Context context;

    @Override
    public void onCreate(){
        context = getApplicationContext();
        LitePal.initialize(context);
    }

    public static Context getContext(){
        return context;
    }
}
