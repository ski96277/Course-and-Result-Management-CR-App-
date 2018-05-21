package com.example.imransk.buproject1.FragmentClass;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.imransk.buproject1.R;
import com.example.imransk.buproject1.pojoClass.SubmitResultPojo;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by imran sk on 5/18/2018.
 */

public class Result_Submit_F extends Fragment{

    TextView subject_name;
    TextView course_credit_number;
    EditText st_id;
    EditText st_mark;
    Button submit_Btn;
    Button lab_result_btn;

    Spinner st_batch;
    Spinner st_grade;

    String subject;
    String course_credit;
    String st_batch_number;
    String id_number;
    String id_mark_number;
    String id_grade_number;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    SubmitResultPojo submitResultPojo;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.result_submit_f, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//get the value from homepage fragment in faculty
        Bundle bundle = getArguments();
        subject = bundle.getString("course_name");

        subject_name = view.findViewById(R.id.subject_name_TV);
        course_credit_number=view.findViewById(R.id.course_credit);

        st_id = view.findViewById(R.id.student_id_ET);
        st_mark = view.findViewById(R.id.student_mark_ET);
        st_batch = view.findViewById(R.id.batch_number_spinner);
        st_grade = view.findViewById(R.id.student_Grade_mark_spinner);
        submit_Btn = view.findViewById(R.id.submit_Result);
        lab_result_btn=view.findViewById(R.id.lab_result);
        lab_result_btn.setVisibility(View.GONE);

        subject_name.setText(subject);
        Log.e("batch number", "Result Submit class : " + st_batch_number);


