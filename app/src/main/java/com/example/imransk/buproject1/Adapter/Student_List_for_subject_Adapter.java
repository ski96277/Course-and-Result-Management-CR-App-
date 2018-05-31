package com.example.imransk.buproject1.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.imransk.buproject1.FragmentClass.Result_Submit_F;
import com.example.imransk.buproject1.R;
import com.example.imransk.buproject1.pojoClass.SignUpPojo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by imran sk on 5/31/2018.
 */

public class Student_List_for_subject_Adapter extends BaseAdapter {
    Context context;
    ArrayList<SignUpPojo> student_list;
    LayoutInflater layoutInflater;
    String sub_name;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    public Student_List_for_subject_Adapter(FragmentActivity activity, ArrayList<SignUpPojo> student_list,
                                            String sub_name) {

        this.context = activity;
        this.student_list = student_list;
        this.sub_name=sub_name;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return student_list.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    private class myHolder {
        TextView student_NameET;

        ImageView imageView_p_student;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        Log.e("sub_name", "getView: adapter --- "+sub_name);
        final myHolder myHolderObj = new myHolder();


        final View listViewItem_student = layoutInflater.inflate(R.layout.custom_faculty_listview_course_assaign, null);


        myHolderObj.student_NameET = listViewItem_student.findViewById(R.id.user_name_faculty);
        myHolderObj.imageView_p_student = listViewItem_student.findViewById(R.id.faculty_image_profile_list);

        final SignUpPojo signUpPojo_for_student = student_list.get(i);


        myHolderObj.student_NameET.append(signUpPojo_for_student.getFull_name().toString());
        Picasso.with(context).load(signUpPojo_for_student.getImageUri_download_Link()).into(myHolderObj.imageView_p_student);

        final Bundle bundle = new Bundle();
        bundle.putString("batch", signUpPojo_for_student.getBatch_number());
        bundle.putString("id_roll", signUpPojo_for_student.getiD());
        bundle.putString("course_name", sub_name);
        bundle.putString("user_id", signUpPojo_for_student.getUser_id());

        myHolderObj.student_NameET.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = null;
                fragment = new Result_Submit_F();
                if (fragment != null) {
                    FragmentTransaction fragmentTransaction = ((FragmentActivity) context).getSupportFragmentManager().beginTransaction();
                    fragment.setArguments(bundle);
                    fragmentTransaction.replace(R.id.screenArea, fragment);
                    fragmentTransaction.commit();
                }
            }
        });
        myHolderObj.imageView_p_student.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = null;
                fragment = new Result_Submit_F();
                if (fragment != null) {
                    FragmentTransaction fragmentTransaction = ((FragmentActivity) context).getSupportFragmentManager().beginTransaction();
                    fragment.setArguments(bundle);
                    fragmentTransaction.replace(R.id.screenArea, fragment);
                    fragmentTransaction.commit();
                }
            }
        });

        return listViewItem_student;
    }
}
