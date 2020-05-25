package com.movtalent.app.util;


/**
 * Created by 幻陌
 * 接口
 */

public class DBjk{
    private int id;
    private String name;
    private String url;
    private int type;
    public DBjk(String name, String url, int type){
        this.name = name;
        this.url = url;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setType(int type){this.type = type;}
    public int getType(){return this.type;}
}
