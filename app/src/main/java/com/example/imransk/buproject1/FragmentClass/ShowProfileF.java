package com.example.imransk.buproject1.FragmentClass;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.imransk.buproject1.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

/**
 * Created by imran sk on 4/4/2018.
 */

public class ShowProfileF extends Fragment {
    Button editYourProfile_Btn;
    ImageView imageView;
    TextView name_TV_show;
    TextView depart_TV_show;
    TextView batch_TV_show;
    TextView email_TV_show;

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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.show_profile, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        imageView = view.findViewById(R.id.user_image_view_show);
        name_TV_show = view.findViewById(R.id.user_name_show);
        depart_TV_show=view.findViewById(R.id.user_Dept_name_show);
        batch_TV_show=view.findViewById(R.id.user_batch_show);
        email_TV_show=view.findViewById(R.id.user_email_show);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

        user_ID = firebaseUser.getUid();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Boolean sTrue = dataSnapshot.child("Student").hasChild(user_ID);
                Boolean fTrue = dataSnapshot.child("faculty").hasChild(user_ID);
                Boolean aTrue = dataSnapshot.child("admin").hasChild(user_ID);
                if (sTrue) {
                    name = dataSnapshot.child("Student").child(user_ID).child("full_name").getValue(String.class).trim();
                    department = dataSnapshot.child("Student").child(user_ID).child("department_name").getValue(String.class).trim();
                    batch = dataSnapshot.child("Student").child(user_ID).child("batch_number").getValue(String.class).trim();
                    email_id = dataSnapshot.child("Student").child(user_ID).child("email").getValue(String.class).trim();
                    image_uri = dataSnapshot.child("Student").child(user_ID).child("imageUri_download_Link").getValue(String.class).trim();


                    name_TV_show.append(name);
                    depart_TV_show.append(department);
                    batch_TV_show.append(batch);
                    email_TV_show.append(email_id);
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


                    name_TV_show.append(name);
                    depart_TV_show.append(department);
                    batch_TV_show.append(batch);
                    email_TV_show.append(email_id);
                    Picasso.with(getContext()).load(image_uri).into(imageView);


                } else if (aTrue) {
                    //name = dataSnapshot.child("admin").child(user_ID).child("full_name").getValue(String.class).trim();
//                    department = dataSnapshot.child("admin").child(user_ID).child("department_name").getValue(String.class).trim();
//                    batch = dataSnapshot.child("admin").child(user_ID).child("batch_number").getValue(String.class).trim();
//                    email_id = dataSnapshot.child("admin").child(user_ID).child("email").getValue(String.class).trim();
//                    image_uri = dataSnapshot.child("admin").child(user_ID).child("imageUri_download_Link").getValue(String.class).trim();


//                    name_TV_show.append(firebaseUser.getDisplayName());
//                    depart_TV_show.append(department);
//                    batch_TV_show.append(batch);

                    name_TV_show.setVisibility(View.GONE);
                    depart_TV_show.setVisibility(View.GONE);
                    batch_TV_show.setVisibility(View.GONE);

                    email_TV_show.append(firebaseUser.getEmail());
//                    Picasso.with(getContext()).load(firebaseUser.getPhotoUrl()).into(imageView);
                    imageView.setImageURI(firebaseUser.getPhotoUrl());

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        //set Button Action
        editYourProfile_Btn = view.findViewById(R.id.edit_your_profile);
        editYourProfile_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent( getContext(), EditProfileF.class));
            }
        });
//end Button Action

    }
}
