package com.example.imransk.buproject1.pojoClass;

/**
 * Created by imran sk on 5/18/2018.
 */

public class SubmitResultPojo {
    private String mark;
    private String grade;
    private String Subject_name;
    private String course_credit;
    private  String total_point;

    public SubmitResultPojo() {
        this.mark = mark;
    }

    public String getSubject_name() {
        return Subject_name;
    }

    public SubmitResultPojo(String mark, String grade, String subject_name,String course_credit,String total_point) {
        this.mark = mark;
        this.grade = grade;
        this.Subject_name = subject_name;
        this.course_credit=course_credit;
        this.total_point = total_point;
    }

    public String getTotal_point() {
        return total_point;
    }

    public String getMark() {
        return mark;
    }

    public String getGrade() {
        return grade;
    }

    public String getCourse_credit() {
        return course_credit;
    }
}
