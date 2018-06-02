package com.example.imransk.buproject1.pojoClass;

/**
 * Created by imran sk on 6/2/2018.
 */

public class Add_notice_pojo {
    String notice;
    String date;

    public Add_notice_pojo() {
    }

    public Add_notice_pojo(String notice, String date) {
        this.notice = notice;
        this.date=date;
    }

    public String getDate() {
        return date;
    }

    public String getNotice() {
        return notice;
    }
}
