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

import com.example.imransk.buproject1.Adapter.SubjetResultAdapter;
import com.example.imransk.buproject1.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by imran sk on 5/29/2018.
 */

public class Result_Sheet_For_Admin_F extends Fragment {

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    ArrayList<String> result_list;

    TextView cgpa_TV;
    ListView listView_result;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getActivity().setTitle("Result Sheet");

        return inflater.inflate(R.layout.result_sheet_for_admin_f, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        cgpa_TV = view.findViewById(R.id.cgpa_for_admin);
        listView_result = view.findViewById(R.id.result_list_for_admin);

        Bundle bundle = getArguments();
        final String batch_number = bundle.getString("batch");
        final String id_roll = bundle.getString("id_roll");

        result_list = new ArrayList<>();

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

//Show the Student Result
//                String batch_number = dataSnapshot.child("Student").child(userId).child("batch_number").getValue(String.class);
//                String iD_number = dataSnapshot.child("Student").child(userId).child("iD").getValue(String.class);

                Log.e(" batch number", " Student: batch homepage" + batch_number.trim());
                Log.e(" batch number", " Student: id number homepage" + id_roll.trim());

                boolean have_result = dataSnapshot.child("Result Sheet").child(batch_number)
                        .child(id_roll).hasChildren();

                int total_credit = 0;
                double total_point = 0;
                result_list.clear();
                if (have_result) {
                    for (DataSnapshot snapshot : dataSnapshot.child("Result Sheet").child(batch_number)
                            .child(id_roll).getChildren()) {

                        String subject = snapshot.getKey();
                        Log.e(" sub ...-- ", "subject name: " + subject.toString().trim());

                        String course_credit_st = dataSnapshot.child("Result Sheet").child(batch_number)
                                .child(id_roll).child(subject).child("course_credit").getValue().toString();

                        String course_point_st = dataSnapshot.child("Result Sheet").child(batch_number)
                                .child(id_roll).child(subject).child("total_point").getValue().toString();

                        total_point = total_point + Integer.parseInt(course_point_st);

                        total_credit = total_credit + Integer.parseInt(course_credit_st);

                        result_list.add(subject);

                    }
//set total credit and CGPA
                    Log.e("Total point -- - -- ", " Home page " + total_point);
                    cgpa_TV.setText("Total Credit - " + String.valueOf(total_credit) + "Total CGPA - " + String.valueOf(total_point / total_credit));

                    if (getActivity() != null) {

                        listView_result.setVisibility(View.VISIBLE);
                        SubjetResultAdapter subjetResultAdapter = new SubjetResultAdapter(getActivity(), result_list, batch_number, id_roll);
                        listView_result.setAdapter(subjetResultAdapter);
                    }
                } else {
                    cgpa_TV.setText("Student have No Result yet .... ");
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
