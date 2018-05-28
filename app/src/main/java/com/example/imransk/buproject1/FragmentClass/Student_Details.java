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
 * Created by imran sk on 5/29/2018.
 */

public class Student_Details extends Fragment {

    ImageView imageView;
    TextView name_TV_show;
    TextView user_type_TV_show;
    TextView depart_TV_show;
    TextView batch_TV_show;
    TextView email_TV_show;
    TextView phone_number_show;
    TextView id_number_show;
    Button result_sheet;
    Button update_student;
    Button delete_student;

    View context;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.student_details,null);
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
        final String user_id = bundle.getString("user_id");

        Picasso.with(getContext()).load(imageUrl).into(imageView);
        name_TV_show.setText(name);
        user_type_TV_show.setText(userType);
        depart_TV_show.setText(department);
        batch_TV_show.setText(batch);
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


        result_sheet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new Result_Sheet();
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
        update_student.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Fragment fragment = new UpdateStudentProfile();
                if (fragment != null) {

                    FragmentManager fragmentManager=getFragmentManager();
                    FragmentTransaction fragmentTransaction =fragmentManager.beginTransaction();
                    fragment.setArguments(bundle_send);
                    fragmentTransaction.replace(R.id.screenArea,fragment);
                    fragmentTransaction.commit();

                }


            }
        });

        delete_student.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseDatabase firebaseDatabase;

                firebaseDatabase = FirebaseDatabase.getInstance();
                firebaseDatabase.getReference("Student").child(user_id).removeValue();

                Fragment fragment = new Student_list();
                if (fragment != null) {

                    FragmentManager fragmentManager=getFragmentManager();
                    FragmentTransaction fragmentTransaction =fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.screenArea,fragment);
                    fragmentTransaction.commit();
                    Toast.makeText(getContext(), "Deleted ", Toast.LENGTH_SHORT).show();

                }


            }
        });


    }

    private void initialize() {

        imageView = context.findViewById(R.id.student_image_view_show);
        name_TV_show = context.findViewById(R.id.student_name_show);
        user_type_TV_show = context.findViewById(R.id.student_type_show);
        depart_TV_show = context.findViewById(R.id.student_Dept_name_show);
        batch_TV_show = context.findViewById(R.id.student_batch_show);
        email_TV_show = context.findViewById(R.id.student_email_show);
        phone_number_show = context.findViewById(R.id.student_phone_number_show);
        id_number_show = context.findViewById(R.id.student_id_show);

         result_sheet= context.findViewById(R.id.result_sheet_btn);
        update_student = context.findViewById(R.id.update_student_btn);
        delete_student = context.findViewById(R.id.delete_student_btn);
    }

}
