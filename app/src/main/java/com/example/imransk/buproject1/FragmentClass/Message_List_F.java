package com.example.imransk.buproject1.FragmentClass;

import android.app.DownloadManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.imransk.buproject1.Adapter.Message_Adapter;
import com.example.imransk.buproject1.R;
import com.example.imransk.buproject1.pojoClass.Add_notice_pojo;
import com.example.imransk.buproject1.pojoClass.SignUpPojo;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by imran sk on 6/1/2018.
 */

public class Message_List_F extends Fragment {
    ListView message_list;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    FirebaseUser firebaseUser;

    String TAG = "                    Message List Fragment";
    String user_id;
    String batch;
    ArrayList<Add_notice_pojo> addNoticesList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.message_list_f, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        message_list = view.findViewById(R.id.message_listView);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        user_id = firebaseUser.getUid();

        addNoticesList = new ArrayList<>();

        Log.e(TAG, "user ID - - - " + user_id);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                SignUpPojo signUpPojo = null;

                for (DataSnapshot snapshot : dataSnapshot.child("Student").getChildren()) {
                    signUpPojo = snapshot.getValue(SignUpPojo.class);

                    if (signUpPojo.getUser_id().equals(user_id)) {
                        batch = signUpPojo.getBatch_number();
                        databaseReference = firebaseDatabase.getReference("Notice").child(batch);
                    }
                }
                Log.e(TAG, "batch Number - - - " + batch);
                Add_notice_pojo add_noticePojo = null;
                addNoticesList.clear();
                for (DataSnapshot snapshot : dataSnapshot.child("Notice").child(batch).getChildren()) {

                    Log.e(TAG, "onDataChange: " + snapshot.getKey());
                    add_noticePojo = snapshot.getValue(Add_notice_pojo.class);
                    Log.e(TAG, "onDataChange: " + add_noticePojo.getNotice());

                    addNoticesList.add(add_noticePojo);

                    if (getActivity()!=null){
                        Message_Adapter message_adapter=new Message_Adapter(getContext(),addNoticesList);
                        message_list.setAdapter(message_adapter);
                    }



                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
