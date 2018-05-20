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
    EditText st_id;
    EditText st_mark;
    Button submit_Btn;

    Spinner st_batch;
    Spinner st_grade;

    String subject;
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
        st_id = view.findViewById(R.id.student_id_ET);
        st_mark = view.findViewById(R.id.student_mark_ET);
        st_batch = view.findViewById(R.id.batch_number_spinner);
        st_grade = view.findViewById(R.id.student_Grade_mark_spinner);
        submit_Btn = view.findViewById(R.id.submit_Result);

        subject_name.setText(subject);

        Log.e("batch number", "onViewCreated: " + st_batch_number);

        submit_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                st_batch_number = st_batch.getSelectedItem().toString();
                id_number = st_id.getText().toString();
                id_mark_number = st_mark.getText().toString();
                id_grade_number = st_grade.getSelectedItem().toString();
                subject=subject_name.getText().toString();

                submitResultPojo = new SubmitResultPojo(id_mark_number, id_grade_number,subject);
                firebaseDatabase = FirebaseDatabase.getInstance();
                databaseReference = firebaseDatabase.getReference();

                databaseReference.child("Result Sheet").child(st_batch_number).child(id_number).child(subject).setValue(submitResultPojo);
                Toast.makeText(getContext(), "Result Submited", Toast.LENGTH_SHORT).show();
                st_mark.setText("");
            }
        });


    }
}
