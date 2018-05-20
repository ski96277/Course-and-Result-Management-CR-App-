package com.example.imransk.buproject1.Adapter;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.imransk.buproject1.R;
import com.example.imransk.buproject1.pojoClass.SubmitResultPojo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by imran sk on 5/19/2018.
 */

public class SubjetResultAdapter extends BaseAdapter {

    Context context;
    ArrayList<String> subject_list;
    LayoutInflater layoutInflater;
    String batch_number;
    String iD_number;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    public SubjetResultAdapter(FragmentActivity activity, ArrayList<String> subject_list, String batch_number, String iD_number) {

        this.context = activity;
        this.subject_list = subject_list;
        this.batch_number=batch_number;
        this.iD_number=iD_number;

        Log.e(" Adapter ", "SubjetResultAdapter: length" + subject_list.size());
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return subject_list.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();


        final View listViewItem_Subject = layoutInflater.inflate(R.layout.custom_result_sheet, null);

       final TextView serial_num = listViewItem_Subject.findViewById(R.id.serial_number);
       final TextView subject_name = listViewItem_Subject.findViewById(R.id.sub_name);
       final TextView grade_and_mark_TV = listViewItem_Subject.findViewById(R.id.grade_and_mark);

        final String subject = subject_list.get(i);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Log.e("id ---===--", "Student Result Adapter: "+iD_number );
                Log.e("batch =- - -", "Student Result Adapter: "+batch_number);

                String grade = dataSnapshot.child("Result Sheet").child(batch_number)
                        .child(iD_number).child(subject).child("grade").getValue().toString();
                String mark = dataSnapshot.child("Result Sheet").child(batch_number)
                        .child(iD_number).child(subject).child("mark").getValue().toString();

                serial_num.setText(String.valueOf(i+1));
                subject_name.setText(subject);
                grade_and_mark_TV.setText(grade+" / "+mark);


                Log.e("grade --  --  ", "custom adapter : " + grade+" "+mark);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return listViewItem_Subject;
    }
}
