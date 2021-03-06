package com.example.imransk.buproject1.FragmentClass;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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
    ImageView imageView;
    TextView name_TV_show;
    TextView user_type_TV_show;
    TextView depart_TV_show;
    TextView batch_TV_show;
    TextView email_TV_show;
    TextView phone_number_show;
    TextView id_number_show;

    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;
    String user_ID;

    //string database
    String image_uri="";
    String name="";
    String department="";
    String batch="";
    String email_id="";
    String phone_number="";
    String id_number="";
    String runningstatus="";
    String type="";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       getActivity().setTitle("Profile");
        return inflater.inflate(R.layout.show_profile_f, null);
    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        imageView = view.findViewById(R.id.user_image_view_show);
        name_TV_show = view.findViewById(R.id.user_name_show);
        user_type_TV_show = view.findViewById(R.id.user_type_show);
        depart_TV_show=view.findViewById(R.id.user_Dept_name_show);
        batch_TV_show=view.findViewById(R.id.user_batch_show);
        email_TV_show=view.findViewById(R.id.user_email_show);
        phone_number_show=view.findViewById(R.id.user_phone_number_show);
        id_number_show=view.findViewById(R.id.user_id_show);


        name_TV_show.append("");
        user_type_TV_show.append("");
        depart_TV_show.append("");
        email_TV_show.append("");
        phone_number_show.append("");
        id_number_show.append("");
        batch_TV_show.append("");


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
                    type = dataSnapshot.child("Student").child(user_ID).child("type").getValue(String.class).trim();
                    email_id = dataSnapshot.child("Student").child(user_ID).child("email").getValue(String.class).trim();
                    phone_number = dataSnapshot.child("Student").child(user_ID).child("phoneNumber").getValue(String.class).trim();
                    id_number = dataSnapshot.child("Student").child(user_ID).child("iD").getValue(String.class).trim();

                    image_uri = dataSnapshot.child("Student").child(user_ID).child("imageUri_download_Link").getValue(String.class).trim();


                    name_TV_show.append(name);
                    user_type_TV_show.append(type);
                    depart_TV_show.append(department);
                    batch_TV_show.append(batch);
                    email_TV_show.append(email_id);
                    phone_number_show.append(phone_number);
                    id_number_show.append(id_number);

                    Picasso.with(getContext()).load(image_uri).into(imageView);
//

                    Log.e("on Data Change", "name: " + name);
                    Log.e("on Data Change", "name: " + department);
                    Log.e("on Data Change", "name: " + batch);
                    Log.e("on Data Change", "name: " + email_id);
                    Log.e("on Data Change", "name: " + image_uri);
                } else if (fTrue) {
                    name = dataSnapshot.child("faculty").child(user_ID).child("full_name").getValue(String.class).trim();
                    department = dataSnapshot.child("faculty").child(user_ID).child("department_name").getValue(String.class).trim();
                    batch = dataSnapshot.child("faculty").child(user_ID).child("batch_number").getValue(String.class).trim();
                    type = dataSnapshot.child("faculty").child(user_ID).child("type").getValue(String.class).trim();
                    email_id = dataSnapshot.child("faculty").child(user_ID).child("email").getValue(String.class).trim();
                    phone_number = dataSnapshot.child("faculty").child(user_ID).child("phoneNumber").getValue(String.class).trim();
                    id_number = dataSnapshot.child("faculty").child(user_ID).child("iD").getValue(String.class).trim();


                    image_uri = dataSnapshot.child("faculty").child(user_ID).child("imageUri_download_Link").getValue(String.class).trim();


                    LinearLayout linearLayout=view.findViewById(R.id.batch_remove_faculty);
                    linearLayout.setVisibility(View.GONE);
                    name_TV_show.append(name);
                    user_type_TV_show.append(type);
                    depart_TV_show.append(department);
                    batch_TV_show.append(batch);
                    email_TV_show.append(email_id);
                    phone_number_show.append(phone_number);
                    id_number_show.append(id_number);
                    Picasso.with(getContext()).load(image_uri).into(imageView);


                } else if (aTrue) {

                    name = dataSnapshot.child("admin").child(user_ID).child("full_name").getValue(String.class).trim();
                    department = dataSnapshot.child("admin").child(user_ID).child("department_name").getValue(String.class).trim();
                    runningstatus = dataSnapshot.child("admin").child(user_ID).child("phoneNumber").getValue(String.class).trim();
                    type = dataSnapshot.child("admin").child(user_ID).child("type").getValue(String.class).trim();
                    email_id = dataSnapshot.child("admin").child(user_ID).child("email").getValue(String.class).trim();
                    id_number = dataSnapshot.child("admin").child(user_ID).child("iD").getValue(String.class).trim();
//                    image_uri = dataSnapshot.child("admin").child(user_ID).child("imageUri_download_Link").getValue(String.class).trim();
//                    Toast.makeText(getContext(), ""+id_number, Toast.LENGTH_SHORT).show();

                    TextView phone_tv=view.findViewById(R.id.phone_TV);
                    TextView batch_tv=view.findViewById(R.id.batch_TV);
                    phone_tv.setVisibility(View.GONE);
                    batch_tv.setVisibility(View.GONE);
                    name_TV_show.append(name);
                    user_type_TV_show.append(type);
                    depart_TV_show.append(department);
                    email_TV_show.append(email_id);
                    phone_number_show.setVisibility(View.GONE);
                    id_number_show.append(id_number);


                    batch_TV_show.setVisibility(View.GONE);

//                    Picasso.with(getContext()).load(firebaseUser.getPhotoUrl()).into(imageView);
//                    imageView.setImageURI(firebaseUser.getPhotoUrl());

                }else {
                    name_TV_show.setText("");
                    user_type_TV_show.setText("");
                    depart_TV_show.setText("");
                    email_TV_show.setText("");
                    phone_number_show.setText("");
                    id_number_show.setText("");


                    batch_TV_show.setText("");
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });




    }
}
