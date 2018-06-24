package com.example.imransk.buproject1.FragmentClass;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.imransk.buproject1.Adapter.Student_List_for_subject_Adapter;
import com.example.imransk.buproject1.R;
import com.example.imransk.buproject1.pojoClass.CourseAssignPojo;
import com.example.imransk.buproject1.pojoClass.SignUpPojo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Vector;

/**
 * Created by imran sk on 5/31/2018.
 */

public class Student_of_this_Subject_F extends Fragment {

    TextView sub_name_TV;
    ListView student_list;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    String batch_Number="";
    ArrayList<SignUpPojo> signUpList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       getActivity().setTitle("Student List of this subject");
        return inflater.inflate(R.layout.student_of_this_subject_f, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        sub_name_TV = view.findViewById(R.id.subject_name_TV_for_student);
        student_list = view.findViewById(R.id.student_list_for_this_subject);

        signUpList=new ArrayList<>();

        Bundle bundle = getArguments();

        final String sub_name = bundle.getString("course_name");
        sub_name_TV.setText(sub_name);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                CourseAssignPojo courseAssignPojo = null;

                for (DataSnapshot snapshot : dataSnapshot.child("CourseList").child("Student").getChildren()) {

                    String batch_number_get = snapshot.getKey();
                    Log.e("batch list", "Student of this course  - - -- - " + batch_number_get);

                    courseAssignPojo = snapshot.getValue(CourseAssignPojo.class);
                    //if status is 0 add it to signUpList
                    if (courseAssignPojo.getCourse_one().toString().equals(sub_name)) {
                        Log.e("Student_of_this_Project", "batch number " + batch_number_get);

                        batch_Number = batch_number_get;

                    }
                    if (courseAssignPojo.getCourse_two().toString().equals(sub_name)) {
                        Log.e("Student_of_this_Project", "batch number " + batch_number_get);

                        batch_Number = batch_number_get;

                    }
                    if (courseAssignPojo.getCourse_three().toString().equals(sub_name)) {

                        batch_Number = batch_number_get;

                    }
                    if (courseAssignPojo.getCourse_four().toString().equals(sub_name)) {

                        batch_Number = batch_number_get;
                    }

                }

                if (batch_Number.isEmpty()){
                    sub_name_TV.setText("No batch found");
                }

                // add student user to my array
                signUpList.clear();
                SignUpPojo signUpPojo = null;

                for (DataSnapshot snapshot : dataSnapshot.child("Student").getChildren()) {
                    Log.e("hello  --  - -", "onDataChange: " + snapshot.getKey());


                    signUpPojo = snapshot.getValue(SignUpPojo.class);
                    //if status is 0 add it to signUpList
                    if (signUpPojo.getBatch_number().equals(batch_Number)) {
                            signUpList.add(signUpPojo);
                        Log.e("hello  --  - -", "50 - -: " + signUpPojo.getBatch_number());

                    }
                    if (getActivity()!=null){

                        Student_List_for_subject_Adapter student_list_for_subjectAdapter = new Student_List_for_subject_Adapter(getActivity(), signUpList,sub_name);

                        student_list.setAdapter(student_list_for_subjectAdapter);
                    }


                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
