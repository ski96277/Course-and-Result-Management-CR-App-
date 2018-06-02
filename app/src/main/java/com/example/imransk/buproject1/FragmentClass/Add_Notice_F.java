package com.example.imransk.buproject1.FragmentClass;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.imransk.buproject1.R;
import com.example.imransk.buproject1.pojoClass.Add_notice_pojo;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

/**
 * Created by imran sk on 6/1/2018.
 */

public class Add_Notice_F extends Fragment {


    EditText message_et;
    EditText batch_et;
    Button submit_Btn;
    String message;
    String batch;
    String date;
    String TAG = "       Add Note Fragment";


    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        getActivity().setTitle("Add Message");
        return inflater.inflate(R.layout.add_notice_f, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference();


        message_et = view.findViewById(R.id.message_ET);
        batch_et = view.findViewById(R.id.batch_ET);
        submit_Btn = view.findViewById(R.id.submit_btn);


        submit_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                message=message_et.getText().toString();
                batch=batch_et.getText().toString();
                date = java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());


                if (TextUtils.isEmpty(message)){
                    message_et.setError("Enter Message");
                    message_et.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(batch)){
                    batch_et.setError("Batch number");
                    batch_et.requestFocus();
                    return;
                }


                Add_notice_pojo add_noticePojo =new Add_notice_pojo(message,date);
                databaseReference.child("Notice").child(batch).child(date).setValue(add_noticePojo);
                Log.e(TAG, "Date --- " + date);

                message_et.setText("");
                batch_et.setText("");
            }
        });

    }
}
