package com.example.hp.test25.object;

import org.litepal.crud.DataSupport;

/**
 * Created by HP on 2018-04-15.
 */

public class ShareSql extends DataSupport{

    public ShareSql(){
        //忘记重载这个方法曾在DataSupport.findAll()方法中出过错，具体什么原因，我想必须去读源码。
    }

    public ShareSql(Share share){
        shareId = share.resultData.dataInfo.shareId;
        name = share.resultData.dataInfo.name;
        nowPri = share.resultData.dataInfo.nowPrice;
        todayStartPri = share.resultData.dataInfo.todayStartPri;
        yestodEndPri = share.resultData.dataInfo.yestodEndPri;
    }

    private String shareId;
    private String name;
    private String nowPri;
    private String todayStartPri;
    private String yestodEndPri;

    public String getShareId() {
        return shareId;
    }

    public String getName() {
        return name;
    }

    public String getNowPri() {
        return nowPri;
    }

    public String getTodayStartPri() {
        return todayStartPri;
    }

    public String getYestodEndPri() {
        return yestodEndPri;
    }

    public void setShareId(String shareId) {
        this.shareId = shareId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNowPri(String nowPri) {
        this.nowPri = nowPri;
    }

    public void setTodayStartPri(String todayStartPri) {
        this.todayStartPri = todayStartPri;
    }

    public void setYestodEndPri(String yestodEndPri) {
        this.yestodEndPri = yestodEndPri;
    }
}
