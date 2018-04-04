package com.example.imransk.buproject1.Adapter;

import android.content.Context;
import android.media.Image;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.imransk.buproject1.R;
import com.example.imransk.buproject1.pojoClass.SignUpPojo;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;


import java.util.List;

/**
 * Created by imran sk on 3/24/2018.
 */

public class UserListAdapter extends ArrayAdapter {

    TextView userNameET;
    TextView userTypeET;
    TextView departmentTypeET;
    ImageView imageView_p;
    Button right_button;
    Context context;
    List<SignUpPojo> signUpPojoList;

    FirebaseDatabase firebaseDatabase;

    public UserListAdapter(Context context, List<SignUpPojo> signUpPojoList) {
        super(context, R.layout.user_list, signUpPojoList);
        this.context = context;
        this.signUpPojoList = signUpPojoList;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);


        View listViewItem = inflater.inflate(R.layout.user_list, null);


        userNameET = listViewItem.findViewById(R.id.user_Id);
        userTypeET = listViewItem.findViewById(R.id.user_Type);
        departmentTypeET = listViewItem.findViewById(R.id.department_Type);
        imageView_p=listViewItem.findViewById(R.id.image_profile_list);

        right_button = listViewItem.findViewById(R.id.right_button);

        final SignUpPojo signUpPojo = signUpPojoList.get(position);

        userNameET.append(signUpPojo.getFull_name());
        userTypeET.append(signUpPojo.getType()+", ");
        departmentTypeET.append(signUpPojo.getDepartment_name());

//set image on list imageView
            Picasso.with(getContext()).load(signUpPojo.getImageUri_download_Link()).into(imageView_p);



        right_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//set status 1 by default inside DataBase useing pojo class
                SignUpPojo signUpPojoForset = new SignUpPojo("1", signUpPojo.getUser_id(), signUpPojo.getType(), signUpPojo.getEmail(),signUpPojo.getFull_name(),signUpPojo.getDepartment_name(),signUpPojo.getBatch_number(),signUpPojo.getImageUri_download_Link());
                firebaseDatabase = FirebaseDatabase.getInstance();
                DatabaseReference databaseReference = firebaseDatabase.getReference();
                databaseReference.child(signUpPojoForset.getType()).child(signUpPojoForset.getUser_id()).setValue(signUpPojoForset);
                Toast.makeText(context, "" + signUpPojoForset.getStatus(), Toast.LENGTH_SHORT).show();
            }
        });

        return listViewItem;
    }

}