        if (subject.equals("Analog Electronics and Lab")){

            subject_name.setText("Analog Electronics");
            lab_result_btn.setVisibility(View.VISIBLE);

            lab_result_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    subject_name.setText("Analog Electronics ( Lab )");
                    course_credit_number.setText("1");
                    lab_result_btn.setVisibility(View.GONE);
                    submit_Btn.setText("Lab Result Submit");

                }
            });

        }

        if (subject.equals("Structural Programming Language and Lab")){

            subject_name.setText("Structural Programming Language");
            lab_result_btn.setVisibility(View.VISIBLE);

            lab_result_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    subject_name.setText("Structural Programming Language ( Lab )");
                    course_credit_number.setText("1");
                    lab_result_btn.setVisibility(View.GONE);
                    submit_Btn.setText("Lab Result Submit");

                }
            });

        }
        if (subject.equals("Digital Logic and Lab")){

            subject_name.setText("Digital Logic");
            lab_result_btn.setVisibility(View.VISIBLE);

            lab_result_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    subject_name.setText("Digital Logic ( Lab )");
                    course_credit_number.setText("1");
                    lab_result_btn.setVisibility(View.GONE);
                    submit_Btn.setText("Lab Result Submit");

                }
            });

        }

        if (subject.equals("Physics and Lab")){

            subject_name.setText("Physics");
            lab_result_btn.setVisibility(View.VISIBLE);

            lab_result_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    subject_name.setText("Physics ( Lab )");
                    course_credit_number.setText("1");
                    lab_result_btn.setVisibility(View.GONE);
                    submit_Btn.setText("Lab Result Submit");

                }
            });

        }
        if (subject.equals("Electronic Device and Circuit and Lab")){

            subject_name.setText("Electronic Device and Circuit");
            lab_result_btn.setVisibility(View.VISIBLE);

            lab_result_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    subject_name.setText("Electronic Device and Circuit ( Lab )");
                    course_credit_number.setText("1");
                    lab_result_btn.setVisibility(View.GONE);
                    submit_Btn.setText("Lab Result Submit");

                }
            });

        }
        if (subject.equals("Object Oriented Programming and Lab")){

            subject_name.setText("Object Oriented Programming");
            lab_result_btn.setVisibility(View.VISIBLE);

            lab_result_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    subject_name.setText("Object Oriented Programming ( Lab )");
                    course_credit_number.setText("1");
                    lab_result_btn.setVisibility(View.GONE);
                    submit_Btn.setText("Lab Result Submit");

                }
            });

        }
        if (subject.equals("Programming Language (Java) and Lab")){

            subject_name.setText("Programming Language (Java)");
            lab_result_btn.setVisibility(View.VISIBLE);

            lab_result_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    subject_name.setText("Programming Language (Java) ( Lab )");
                    course_credit_number.setText("1");
                    lab_result_btn.setVisibility(View.GONE);
                    submit_Btn.setText("Lab Result Submit");

                }
            });

        }
        if (subject.equals("Data Structure and Lab")){

            subject_name.setText("Data Structure");
            lab_result_btn.setVisibility(View.VISIBLE);

            lab_result_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    subject_name.setText("Data Structure ( Lab )");
                    course_credit_number.setText("1");
                    lab_result_btn.setVisibility(View.GONE);
                    submit_Btn.setText("Lab Result Submit");

                }
            });

        }
        if (subject.equals("Algorithm and Lab")){

            subject_name.setText("Algorithm");
            lab_result_btn.setVisibility(View.VISIBLE);

            lab_result_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    subject_name.setText("Algorithm ( Lab )");
                    course_credit_number.setText("1");
                    lab_result_btn.setVisibility(View.GONE);
                    submit_Btn.setText("Lab Result Submit");

                }
            });

        }
        if (subject.equals("Microprocessor and Assembly Language and Lab")){

            subject_name.setText("Microprocessor and Assembly Language");
            lab_result_btn.setVisibility(View.VISIBLE);

            lab_result_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    subject_name.setText("Microprocessor and Assembly Language ( Lab )");
                    course_credit_number.setText("1");
                    lab_result_btn.setVisibility(View.GONE);
                    submit_Btn.setText("Lab Result Submit");

                }
            });

        }
        if (subject.equals("Electrical Drives and Instrumentation and Lab")){

            subject_name.setText("Electrical Drives and Instrumentation");
            lab_result_btn.setVisibility(View.VISIBLE);

            lab_result_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    subject_name.setText("Electrical Drives and Instrumentation ( Lab )");
                    course_credit_number.setText("1");
                    lab_result_btn.setVisibility(View.GONE);
                    submit_Btn.setText("Lab Result Submit");

                }
            });

        }
        if (subject.equals("Database System and Lab")){

            subject_name.setText("Database System");
            lab_result_btn.setVisibility(View.VISIBLE);

            lab_result_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    subject_name.setText("Database System ( Lab )");
                    course_credit_number.setText("1");
                    lab_result_btn.setVisibility(View.GONE);
                    submit_Btn.setText("Lab Result Submit");

                }
            });

        }
        if (subject.equals("Operating System and Lab")){

            subject_name.setText("Operating System");
            lab_result_btn.setVisibility(View.VISIBLE);

            lab_result_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    subject_name.setText("Operating System ( Lab )");
                    course_credit_number.setText("1");
                    lab_result_btn.setVisibility(View.GONE);
                    submit_Btn.setText("Lab Result Submit");

                }
            });

        }
        if (subject.equals("Compiler Design and Lab")){

            subject_name.setText("Compiler Design");
            lab_result_btn.setVisibility(View.VISIBLE);

            lab_result_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    subject_name.setText("Compiler Design ( Lab )");
                    course_credit_number.setText("1");
                    lab_result_btn.setVisibility(View.GONE);
                    submit_Btn.setText("Lab Result Submit");

                }
            });

        }


        if (subject.equals("Digital System Design and Lab ")){

            subject_name.setText("Digital System Design");
            lab_result_btn.setVisibility(View.VISIBLE);

            lab_result_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    subject_name.setText("Digital System Design ( Lab )");
                    course_credit_number.setText("1");
                    lab_result_btn.setVisibility(View.GONE);
                    submit_Btn.setText("Lab Result Submit");

                }
            });

        }
        if (subject.equals("Pattern Recognition and Lab")){

            subject_name.setText("Pattern Recognition");
            lab_result_btn.setVisibility(View.VISIBLE);

            lab_result_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    subject_name.setText("Pattern Recognition ( Lab )");
                    course_credit_number.setText("1");
                    lab_result_btn.setVisibility(View.GONE);
                    submit_Btn.setText("Lab Result Submit");

                }
            });

        }
        if (subject.equals("Computer Network and lab")){

            subject_name.setText("Computer Network");
            lab_result_btn.setVisibility(View.VISIBLE);

            lab_result_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    subject_name.setText("Computer Network ( Lab )");
                    course_credit_number.setText("1");
                    lab_result_btn.setVisibility(View.GONE);
                    submit_Btn.setText("Lab Result Submit");

                }
            });

        }
        if (subject.equals("Artificial Intelligence and Lab")){

            subject_name.setText("Artificial Intelligence");
            lab_result_btn.setVisibility(View.VISIBLE);

            lab_result_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    subject_name.setText("Artificial Intelligence ( Lab )");
                    course_credit_number.setText("1");
                    lab_result_btn.setVisibility(View.GONE);
                    submit_Btn.setText("Lab Result Submit");

                }
            });

        }
        if (subject.equals("Computer Graphics and Lab")){

            subject_name.setText("Computer Graphics");
            lab_result_btn.setVisibility(View.VISIBLE);

            lab_result_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    subject_name.setText("Computer Graphics ( Lab )");
                    course_credit_number.setText("1");
                    lab_result_btn.setVisibility(View.GONE);
                    submit_Btn.setText("Lab Result Submit");

                }
            });

        }
        if (subject.equals("System Analysis and Design and Lab")){

            subject_name.setText("System Analysis and Design");
            lab_result_btn.setVisibility(View.VISIBLE);

            lab_result_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    subject_name.setText("System Analysis and Design ( Lab )");
                    course_credit_number.setText("1");
                    lab_result_btn.setVisibility(View.GONE);
                    submit_Btn.setText("Lab Result Submit");

                }
            });

        }

        submit_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                st_batch_number = st_batch.getSelectedItem().toString();
                id_number = st_id.getText().toString();
                id_mark_number = st_mark.getText().toString();
                id_grade_number = st_grade.getSelectedItem().toString();
                subject=subject_name.getText().toString();

                course_credit=course_credit_number.getText().toString();

                int creditnumber=Integer.parseInt(course_credit);
                int total_point=0;

                if (id_grade_number.equals("A+")){
                    total_point= (int) (creditnumber*4.00);
                } if (id_grade_number.equals("A")){
                    total_point= (int) (creditnumber*3.75);
                } if (id_grade_number.equals("A-")){
                    total_point= (int) (creditnumber*3.50);
                } if (id_grade_number.equals("B+")){
                    total_point= (int) (creditnumber*3.25);
                }if (id_grade_number.equals("B")){
                    total_point= (int) (creditnumber*3.00);
                }if (id_grade_number.equals("B-")){
                    total_point= (int) (creditnumber*2.75);
                }if (id_grade_number.equals("C+")){
                    total_point= (int) (creditnumber*2.50);
                }if (id_grade_number.equals("C")){
                    total_point= (int) (creditnumber*2.25);
                }if (id_grade_number.equals("D")){
                    total_point= (int) (creditnumber*2.00);
                }if (id_grade_number.equals("F")){
                    total_point= (int) (creditnumber*0.00);
                }
                String total_Point=String.valueOf(total_point);

                submitResultPojo = new SubmitResultPojo(id_mark_number, id_grade_number,subject,course_credit,total_Point);
                firebaseDatabase = FirebaseDatabase.getInstance();
                databaseReference = firebaseDatabase.getReference();

                databaseReference.child("Result Sheet").child(st_batch_number).child(id_number).child(subject).setValue(submitResultPojo);
                Toast.makeText(getContext(), "Result Submited", Toast.LENGTH_SHORT).show();
                st_mark.setText("");
            }
        });


    }
}
