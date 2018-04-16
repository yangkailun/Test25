package com.example.hp.test25.util;

import com.example.hp.test25.object.ShareSql;

import java.util.List;

/**
 * Created by HP on 2018-04-16.
 */

public class FindShare {

    public static final int NO_EXIST = -1;

    //第一个参数是查询的列表；第二个参数是查询的对象
    public static int findShare(List<ShareSql> shareSqlList,ShareSql shareSql){
        for(int i = 0; i < shareSqlList.size(); i++){
            //String类必须用equals来比大小
            if(shareSql.getShareId().equals(shareSqlList.get(i).getShareId())){
                return i;
            }
        }
        return NO_EXIST; //返回-1代表查询失败
    }
}
