package com.example.imransk.buproject1.Adapter;

import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.imransk.buproject1.R;
import com.example.imransk.buproject1.pojoClass.SignUpPojo;


import java.util.List;

/**
 * Created by imran sk on 3/24/2018.
 */

public class UserListAdapter extends ArrayAdapter {

    TextView useridET;
    TextView userTypeET;
    Button right_button;
    Context context;
    List<SignUpPojo> signUpPojoList;

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


        useridET = listViewItem.findViewById(R.id.user_Id);
        userTypeET = listViewItem.findViewById(R.id.user_Type);
        right_button = listViewItem.findViewById(R.id.right_button);

        final SignUpPojo signUpPojo = signUpPojoList.get(position);

        useridET.append(signUpPojo.getUser_id());
        userTypeET.append(signUpPojo.getType());
        right_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(context, ""+signUpPojo.getUser_id(), Toast.LENGTH_SHORT).show();
            }
        });
       /* listViewItem.setOnContextClickListener(new View.OnContextClickListener() {
          @Override
          public boolean onContextClick(View view) {

              Toast.makeText(context, ""+ signUpPojo.getUser_id(), Toast.LENGTH_SHORT).show();
              return true;
          }
      });*/

        return listViewItem;
    }


   /* @Override
    public void onClick(View view) {
        Toast.makeText(context, "hi", Toast.LENGTH_SHORT).show();
        signUpPojoList.getUser_id();
    }*/
}























