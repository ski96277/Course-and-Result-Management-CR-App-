package com.example.imransk.buproject1.pojoClass;

/**
 * Created by imran sk on 5/18/2018.
 */

public class CourseAssignPojo {
    String course_one;
    String course_two;
    String course_three;
    String course_four;

    public CourseAssignPojo() {

    }

    public CourseAssignPojo(String course_one, String course_two, String course_three, String course_four) {
        this.course_one = course_one;
        this.course_two = course_two;
        this.course_three = course_three;
        this.course_four = course_four;
    }

    public String getCourse_one() {
        return course_one;
    }

    public String getCourse_two() {
        return course_two;
    }

    public String getCourse_three() {
        return course_three;
    }

    public String getCourse_four() {
        return course_four;
    }
}
