package com.example.imransk.buproject1.FragmentClass;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.imransk.buproject1.R;
import com.example.imransk.buproject1.pojoClass.CourseAssignPojo;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by imran sk on 5/31/2018.
 */

public class Course_Assign_To_Batch extends Fragment {

    EditText batch_ET;
    Spinner spinner1;
    Spinner spinner2;
    Spinner spinner3;
    Spinner spinner4;
    Button course_submit_btn;


    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.course_assign_to_batch, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        batch_ET = view.findViewById(R.id.batch_num_Admin_ET);
        spinner1 = view.findViewById(R.id.spinner_course_1_for_batch);
        spinner2 = view.findViewById(R.id.spinner_course_2_for_batch);
        spinner3 = view.findViewById(R.id.spinner_course_3_for_batch);
        spinner4 = view.findViewById(R.id.spinner_course_4_for_batch);
        course_submit_btn = view.findViewById(R.id.submit_course_for_batch);

        course_submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String batch_number;
                String sub1;
                String sub2;
                String sub3;
                String sub4;
                batch_number = batch_ET.getText().toString();
                sub1 = spinner1.getSelectedItem().toString();
                sub2 = spinner2.getSelectedItem().toString();
                sub3 = spinner3.getSelectedItem().toString();
                sub4 = spinner4.getSelectedItem().toString();

                CourseAssignPojo courseAssignPojo = new CourseAssignPojo(sub1, sub2, sub3, sub4);

                firebaseDatabase = FirebaseDatabase.getInstance();
                databaseReference = firebaseDatabase.getReference("CourseList");
                databaseReference.child("Student").child(batch_number).setValue(courseAssignPojo);

                Toast.makeText(getContext(), "Submited Success", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
