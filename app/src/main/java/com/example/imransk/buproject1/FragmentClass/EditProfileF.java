package com.example.imransk.buproject1.FragmentClass;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.imransk.buproject1.LogInActivity;
import com.example.imransk.buproject1.R;
import com.example.imransk.buproject1.pojoClass.SignUpPojo;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

/**
 * Created by imran sk on 3/22/2018.
 */

public class EditProfileF extends Fragment implements View.OnClickListener {
    Button profile_Update;
    ImageView imageView;
    EditText name_TV_show;
    EditText depart_TV_show;
    EditText batch_TV_show;
    TextView email_TV_show;
    EditText phone_number_TV_show;
    EditText id_number_TV_show;

    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;
    String user_ID;

    //string database
    String image_uri;
    String name;
    String department;
    String batch;
    String email_id;
    String phone_number;
    String id_number;
    String type;
    String runningstatus;

    Boolean aTrue;
    Boolean fTrue;
    Boolean sTrue;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.editpprofilef, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        imageView = view.findViewById(R.id.user_image_view_ET);
        name_TV_show = view.findViewById(R.id.user_name_ET);
        depart_TV_show = view.findViewById(R.id.user_Dept_name_ET);
        batch_TV_show = view.findViewById(R.id.user_batch_ET);
        email_TV_show = view.findViewById(R.id.user_email_ET);
        phone_number_TV_show = view.findViewById(R.id.user_phone_number_ET);
        id_number_TV_show = view.findViewById(R.id.user_id_number_ET);

        profile_Update = view.findViewById(R.id.submit_profile);
        profile_Update.setOnClickListener(this);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

        user_ID = firebaseUser.getUid();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                 sTrue = dataSnapshot.child("Student").hasChild(user_ID);
                 fTrue = dataSnapshot.child("faculty").hasChild(user_ID);
                 aTrue = dataSnapshot.child("admin").hasChild(user_ID);

                if (sTrue) {
                    name = dataSnapshot.child("Student").child(user_ID).child("full_name").getValue(String.class).trim();
                    department = dataSnapshot.child("Student").child(user_ID).child("department_name").getValue(String.class).trim();
                    batch = dataSnapshot.child("Student").child(user_ID).child("batch_number").getValue(String.class).trim();
                    email_id = dataSnapshot.child("Student").child(user_ID).child("email").getValue(String.class).trim();
                    image_uri = dataSnapshot.child("Student").child(user_ID).child("imageUri_download_Link").getValue(String.class).trim();
                    phone_number = dataSnapshot.child("Student").child(user_ID).child("phoneNumber").getValue(String.class).trim();
                    id_number = dataSnapshot.child("Student").child(user_ID).child("iD").getValue(String.class).trim();
                    type = dataSnapshot.child("Student").child(user_ID).child("type").getValue(String.class).trim();

                    name_TV_show.setText(name);
                    depart_TV_show.setText(department);
                    batch_TV_show.setText(batch);
                    email_TV_show.setText(email_id);
                    phone_number_TV_show.setText(phone_number);
                    id_number_TV_show.setText(id_number);
                    Picasso.with(getContext()).load(image_uri).into(imageView);


                    Log.e("on Data Change", "name: " + name);
                    Log.e("on Data Change", "name: " + department);
                    Log.e("on Data Change", "name: " + batch);
                    Log.e("on Data Change", "name: " + email_id);
                    Log.e("on Data Change", "name: " + image_uri);
                } else if (fTrue) {
                    name = dataSnapshot.child("faculty").child(user_ID).child("full_name").getValue(String.class).trim();
                    department = dataSnapshot.child("faculty").child(user_ID).child("department_name").getValue(String.class).trim();
                    batch = dataSnapshot.child("faculty").child(user_ID).child("batch_number").getValue(String.class).trim();
                    email_id = dataSnapshot.child("faculty").child(user_ID).child("email").getValue(String.class).trim();
                    image_uri = dataSnapshot.child("faculty").child(user_ID).child("imageUri_download_Link").getValue(String.class).trim();
                    phone_number = dataSnapshot.child("faculty").child(user_ID).child("phoneNumber").getValue(String.class).trim();
                    id_number = dataSnapshot.child("faculty").child(user_ID).child("iD").getValue(String.class).trim();
                    type = dataSnapshot.child("faculty").child(user_ID).child("type").getValue(String.class).trim();

                    name_TV_show.setText(name);
                    depart_TV_show.setText(department);
                    batch_TV_show.setText(batch);
                    email_TV_show.setText(email_id);
                    phone_number_TV_show.setText(phone_number);
                    id_number_TV_show.setText(id_number);
                    Picasso.with(getContext()).load(image_uri).into(imageView);



                } else if (aTrue) {
                    name = dataSnapshot.child("admin").child(user_ID).child("full_name").getValue(String.class).trim();
                    department = dataSnapshot.child("admin").child(user_ID).child("department_name").getValue(String.class).trim();
                    runningstatus = dataSnapshot.child("admin").child(user_ID).child("phoneNumber").getValue(String.class).trim();
                    type = dataSnapshot.child("admin").child(user_ID).child("type").getValue(String.class).trim();
                    email_id = dataSnapshot.child("admin").child(user_ID).child("email").getValue(String.class).trim();
                    id_number = dataSnapshot.child("admin").child(user_ID).child("iD").getValue(String.class).trim();


//                    depart_TV_show.append(department);
//                    batch_TV_show.append(batch);

                    name_TV_show.setText(name);
                    depart_TV_show.setText(department);
                    email_TV_show.setText(email_id);
                    id_number_TV_show.setText(id_number);

                    phone_number_TV_show.setText(runningstatus);
                    batch_TV_show.setText(type);

//                    Picasso.with(getContext()).load(firebaseUser.getPhotoUrl()).into(imageView);
//                    imageView.setImageURI(firebaseUser.getPhotoUrl());

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }


    //Update Database
    @Override
    public void onClick(View view) {
//        Toast.makeText(getContext(), type, Toast.LENGTH_SHORT).show();
//        Toast.makeText(getContext(), user_ID+","+id_number, Toast.LENGTH_SHORT).show();

        FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
        final DatabaseReference databaseReference = firebaseDatabase.getReference(type);

//        if (aTrue){
//            databaseReference.child(user_ID).child("phoneNumber").setValue(phone_number_TV_show.getText().toString().trim());
//            Toast.makeText(getContext(), "Updated.."+type, Toast.LENGTH_SHORT).show();
//
//
//        }else {
            databaseReference.child(user_ID).child("phoneNumber").setValue(phone_number_TV_show.getText().toString().trim());

            Toast.makeText(getContext(), "Updated.. too"+type, Toast.LENGTH_SHORT).show();

//        }




            startActivity(new Intent(getContext(), LogInActivity.class));




    }
}
