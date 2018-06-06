package com.example.imransk.buproject1.pojoClass;

/**
 * Created by imran sk on 6/4/2018.
 */

public class Upload_file {

    public String details;
    public String url;
    public String batch;
    public String date;

    // Default constructor required for calls to
    // DataSnapshot.getValue(User.class)
    public Upload_file() {
    }

    public Upload_file(String batch , String url, String details,String date) {
        this.details = details;
        this.url = url;
        this.batch=batch;
        this.date=date;
    }

    public String getDate() {
        return date;
    }

    public String getDetails() {
        return details;
    }

    public String getUrl() {
        return url;
    }

    public String getBatch() {
        return batch;
    }
}