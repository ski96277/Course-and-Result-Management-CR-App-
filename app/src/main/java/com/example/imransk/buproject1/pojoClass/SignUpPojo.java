package com.example.imransk.buproject1.pojoClass;

/**
 * Created by imran sk on 3/22/2018.
 */

public class SignUpPojo {
    String status;
    String user_id;
    String type;
    String email;
    String full_name;
    String department_name;
    String batch_number;

    public SignUpPojo() {
    }

    public SignUpPojo(String status, String user_id, String type,String email,String full_name,String department_name,String batch_number) {
        this.status = status;
        this.user_id = user_id;
        this.type = type;
        this.email = email;
        this.full_name =full_name;
        this.department_name = department_name;
        this.batch_number =batch_number;
    }

    public String getFull_name() {
        return full_name;
    }

    public String getDepartment_name() {
        return department_name;
    }

    public String getBatch_number() {
        return batch_number;
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
