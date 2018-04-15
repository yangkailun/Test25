package com.example.hp.test25.object;

import com.google.gson.annotations.SerializedName;

/**
 * Created by HP on 2018-04-14.
 */

public class Share {
    public String error_code;
    public String reason;
    @SerializedName("result")
    public ResultData resultData;

    public static class ResultData {
        @SerializedName("data")
        public Data dataInfo;

        public static class Data {
            @SerializedName("gid")
            public String shareId;
            public String name;
            @SerializedName("nowPri")
            public String nowPrice;
            public String todayStartPri;
            public String yestodEndPri;
        }
    }
}
