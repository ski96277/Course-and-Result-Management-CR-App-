package com.example.imransk.buproject1.pojoClass;

/**
 * Created by imran sk on 3/22/2018.
 */

public class SignUpPojo {
    String status;
    String user_id;
    String type;
    String email;

    public SignUpPojo() {
    }

    public SignUpPojo(String status, String user_id, String type,String email) {
        this.status = status;
        this.user_id = user_id;
        this.type = type;
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public String getStatus() {
        return status;
    }

    public String getUser_id() {
        return user_id;
    }

    public String getType() {
        return type;
    }
}
