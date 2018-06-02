package com.example.imransk.buproject1.FragmentClass;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.imransk.buproject1.Activity.AboutActivity;
import com.example.imransk.buproject1.Adapter.SubjetResultAdapter;
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
    private ArrayList<SignUpPojo> signUpList = null;

    private ArrayList<String> result_list = null;


    Context context;
    AlertDialog.Builder builder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        getActivity().setTitle("Home Page");

        return inflater.inflate(R.layout.homepagef, null);

    }

    @Override
    public void onViewCreated(final View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        this.context = view.getContext();

        signUpList = new ArrayList<>();

        result_list = new ArrayList<>();


        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        userId = firebaseUser.getUid();
        statusTV = view.findViewById(R.id.homepageTV);

        listViewUser = view.findViewById(R.id.userList);
//geting data base referance
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
//Show the Student Result
                        String batch_number = dataSnapshot.child("Student").child(userId).child("batch_number").getValue(String.class);
                        String iD_number = dataSnapshot.child("Student").child(userId).child("iD").getValue(String.class);

                        Log.e(" batch number", " Student: batch homepage" + batch_number.trim());
                        Log.e(" batch number", " Student: id number homepage" + iD_number.trim());

                        boolean have_result = dataSnapshot.child("Result Sheet").child(batch_number)
                                .child(iD_number).hasChildren();

                        int total_credit = 0;
                        double total_point = 0;
                        result_list.clear();
                        if (have_result) {
                            for (DataSnapshot snapshot : dataSnapshot.child("Result Sheet").child(batch_number)
                                    .child(iD_number).getChildren()) {

                                String subject = snapshot.getKey();
                                Log.e(" sub ...-- ", "subject name: " + subject.toString().trim());

                                String course_credit_st = dataSnapshot.child("Result Sheet").child(batch_number)
                                        .child(iD_number).child(subject).child("course_credit").getValue().toString();

                                String course_point_st = dataSnapshot.child("Result Sheet").child(batch_number)
                                        .child(iD_number).child(subject).child("total_point").getValue().toString();

                                total_point=total_point+Integer.parseInt(course_point_st);

                                total_credit = total_credit + Integer.parseInt(course_credit_st);

                                result_list.add(subject);

                            }
//set total credit and CGPA
                            Log.e("Total point -- - -- ", " Home page "+total_point );
                            statusTV.setText("Total Credit - " + String.valueOf(total_credit)+"Total CGPA - "+String.valueOf(total_point/total_credit));


                            listViewUser.setVisibility(View.VISIBLE);
                            SubjetResultAdapter subjetResultAdapter = new SubjetResultAdapter(getContext(), result_list, batch_number, iD_number);
                            listViewUser.setAdapter(subjetResultAdapter);
                        } else {
                            statusTV.setText("You have No Result yet .... ");
                            Toast.makeText(view.getContext(), "" + status_student, Toast.LENGTH_SHORT).show();
                        }


                    }
                } else if (fTrue) {
                    status_faculty = dataSnapshot.child("faculty").child(userId).child("status").getValue(String.class).trim();
                    if (status_faculty.equals("0")) {
                        statusTV.setText("Your Faculty Status is 0");
                        Toast.makeText(view.getContext(), "" + status_faculty, Toast.LENGTH_SHORT).show();
                        //Call alert dialog method
                        alert();

                    } else if (status_faculty.equals("1")) {

                        boolean courselist_true = dataSnapshot.child("CourseList").child("faculty").child(userId).hasChild("CourseList");
                        Log.e("course List", "having course list " + courselist_true);

                        if (courselist_true) {

                            statusTV.setVisibility(View.GONE);

                            LinearLayout linearLayout1 = view.findViewById(R.id.course_one_root_layout);
                            LinearLayout linearLayout2 = view.findViewById(R.id.course_two_root_layout);
                            LinearLayout linearLayout3 = view.findViewById(R.id.course_three_root_layout);
                            LinearLayout linearLayout4 = view.findViewById(R.id.course_four_root_layout);

                            View view1 = view.findViewById(R.id.view_Line_1);
                            View view2 = view.findViewById(R.id.view_Line_2);
                            View view3 = view.findViewById(R.id.view_Line_3);
                            View view4 = view.findViewById(R.id.view_Line_4);


                            view1.setVisibility(View.VISIBLE);
                            view2.setVisibility(View.VISIBLE);
                            view3.setVisibility(View.VISIBLE);
                            view4.setVisibility(View.VISIBLE);

                            linearLayout1.setVisibility(View.VISIBLE);
                            linearLayout2.setVisibility(View.VISIBLE);
                            linearLayout3.setVisibility(View.VISIBLE);
                            linearLayout4.setVisibility(View.VISIBLE);

                            final TextView course_1_Tv = view.findViewById(R.id.course_one_TV);
                            final TextView course_2_Tv = view.findViewById(R.id.course_two_TV);
                            final TextView course_3_Tv = view.findViewById(R.id.course_three_TV);
                            final TextView course_4_Tv = view.findViewById(R.id.course_four_TV);


                            String couser_one = dataSnapshot.child("CourseList").child("faculty").child(userId).child("CourseList").child("course_one").getValue(String.class);
                            String couser_two = dataSnapshot.child("CourseList").child("faculty").child(userId).child("CourseList").child("course_two").getValue(String.class);
                            String couser_three = dataSnapshot.child("CourseList").child("faculty").child(userId).child("CourseList").child("course_three").getValue(String.class);
                            String couser_four = dataSnapshot.child("CourseList").child("faculty").child(userId).child("CourseList").child("course_four").getValue(String.class);
                            course_1_Tv.setText(couser_one);
                            course_2_Tv.setText(couser_two);
                            course_3_Tv.setText(couser_three);
                            course_4_Tv.setText(couser_four);

//Go Result Submit fragment with data
                            if (course_1_Tv.getText().equals("No course yet")) {
                                course_1_Tv.setTextColor(0xFFF06D2F);

                            } else {
                                course_1_Tv.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {

                                        Fragment fragment = null;
                                        fragment = new Student_of_this_Subject_F();
                                        if (fragment != null) {

                                            Bundle bundle = new Bundle();
                                            bundle.putString("course_name", course_1_Tv.getText().toString());

                                            FragmentTransaction fragmentTransaction = ((FragmentActivity) context).getSupportFragmentManager()
                                                    .beginTransaction();
                                            fragmentTransaction.addToBackStack("");
                                            fragmentTransaction.replace(R.id.screenArea, fragment);
                                            fragment.setArguments(bundle);
                                            fragmentTransaction.commit();


                                        }

                                    }
                                });
                            }

                            if (course_2_Tv.getText().equals("No course yet")) {
                                course_2_Tv.setTextColor(0xFFF06D2F);
                            } else {
                                course_2_Tv.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {

                                        Fragment fragment = null;
                                        fragment = new Student_of_this_Subject_F();
                                        if (fragment != null) {

                                            Bundle bundle = new Bundle();
                                            bundle.putString("course_name", course_2_Tv.getText().toString());

                                            FragmentTransaction fragmentTransaction = ((FragmentActivity) context).getSupportFragmentManager()
                                                    .beginTransaction();
                                            fragmentTransaction.addToBackStack("");
                                            fragmentTransaction.replace(R.id.screenArea, fragment);
                                            fragment.setArguments(bundle);
                                            fragmentTransaction.commit();


                                        }

                                    }
                                });
                            }

                            if (course_3_Tv.getText().equals("No course yet")) {
                                course_3_Tv.setTextColor(0xFFF06D2F);
                            } else {
                                course_3_Tv.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {

                                        Fragment fragment = null;
                                        fragment = new Student_of_this_Subject_F();
                                        if (fragment != null) {

                                            Bundle bundle = new Bundle();
                                            bundle.putString("course_name", course_3_Tv.getText().toString());

                                            FragmentTransaction fragmentTransaction = ((FragmentActivity) context).getSupportFragmentManager()
                                                    .beginTransaction();
                                            fragmentTransaction.addToBackStack("");
                                            fragmentTransaction.replace(R.id.screenArea, fragment);
                                            fragment.setArguments(bundle);
                                            fragmentTransaction.commit();


                                        }


                                    }
                                });
                            }
                            if (course_4_Tv.getText().equals("No course yet")) {
                                course_4_Tv.setTextColor(0xFFF06D2F);
                            } else {
                                course_4_Tv.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {

                                        Fragment fragment = null;
                                        fragment = new Student_of_this_Subject_F();
                                        if (fragment != null) {

                                            Bundle bundle = new Bundle();
                                            bundle.putString("course_name", course_4_Tv.getText().toString());

                                            FragmentTransaction fragmentTransaction = ((FragmentActivity) context).getSupportFragmentManager()
                                                    .beginTransaction();
                                            fragmentTransaction.addToBackStack("");
                                            fragmentTransaction.replace(R.id.screenArea, fragment);
                                            fragment.setArguments(bundle);
                                            fragmentTransaction.commit();


                                        }

                                    }
                                });
                            }
