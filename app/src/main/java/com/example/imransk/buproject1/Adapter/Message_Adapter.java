package com.example.imransk.buproject1.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.imransk.buproject1.FragmentClass.Message_Details_F;
import com.example.imransk.buproject1.R;
import com.example.imransk.buproject1.pojoClass.Add_notice_pojo;

import java.util.ArrayList;

/**
 * Created by imran sk on 6/2/2018.
 */

public class Message_Adapter extends BaseAdapter{

    ArrayList<Add_notice_pojo> addNoticesList;
    Context context;
            LayoutInflater layoutInflater=null;

    public Message_Adapter(Context context, ArrayList<Add_notice_pojo> addNoticesList) {

        this.addNoticesList=addNoticesList;
        this.context=context;
        layoutInflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount() {
        return addNoticesList.size();
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
    public View getView(int i, View view, ViewGroup viewGroup) {

        View notice_List_View=layoutInflater.inflate(R.layout.custom_message_list,null);

        Add_notice_pojo add_noticePojo =addNoticesList.get(i);

        final TextView date_set=notice_List_View.findViewById(R.id.date_TV);
        final TextView message_set=notice_List_View.findViewById(R.id.message_TV);
        date_set.setText(add_noticePojo.getDate());
        message_set.setText(add_noticePojo.getNotice());
        notice_List_View.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Bundle bundle_send=new Bundle();
                bundle_send.putString("date",date_set.getText().toString());
                bundle_send.putString("message",message_set.getText().toString());

                Fragment fragment=new Message_Details_F();
                if (fragment!=null){
                    FragmentTransaction fragmentTransaction=((FragmentActivity)context).getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.addToBackStack("");
                    fragment.setArguments(bundle_send);
                    fragmentTransaction.replace(R.id.screenArea,fragment);
                    fragmentTransaction.commit();
                }
            }
        });

        return notice_List_View;
    }
}
