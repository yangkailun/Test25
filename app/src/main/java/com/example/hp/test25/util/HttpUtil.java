package com.example.hp.test25.util;

import com.example.hp.test25.object.Share;
import com.google.gson.Gson;

import okhttp3.OkHttpClient;
import okhttp3.Request;
/**
 * Created by HP on 2018-04-14.
 */

public class HttpUtil {
    public static void sendOkHttpRequest(String address,okhttp3.Callback callback){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(address).build();
        client.newCall(request).enqueue(callback);
    }

    /**
     * 将返回的JSON数据解析成Shares实体类
     */
    public static Share handleSharesResponse(String response){

        try {
            return new Gson().fromJson(response,Share.class);
        }catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }
}
