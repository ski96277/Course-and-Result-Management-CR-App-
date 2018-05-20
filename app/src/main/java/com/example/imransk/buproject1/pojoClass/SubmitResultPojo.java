package com.example.imransk.buproject1.pojoClass;

/**
 * Created by imran sk on 5/18/2018.
 */

public class SubmitResultPojo {
    private String mark;
    private String grade;
    private String Subject_name;

    public SubmitResultPojo() {
        this.mark = mark;
    }

    public String getSubject_name() {
        return Subject_name;
    }

    public SubmitResultPojo(String mark, String grade, String subject_name) {
        this.mark = mark;
        this.grade = grade;
        Subject_name = subject_name;
    }

    public SubmitResultPojo(String mark, String grade) {
        this.mark = mark;
        this.grade = grade;
    }

    public String getMark() {
        return mark;
    }

    public String getGrade() {
        return grade;
    }
}
