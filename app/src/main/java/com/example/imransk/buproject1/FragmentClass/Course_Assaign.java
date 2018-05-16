package com.example.imransk.buproject1.FragmentClass;

import android.media.Image;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by imran sk on 5/16/2018.
 */

public class Course_Assaign extends Fragment {

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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
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

//        set course to your spinner
        // Spinner Drop down elements
        List<String> categories = new ArrayList<String>();
        categories.add("Select The Course");
        categories.add("Introduction of computer Science");
        categories.add("Analog Electronics & Lab");
        categories.add("Math-I (Differential Calculus & Co-ordinateGeom.)");
        categories.add("English I");
        categories.add("Business Organization");

        categories.add("Structural Programming Language & Lab");
        categories.add("Integral Calculus & Differential Equation");
        categories.add("Digital Logic & Lab");
        categories.add("English II");

        categories.add("Physics & Lab");
        categories.add("Electronic Device & Circuit & Lab");
        categories.add("Object Oriented Programming & Lab");
        categories.add("Government");

        categories.add("Programming Language (Java) & Lab");
        categories.add("Data Structure & Lab");
        categories.add("Discrete Mathematics");
        categories.add("Lainnde aVre Actlgoerbra, Complex Variable, Laplace Transformation");

        categories.add("Algorithm & Lab");
        categories.add("Microprocessor & Assembly Language & Lab");
        categories.add("Statistics & Probability");

        categories.add("Theory of Computation");
        categories.add("Data Communication");
        categories.add("Electrical Drives and Instrumentation & Lab");
        categories.add("Web Programming");

        categories.add("Database System & Lab");
        categories.add("Operating System & Lab");
        categories.add("Accounting");
        categories.add("VLSI Design");

        categories.add("Compiler Design & Lab");
        categories.add("Digital System Design & Lab");
        categories.add("Digital Electronics & Pulse Technique");
        categories.add("Software Engineering");
        categories.add("Pattern Recognition & Lab");
        categories.add("Computer Network & lab");
        categories.add("E-Commerce");
        categories.add("Numerical Method");

        categories.add("Project & Thesis I");
        categories.add("Artificial Intelligence & Lab");
        categories.add("Accounting & Introduction to Finance & International Trade");
        categories.add("Elective Major 1(MIS)");

        categories.add("Project & Thesis II");
        categories.add("Computer Graphics & Lab");
        categories.add("System Analysis & Design & Lab");
        categories.add("Project and Thesis III");
        categories.add("Elective Major II (System Programming)");
        categories.add("Peripheral and Interfacing");
        categories.add("Computer Organization & Architecture");


        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(), R.layout.custom_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner1.setAdapter(dataAdapter);
        spinner2.setAdapter(dataAdapter);
        spinner3.setAdapter(dataAdapter);
        spinner4.setAdapter(dataAdapter);


        submit_course_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Ok", Toast.LENGTH_SHORT).show();
            }
        });


    }

}