// END Result Submit fragment with data

                        } else {

                            statusTV.setText("Wait for course Assign");
                        }

                    }
                } else if (aTrue) {
                    listViewUser.setVisibility(View.VISIBLE);

                    status_admin = dataSnapshot.child("admin").child(userId).child("status").getValue(String.class).trim();
                    if (status_admin.equals("1")) {
                        statusTV.setText("WelCome Dear Admin \n");

//                        Toast.makeText(view.getContext(), "" + status_admin, Toast.LENGTH_SHORT).show();

                        // add student user to my array
                        signUpList.clear();
                        SignUpPojo signUpPojo = null;
                        for (DataSnapshot snapshot : dataSnapshot.child("Student").getChildren()) {
                            signUpPojo = snapshot.getValue(SignUpPojo.class);
                            //if status is 0 add it to signUpList
                            if (signUpPojo.getStatus().equals("0")) {
                                signUpList.add(signUpPojo);

                            }

                        }

                        // add Faculty user to my array
                        for (DataSnapshot snapshot : dataSnapshot.child("faculty").getChildren()) {
                            signUpPojo = snapshot.getValue(SignUpPojo.class);
                            //if status is 0 add it to signUpList
                            if (signUpPojo.getStatus().equals("0")) {
                                signUpList.add(signUpPojo);
                            }

                        }
                        UserListAdapter adapter;


//use if Condition for skip nullpointer exception
                        if (getActivity() != null) {
                            adapter = new UserListAdapter(getActivity(), signUpList);
                            listViewUser.setAdapter(adapter);

                        }


                        statusTV.append("Pending User" + String.valueOf(signUpList.size()));
//                        statusTV.append(signUpList.get(0).getStatus());
                        Log.e("Come from data Base", "onDataChange: " + signUpList);

                    }
                } else {

                    //if account is deleted show this message to user
                    status_student = dataSnapshot.child("WorngAccount").child("DeleteStatus").getValue(String.class).trim();

                    statusTV.setText(status_student);

                    deleteUserAlert();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    //If user is deleted  alert dialog method
    private void deleteUserAlert() {

        //show alert dialog
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(context, android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(getContext());
        }
        builder.setTitle("Deleted Account")
                .setMessage(status_student)
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

    //Create User Alert  alert dialog method
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
