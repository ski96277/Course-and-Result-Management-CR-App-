package com.example.imransk.buproject1.Adapter;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.imransk.buproject1.R;
import com.example.imransk.buproject1.pojoClass.Upload_file;

import java.util.ArrayList;

/**
 * Created by imran sk on 6/5/2018.
 */

public class Class_Note_ListView_Adapter extends BaseAdapter {

    ArrayList<Upload_file> addFile_list;
    Context context;
    Long id_enque;

    LayoutInflater layoutInflater = null;

    public Class_Note_ListView_Adapter(Context context, ArrayList<Upload_file> addFile_list) {

        this.context = context;
        this.addFile_list = addFile_list;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount() {
        return addFile_list.size();
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

        final View class_note_list_View;
        class_note_list_View = layoutInflater.inflate(R.layout.custom_class_note_list, null);

        TextView date_tv = class_note_list_View.findViewById(R.id.date_class_note_TV);
        TextView details_tv = class_note_list_View.findViewById(R.id.details_class_note_TV);
        Button download_btn = class_note_list_View.findViewById(R.id.download_class_note_btn);

        Upload_file upload_file = addFile_list.get(i);

        String date = upload_file.getDate();
        String details = upload_file.getDetails();
        final String download_link = upload_file.getUrl();
        date_tv.setText(date);
        details_tv.setText(details);
//        download_Tv.setText(download_link);

        class_note_list_View.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(context, "hello", Toast.LENGTH_SHORT).show();

                Intent intent=new Intent();
                intent.setType(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(download_link));
                context.startActivity(intent);
            }
        });
        download_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                File file=new File(download_link);
//                Toast.makeText(context, "btn", Toast.LENGTH_SHORT).show();

                Intent intent=new Intent();
                intent.setType(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(download_link));
                context.startActivity(intent);

               /* DownloadManager downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
                DownloadManager.Request request = new DownloadManager.Request(Uri.parse(download_link));

                id_enque = downloadManager.enqueue(request);*/


            }

        });//end get button click


        return class_note_list_View;
    }


}
