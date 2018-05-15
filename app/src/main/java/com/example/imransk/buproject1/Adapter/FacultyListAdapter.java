package com.example.imransk.buproject1.Adapter;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.imransk.buproject1.R;
import com.example.imransk.buproject1.pojoClass.SignUpPojo;
import com.squareup.picasso.Picasso;
import java.util.List;

/**
 * Created by imran sk on 5/4/2018.
 */

public class FacultyListAdapter extends BaseAdapter {


    Context context;
    List<SignUpPojo> signUpPojoList_faculty;

    private static LayoutInflater layoutInflater=null;


    public FacultyListAdapter(Activity context, List<SignUpPojo> signUpPojoList_faculty) {

        this.context = context;
        this.signUpPojoList_faculty = signUpPojoList_faculty;
        layoutInflater= (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount() {
        return signUpPojoList_faculty.size();
    }

    @Override
    public Object getItem(int i) {
        return 0;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    private class myHolder{
        TextView faculty_NameET;

        ImageView imageView_p_faculty;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @NonNull
    @Override
    public View getView(final int position, @Nullable final View convertView, @NonNull ViewGroup parent) {

        myHolder myHolderObj=new myHolder();


        final View listViewItem_faculty = layoutInflater.inflate(R.layout.custom_faculty_listview_course_assaign, null);


        myHolderObj.faculty_NameET = listViewItem_faculty.findViewById(R.id.user_name_faculty);
        myHolderObj.imageView_p_faculty = listViewItem_faculty.findViewById(R.id.faculty_image_profile_list);



        final SignUpPojo signUpPojo_for_faculty = signUpPojoList_faculty.get(position);

        myHolderObj.faculty_NameET.append(signUpPojo_for_faculty.getFull_name());


//set image on list imageView
        Picasso.with(listViewItem_faculty.getContext()).load(signUpPojo_for_faculty.getImageUri_download_Link()).into(myHolderObj.imageView_p_faculty);

//On Click is not Working , so sad for me :'(

        /*listViewItem_faculty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), "you click", Toast.LENGTH_SHORT).show();
            }
        });*/
        return listViewItem_faculty;

    }





}