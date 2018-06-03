package com.example.imransk.buproject1.FragmentClass;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.imransk.buproject1.R;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

/**
 * Created by imran sk on 5/27/2018.
 */

public class Faculty_Aprove_details_F extends Fragment {
    ImageView imageView;
    TextView name_TV_show;
    TextView user_type_TV_show;
    TextView depart_TV_show;
//    TextView batch_TV_show;
//    TextView batch_TV;
    TextView email_TV_show;
    TextView phone_number_show;
    TextView id_number_show;
    Button course_assign;
    Button delete_faculty;
    Button update_faculty;

    View context;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.faculty_aprove_details_f, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.context = view;
        initialize();

        Bundle bundle = getArguments();
        String name = bundle.getString("name");
        String department = bundle.getString("department");
        String batch = bundle.getString("batch");
        String id_roll = bundle.getString("id_roll");
        String email_id = bundle.getString("email_id");
        String phone = bundle.getString("phone");
        String imageUrl = bundle.getString("imageUrl");
        String userType = bundle.getString("userType");
        String address = bundle.getString("address");
        final String user_id = bundle.getString("user_id");

        Picasso.with(getContext()).load(imageUrl).into(imageView);
        name_TV_show.setText(name);
        user_type_TV_show.setText(userType);
        depart_TV_show.setText(department);

//        batch_TV_show.setText(batch);
        email_TV_show.setText(email_id);
        phone_number_show.setText(phone);
        id_number_show.setText(id_roll);

        final Bundle bundle_send = new Bundle();
        bundle_send.putString("user_id", user_id.toString());

        bundle_send.putString("name", name.toString());
        bundle_send.putString("department", department.toString());
        bundle_send.putString("batch", batch.toString());
        bundle_send.putString("id_roll", id_roll.toString());
        bundle_send.putString("email_id", email_id.toString());
        bundle_send.putString("phone", phone.toString());
        bundle_send.putString("imageUrl", imageUrl);
        bundle_send.putString("userType", userType);
        bundle_send.putString("address", address);


        course_assign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new Course_Assaign();
                if (fragment != null) {
                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.screenArea, fragment);
                    fragment.setArguments(bundle_send);
                    fragmentTransaction.addToBackStack("");
                    fragmentTransaction.commit();
                }

            }
        });
        update_faculty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = null;
                fragment = new Update_Faculty_Profile_F();
                if (fragment!=null){
                    FragmentManager fragmentManager=getFragmentManager();
                    FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
                    fragment.setArguments(bundle_send);
                    fragmentTransaction.replace(R.id.screenArea,fragment);
                    fragmentTransaction.commit();
                }

            }
        });
        delete_faculty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseDatabase firebaseDatabase;

                firebaseDatabase = FirebaseDatabase.getInstance();
                firebaseDatabase.getReference("faculty").child(user_id).removeValue();
                Fragment fragment = new Faculty_List_For_Course_AssigneF();
                if (fragment != null) {

                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.screenArea, fragment);
                    fragmentTransaction.commit();
                    Toast.makeText(getContext(), "Deleted ", Toast.LENGTH_SHORT).show();

                }


            }
        });


    }

    private void initialize() {

        imageView = context.findViewById(R.id.faculty_image_view_show);
        name_TV_show = context.findViewById(R.id.faculty_name_show);
        user_type_TV_show = context.findViewById(R.id.faculty_type_show);
        depart_TV_show = context.findViewById(R.id.faculty_Dept_name_show);
//        batch_TV_show = context.findViewById(R.id.facultu_batch_show);
        email_TV_show = context.findViewById(R.id.faculty_email_show);
        phone_number_show = context.findViewById(R.id.faculty_phone_number_show);
        id_number_show = context.findViewById(R.id.faculty_id_show);

//        batch_TV = context.findViewById(R.id.batch_Tv);

        course_assign = context.findViewById(R.id.course_assign_btn);
        delete_faculty = context.findViewById(R.id.delete_faculty_btn);
        update_faculty = context.findViewById(R.id.update_faculty_btn);
    }

}
