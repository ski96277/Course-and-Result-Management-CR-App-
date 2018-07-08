package com.example.imransk.buproject1.FragmentClass;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.imransk.buproject1.Adapter.Class_Note_ListView_Adapter;
import com.example.imransk.buproject1.R;
import com.example.imransk.buproject1.pojoClass.SignUpPojo;
import com.example.imransk.buproject1.pojoClass.Upload_file;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by imran sk on 6/5/2018.
 */

public class Class_Note_list_F extends Fragment {

    ListView file_listView;
    TextView class_note_TV;

    DatabaseReference databaseReference;
    FirebaseUser firebaseUser;
    String user_id;
    String batch="";
    ArrayList<Upload_file> addFile_List;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getActivity().setTitle("Class Note");
        return inflater.inflate(R.layout.class_note_list_f,null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        file_listView=view.findViewById(R.id.note_listView);
        class_note_TV=view.findViewById(R.id.class_note_not_found);

        firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        user_id=firebaseUser.getUid();

        addFile_List=new ArrayList<>();

        databaseReference= FirebaseDatabase.getInstance().getReference();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                SignUpPojo signUpPojo = null;

                for (DataSnapshot snapshot : dataSnapshot.child("Student").getChildren()) {
                    signUpPojo = snapshot.getValue(SignUpPojo.class);

                    if (signUpPojo.getUser_id().equals(user_id)) {
                        batch = signUpPojo.getBatch_number();
                        databaseReference = FirebaseDatabase.getInstance().getReference("file").child(batch);
                    }
                }


                Log.e("     Class Note ", "batch Number - - - " + batch);
                Upload_file upload_file= null;
                addFile_List.clear();
                for (DataSnapshot snapshot : dataSnapshot.child("file").child(batch).getChildren()) {

                    Log.e("     Class Note ", "key      : " + snapshot.getKey());
                    upload_file = snapshot.getValue(Upload_file.class);
                    Log.e("     class note", "  Details: " + upload_file.getDetails());

                    addFile_List.add(upload_file);

                    Class_Note_ListView_Adapter class_note_listView_adapter=new Class_Note_ListView_Adapter(getContext(),addFile_List);
                    file_listView.setAdapter(class_note_listView_adapter);
                }

                if (addFile_List.isEmpty()){
                    class_note_TV.setVisibility(View.VISIBLE);
                    file_listView.setVisibility(View.GONE);
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
