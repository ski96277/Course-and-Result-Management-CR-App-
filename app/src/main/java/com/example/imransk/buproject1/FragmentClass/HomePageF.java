package com.example.imransk.buproject1.FragmentClass;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.imransk.buproject1.AboutActivity;
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
 * Created by imran sk on 3/22/2018.
 */

public class HomePageF extends Fragment {

    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    DatabaseReference databaseReference;
    String userId;
    String status_student = "";
    String status_faculty = "";
    String status_admin = "";
    TextView statusTV;

    ListView listViewUser;
    private ArrayList<SignUpPojo> signUpList;

    Context context;
    AlertDialog.Builder builder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.homepagef, null);

    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        this.context = view.getContext();

        signUpList = new ArrayList<>();

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        userId = firebaseUser.getUid();
        statusTV = view.findViewById(R.id.homepageTV);
        listViewUser = view.findViewById(R.id.userList);

        databaseReference = FirebaseDatabase.getInstance().getReference();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Boolean sTrue = dataSnapshot.child("Student").hasChild(userId);
                Boolean fTrue = dataSnapshot.child("faculty").hasChild(userId);
                Boolean aTrue = dataSnapshot.child("admin").hasChild(userId);
                if (sTrue) {

                    status_student = dataSnapshot.child("Student").child(userId).child("status").getValue(String.class).trim();
                    if (status_student.equals("0")) {
                        statusTV.setText("Your student Status is 0");
                        Toast.makeText(view.getContext(), "" + status_student, Toast.LENGTH_SHORT).show();
                        //Call alert dialog method
                        alert();
                    } else if (status_student.equals("1")) {
                        statusTV.setText("Your student Status is 1");
                        Toast.makeText(view.getContext(), "" + status_student, Toast.LENGTH_SHORT).show();

                    }
                } else if (fTrue) {
                    status_faculty = dataSnapshot.child("faculty").child(userId).child("status").getValue(String.class).trim();
                    if (status_faculty.equals("0")) {
                        statusTV.setText("Your Faculty Status is 0");
                        Toast.makeText(view.getContext(), "" + status_faculty, Toast.LENGTH_SHORT).show();
                        //Call alert dialog method
                        alert();

                    } else if (status_faculty.equals("1")) {
                        statusTV.setText("Your student Status is 1");
                        Toast.makeText(view.getContext(), "" + status_faculty, Toast.LENGTH_SHORT).show();

                    }
                } else if (aTrue) {
                    listViewUser.setVisibility(View.VISIBLE);

                    status_admin = dataSnapshot.child("admin").child(userId).child("status").getValue(String.class).trim();
                    if (status_admin.equals("1")) {
                        statusTV.setText("WelCome Dear Admin \n");
                        Toast.makeText(view.getContext(), "" + status_admin, Toast.LENGTH_SHORT).show();

     // add student user to my array
                        signUpList.clear();
                        SignUpPojo signUpPojo = null;
                        for (DataSnapshot snapshot : dataSnapshot.child("Student").getChildren()) {
                            signUpPojo = snapshot.getValue(SignUpPojo.class);
                            if (signUpPojo.getStatus().equals("0")) {
                                signUpList.add(signUpPojo);
                            }

                        }

   // add Faculty user to my array
                        for (DataSnapshot snapshot : dataSnapshot.child("faculty").getChildren()) {
                            signUpPojo = snapshot.getValue(SignUpPojo.class);
                            if (signUpPojo.getStatus().equals("0")) {
                                signUpList.add(signUpPojo);
                            }

                        }
                        UserListAdapter adapter=new UserListAdapter(getContext(),signUpList);
                        listViewUser.setAdapter(adapter);

                        statusTV.append("Pending User" + String.valueOf(signUpList.size()));
//                        statusTV.append(signUpList.get(0).getStatus());
                        Log.e("Come from data Base", "onDataChange: " + signUpList);

                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    //Create alert dialog method
    private void alert() {

        //show alert dialog
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(context, android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(getContext());
        }
        builder.setTitle("Your Staus is 0")
                .setMessage("You need to Admin Aproval, So Wait for Admin Aprove")
                .setCancelable(false)//Can't Cancel when User Click on outside of my alertDailog or Backpressed
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete

                        startActivity(new Intent(getContext(), AboutActivity.class));
                        getActivity().finish();
                    }
                })
                /*.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                    }
                })*/
                .setIcon(android.R.drawable.ic_dialog_alert);
//use this code for need to crush off
        if (!((Activity) context).isFinishing()) {
            builder.show();
        }


    }
}
