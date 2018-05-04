package com.example.imransk.buproject1.FragmentClass;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.imransk.buproject1.LoginSuccessActivity;
import com.example.imransk.buproject1.R;
import com.example.imransk.buproject1.pojoClass.SignUpPojo;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

/**
 * Created by imran sk on 4/30/2018.
 */

public class ShowUserProfileF extends Fragment{

    ImageView imageView;
    TextView name_TV_show;
    TextView depart_TV_show;
    TextView batch_TV_show;
    TextView email_TV_show;
    TextView phone_number_show;
    TextView id_number_show;
    TextView user_type_show;
    Button deleteButton;
    Button approveButton;

    String user_id;
    String user_type;
    String name;
    String department;
    String batch;
    String id_roll;
    String email_id;
    String phone_number;
    String image_url;

    FirebaseDatabase firebaseDatabase;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        return inflater.inflate(R.layout.show_user_profile_f, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        imageView = view.findViewById(R.id.admin_can_see_user_image_view_show);
        name_TV_show = view.findViewById(R.id.admin_can_see_user_name_show);
        depart_TV_show=view.findViewById(R.id.admin_can_see_user_Dept_name_show);
        batch_TV_show=view.findViewById(R.id.admin_can_see_user_batch_show);
        email_TV_show=view.findViewById(R.id.admin_can_see_user_email_show);
        phone_number_show=view.findViewById(R.id.admin_can_see_user_phone_number_show);
        id_number_show=view.findViewById(R.id.admin_can_see_user_id_show);
        user_type_show=view.findViewById(R.id.admin_can_see_user_type_show);

        deleteButton=view.findViewById(R.id.deleteAccountBtn);
        approveButton=view.findViewById(R.id.approveAccountBtn);


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

        Picasso.with(getContext()).load(image_url).into(imageView);
        name_TV_show.append(name);
        user_type_show.append(user_type);
        depart_TV_show.append(department);
        batch_TV_show.append(batch);
        email_TV_show.append(email_id);
        phone_number_show.append(phone_number);
        id_number_show.append(id_roll);

        approveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


//set status 1 by default inside DataBase useing pojo class
                SignUpPojo signUpPojoForset = new SignUpPojo("1", user_id, user_type, email_id, name,
                        department, batch, phone_number, id_roll, image_url);
                //get Referance
                firebaseDatabase = FirebaseDatabase.getInstance();
                DatabaseReference databaseReference = firebaseDatabase.getReference().child(signUpPojoForset.getType()).child(signUpPojoForset.getUser_id());

                databaseReference.setValue(signUpPojoForset);

                startActivity(new Intent(getContext(),LoginSuccessActivity.class));
                getActivity().finish();


            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                /*Remove DataBase Value and user ID*/

                firebaseDatabase = FirebaseDatabase.getInstance();

                 firebaseDatabase.getReference().child(user_type).child(user_id).removeValue();

                 startActivity(new Intent(getContext(),LoginSuccessActivity.class));
                getActivity().finish();


            }
        });
    }

//After Distroy app Run again
    @Override
    public void onDetach() {
        super.onDetach();
        startActivity(new Intent(getContext(), LoginSuccessActivity.class));
    }
}
