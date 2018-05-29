package com.example.imransk.buproject1.FragmentClass;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.imransk.buproject1.R;
import com.example.imransk.buproject1.pojoClass.SignUpPojo;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

/**
 * Created by imran sk on 5/29/2018.
 */

public class Update_Faculty_Profile_F extends Fragment {

    ImageView imageView_F;
    EditText name_ET_show;
    EditText user_type_ET_show;
    EditText depart_ET_show;
    //    TextView batch_TV_show;
//    TextView batch_TV;
    EditText email_ET_show;
    EditText phone_number_ET_show;
    EditText id_number_ET_show;
    Button update_btn;

    String user_id;
    String user_type;
    String name;
    String department;
    String batch;
    String id_roll;
    String email_id;
    String phone_number;
    String image_url;


    View context;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getActivity().setTitle("Update Faculty Profile");
        return inflater.inflate(R.layout.update_faculty_profile,null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        this.context=view;


        final Bundle bundle = getArguments();

        user_id = bundle.getString("user_id");

        name=bundle.getString("name");
        user_type=bundle.getString("userType");
        department=bundle.getString("department");
        batch=bundle.getString("batch");
        id_roll=bundle.getString("id_roll");
        email_id=bundle.getString("email_id");
        phone_number=bundle.getString("phone");
        image_url=bundle.getString("imageUrl");
        Log.e("image url - - - ", "onViewCreated: "+image_url );
        Log.e("image url - - - ", "onViewCreated: "+user_type );
        Log.e("image url - - - ", "onViewCreated: "+department );
        Log.e("image url - - - ", "onViewCreated: "+id_roll);
        Log.e("image url - - - ", "onViewCreated: "+phone_number );
        Log.e("image url - - - ", "onViewCreated: "+name);
        Log.e("image url - - - ", "onViewCreated: "+user_id);


        initialize();
        Picasso.with(getContext()).load(image_url).into(imageView_F);
        name_ET_show.setText(name);
        user_type_ET_show.setText(user_type);
        depart_ET_show.setText(department);
        email_ET_show.setText(email_id);
        phone_number_ET_show.setText(phone_number);
        id_number_ET_show.setText(id_roll);
    update_btn=(Button)view.findViewById(R.id.update_faculty_btn_ET);
        update_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(getContext(), "hello", Toast.LENGTH_SHORT).show();
                    FirebaseDatabase firebaseDatabase;
                    DatabaseReference databaseReference;
                    firebaseDatabase=FirebaseDatabase.getInstance();
                    databaseReference=firebaseDatabase.getReference("faculty");

                    SignUpPojo signUpPojo=new SignUpPojo("1",user_id,user_type_ET_show.getText().toString(),
                            email_ET_show.getText().toString(),name_ET_show.getText().toString(),
                            depart_ET_show.getText().toString(),batch,phone_number_ET_show.getText().toString(),
                            id_number_ET_show.getText().toString(),image_url);
                    databaseReference.child(user_id).setValue(signUpPojo);

                    Fragment  fragment=new Faculty_List_For_Course_AssigneF();
                    if (fragment!=null){
                        FragmentManager fragmentManager=getFragmentManager();
                        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.screenArea,fragment);
                        fragmentTransaction.commit();


                    }


                }
        });
    }

    private void initialize() {

        imageView_F = context.findViewById(R.id.faculty_image_view_show_ET);
        name_ET_show = context.findViewById(R.id.faculty_name_show_ET);
        user_type_ET_show= context.findViewById(R.id.faculty_type_show_ET);
        depart_ET_show = context.findViewById(R.id.faculty_Dept_name_show_ET);
//        batch_TV_show = context.findViewById(R.id.facultu_batch_show);
        email_ET_show = context.findViewById(R.id.faculty_email_show_ET);
        phone_number_ET_show = context.findViewById(R.id.faculty_phone_number_show_ET);
        id_number_ET_show = context.findViewById(R.id.faculty_id_show_ET);

//        batch_TV = context.findViewById(R.id.batch_Tv);

        update_btn = context.findViewById(R.id.update_faculty_btn_ET);
    }
}
