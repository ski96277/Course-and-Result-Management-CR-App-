package com.example.imransk.buproject1.FragmentClass;

import android.media.Image;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.imransk.buproject1.R;
import com.example.imransk.buproject1.pojoClass.CourseAssignPojo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
/**
 * Created by imran sk on 5/16/2018.
 */

public class Course_Assaign extends Fragment implements View.OnClickListener {

    String user_id;
    String user_type;
    String name;
    String department;
    String batch;
    String id_roll;
    String email_id;
    String phone_number;
    String image_url;


    TextView fac_name_TV;
    ImageView fac_Image;
    Spinner spinner1;
    Spinner spinner2;
    Spinner spinner3;
    Spinner spinner4;
    Button submit_course_list;


    String course_one="";
    String course_two="";
    String course_three="";
    String course_four="";
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        getActivity().setTitle("Course Assign");

        return inflater.inflate(R.layout.course_assign_f,null);



    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //retrieve Value from bundle that store from previous fragment
        Bundle bundle = getArguments();

        user_id = bundle.getString("user_id");

        name=bundle.getString("name");
        user_type=bundle.getString("userType");
        department=bundle.getString("department");
        batch=bundle.getString("batch");
        id_roll=bundle.getString("id_roll");
        email_id=bundle.getString("email_id");
        phone_number=bundle.getString("phone");
        image_url=bundle.getString("imageUrl");

        fac_Image=view.findViewById(R.id.image_view_show_for_course);
        fac_name_TV=view.findViewById(R.id.user_name_show_for_course);

        spinner1=view.findViewById(R.id.spinner_course_1);
        spinner2=view.findViewById(R.id.spinner_course_2);
        spinner3=view.findViewById(R.id.spinner_course_3);
        spinner4=view.findViewById(R.id.spinner_course_4);

        submit_course_list=view.findViewById(R.id.submit_course);

        //set image and name to fragment
        Picasso.with(getContext()).load(image_url).into(fac_Image);
        fac_name_TV.append(name);

        submit_course_list.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        course_one= spinner1.getSelectedItem().toString();
        course_two=spinner2.getSelectedItem().toString();
        course_three=spinner3.getSelectedItem().toString();
        course_four=spinner4.getSelectedItem().toString();

        if (course_one.equals("Select Course")){
            course_one="No course yet";
        }
        if (course_two.equals("Select Course")){
            course_two="No course yet";
        }
        if (course_three.equals("Select Course")){
            course_three="No course yet";
        }
        if (course_four.equals("Select Course")){
            course_four="No course yet";
        }
        FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
        DatabaseReference databaseReference=firebaseDatabase.getReference();


        CourseAssignPojo course_assaign_pojo=new CourseAssignPojo(course_one,course_two,course_three,course_four);
        databaseReference.child(user_type).child(user_id).child("CourseList").setValue(course_assaign_pojo);
        Toast.makeText(getContext(), "Succesfully Assign courses", Toast.LENGTH_SHORT).show();

    }



}
