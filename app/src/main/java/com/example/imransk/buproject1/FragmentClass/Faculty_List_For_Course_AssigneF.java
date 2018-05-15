package com.example.imransk.buproject1.FragmentClass;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.imransk.buproject1.Adapter.FacultyListAdapter;
import com.example.imransk.buproject1.Adapter.UserListAdapter;
import com.example.imransk.buproject1.R;
import com.example.imransk.buproject1.pojoClass.SignUpPojo;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by imran sk on 5/4/2018.
 */

public class Faculty_List_For_Course_AssigneF extends Fragment {

    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    DatabaseReference databaseReference;
    String userId;
    String status_admin = "";
    private ArrayList<SignUpPojo> signUpPojoList_faculty = null;
    Context context;


    ListView faculty_listView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.faculty_list_for_course_assigne_f, null);
    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        faculty_listView = view.findViewById(R.id.faculty_list_item);

        this.context = view.getContext();

        signUpPojoList_faculty = new ArrayList<>();

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        userId = firebaseUser.getUid();

        //listViewUser = view.findViewById(R.id.userList);
//geting data base referance
        databaseReference = FirebaseDatabase.getInstance().getReference();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //if admin is true
                Boolean aTrue = dataSnapshot.child("admin").hasChild(userId);
               if (aTrue) {
                    faculty_listView.setVisibility(View.VISIBLE);

                    status_admin = dataSnapshot.child("admin").child(userId).child("status").getValue(String.class).trim();
                    if (status_admin.equals("1")) {


                        // add student user to my array
                        signUpPojoList_faculty.clear();
                        SignUpPojo signUpPojo = null;

                        // add Faculty user to my array
                        for (DataSnapshot snapshot : dataSnapshot.child("faculty").getChildren()) {

                            signUpPojo = snapshot.getValue(SignUpPojo.class);
                            //if status is 0 add it to signUpList
//                            status_faculty = dataSnapshot.child("faculty").child(userId).child("status").getValue(String.class).trim();

                            if (signUpPojo.getStatus().equals("1")) {
                                signUpPojoList_faculty.add(signUpPojo);
                            }
                        }
                        FacultyListAdapter facultyListAdapter;


//use if Condition for skip nullpointer exception
                        if (getActivity() != null) {
                            facultyListAdapter = new FacultyListAdapter(getActivity(), signUpPojoList_faculty);

                            faculty_listView.setAdapter(facultyListAdapter);
                        }

                        }

                    }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}

