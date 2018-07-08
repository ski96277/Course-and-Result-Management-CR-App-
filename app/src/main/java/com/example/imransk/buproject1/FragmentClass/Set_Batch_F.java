package com.example.imransk.buproject1.FragmentClass;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.imransk.buproject1.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by imran sk on 6/27/2018.
 */

public class Set_Batch_F extends Fragment {

    EditText batch_number_ET;
    Button set_batch_number_btn;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.set_batch_f,null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        batch_number_ET=view.findViewById(R.id.set_batch_number_ET);
        set_batch_number_btn=view.findViewById(R.id.set_batch_number_Btn);
        set_batch_number_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (TextUtils.isEmpty(batch_number_ET.getText().toString())){
                    batch_number_ET.setError("Enter batch Number");
                    batch_number_ET.requestFocus();
                    return;
                }
                firebaseDatabase=FirebaseDatabase.getInstance();
                databaseReference=firebaseDatabase.getReference();
                databaseReference.child("Batch Number").child("batch_number").setValue(batch_number_ET.getText().toString());
                databaseReference.child("Roll Number").child("student_roll").setValue("001");
                Toast.makeText(getContext(), "Set batch "+batch_number_ET.getText().toString()+"\nAnd\nID number 001", Toast.LENGTH_LONG).show();

                batch_number_ET.setText("");
            }
        });
    }
}
