package com.example.imransk.buproject1.FragmentClass;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StyleableRes;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.imransk.buproject1.R;

/**
 * Created by imran sk on 6/2/2018.
 */

public class Message_Details_F extends Fragment {
    TextView date_set_TV;
    TextView message_set_TV;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getActivity().setTitle("Message Details");
        return inflater.inflate(R.layout.message_details_f,null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        date_set_TV=view.findViewById(R.id.date_show_TV);
        message_set_TV=view.findViewById(R.id.messge_show_TV);

        Bundle bundle=getArguments();
        String date=bundle.getString("date");
        String message=bundle.getString("message");
        date_set_TV.setText(date);
        message_set_TV.setText(message);

    }
}
