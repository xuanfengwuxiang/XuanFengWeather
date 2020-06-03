package com.xuanfeng.xflibrary.magicindicator;

/**
 * Created by xuanfengwuxiang on 2018/8/2.
 */

public class MagicBean {
    private String tittle;
    private String key;

    public MagicBean(String tittle, String key) {
        this.tittle = tittle;
        this.key = key;
    }

    public String getTittle() {
        return tittle;
    }

    public void setTittle(String tittle) {
        this.tittle = tittle;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
