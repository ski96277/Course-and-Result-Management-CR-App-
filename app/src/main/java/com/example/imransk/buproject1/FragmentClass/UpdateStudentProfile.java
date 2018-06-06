package com.example.imransk.buproject1.FragmentClass;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.imransk.buproject1.R;
import com.example.imransk.buproject1.pojoClass.SignUpPojo;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

/**
 * Created by imran sk on 5/29/2018.
 */

public class UpdateStudentProfile extends Fragment {

    ImageView imageView;
    EditText name_ET_show;
    EditText user_type_ET_show;
    EditText depart_ET_show;
    EditText batch_ET_show;
    EditText email_ET_show;
    EditText phone_number_ET_show;
    EditText id_number__ET_show;
    Button update_btn;
    View context;


    String user_id;
    String user_type;
    String name;
    String department;
    String batch;
    String id_roll;
    String email_id;
    String phone_number;
    String image_url;
    String address;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getActivity().setTitle("Update Student Profile");
        return inflater.inflate(R.layout.update_student_profile,null);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.context = view;
        initialize();

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
        address=bundle.getString("address");

        Picasso.with(getContext()).load(image_url).into(imageView);
        name_ET_show.setText(name);
        user_type_ET_show.setText(user_type);
        depart_ET_show.setText(department);
        batch_ET_show.setText(batch);
        email_ET_show.setText(email_id);
        phone_number_ET_show.setText(phone_number);
        id_number__ET_show.setText(id_roll);

        update_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseDatabase  firebaseDatabase;
                DatabaseReference databaseReference;
                firebaseDatabase=FirebaseDatabase.getInstance();
                databaseReference=firebaseDatabase.getReference("Student");


    SignUpPojo signUpPojo=new SignUpPojo("1",user_id,user_type_ET_show.getText().toString()
                            ,email_ET_show.getText().toString(),name_ET_show.getText().toString(),depart_ET_show.getText().toString(),
                            batch_ET_show.getText().toString(),phone_number_ET_show.getText().toString(),
                            id_number__ET_show.getText().toString(),image_url,address);

                Log.e("Log e ", "onClick: "+user_id );
                databaseReference.child(user_id).setValue(signUpPojo);
                Toast.makeText(getContext(), "Updated", Toast.LENGTH_SHORT).show();

                Fragment fragment=null;
                fragment=new Student_list();

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

        imageView = context.findViewById(R.id.student_image_view_show);
        name_ET_show = context.findViewById(R.id.student_name_ET);
        user_type_ET_show = context.findViewById(R.id.student_type_ET);
        depart_ET_show = context.findViewById(R.id.student_Dept_name_ET);
        batch_ET_show = context.findViewById(R.id.student_batch_ET);
        email_ET_show = context.findViewById(R.id.student_email_ET);
        phone_number_ET_show = context.findViewById(R.id.student_phone_number_ET);
        id_number__ET_show = context.findViewById(R.id.student_id_ET);

        update_btn = context.findViewById(R.id.update_btn);
    }
}
