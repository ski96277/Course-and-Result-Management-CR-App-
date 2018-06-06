package com.example.imransk.buproject1.FragmentClass;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.imransk.buproject1.R;
import com.example.imransk.buproject1.pojoClass.Upload_file;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Calendar;

import static android.app.Activity.RESULT_OK;

/**
 * Created by imran sk on 6/1/2018.
 */

public class Add_Class_Note_F extends Fragment {

    TextView file_name_tv;
    EditText file_details;
    EditText batch_number;
    Button select_file_btn;
    Button upload_file_btn;
    ProgressBar progressBar;

    String file_details_string;
    String batch_string;
    String date;

    //the firebase objects for storage and database
    StorageReference storageReference;
    DatabaseReference databaseReference;

    //this is the pic pdf code used in file chooser
    final static int PICK_PDF_CODE = 2342;

    Uri uri;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.add_class_note_f,null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        file_name_tv =view.findViewById(R.id.file_name_TV);
        file_details=view.findViewById(R.id.file_details_ET);
        batch_number=view.findViewById(R.id.batch_note_ET);
        select_file_btn=view.findViewById(R.id.choose_file_btn);
        upload_file_btn=view.findViewById(R.id.upload_file_btn);
        progressBar=view.findViewById(R.id.progressbar_for_uploading);


        date = java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());


        //getting firebase objects
        storageReference = FirebaseStorage.getInstance().getReference();
        databaseReference = FirebaseDatabase.getInstance().getReference();



        select_file_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                select_file();
            }
        });
        upload_file_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                upload_file();

            }
        });
    }


    private void select_file() {
        //for greater than lolipop versions we need the permissions asked on runtime
        //so if the permission is not available user will go to the screen to allow storage permission
       /* if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                    Uri.parse("package:" + getPackageName()));
            startActivity(intent);
            return;
        }*/

       file_name_tv.setVisibility(View.VISIBLE);
        //creating an intent for file chooser
        Intent intent = new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select pdf"), PICK_PDF_CODE);
    }



    private void upload_file() {
        progressBar.setVisibility(View.VISIBLE);

//        Log.e("       Uri --- --- --- ", "upload_file: "+uri.toString() );
        file_details_string=file_details.getText().toString();
        batch_string=batch_number.getText().toString();

        Log.e("         text", "upload_file: "+file_details_string );
        Log.e("         text", "upload_file: "+batch_string );
        StorageReference file_path = storageReference.child("file").child(batch_string).child(file_details_string);
        file_path.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                String file_download_lisk=taskSnapshot.getDownloadUrl().toString();

                file_details_string=file_details.getText().toString();
                Upload_file uploadFile =new Upload_file(batch_string,file_download_lisk,file_details_string,date);
                databaseReference.child("file").child(batch_string).child(date).setValue(uploadFile);
                Toast.makeText(getContext(), "uploadFile success", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
                file_name_tv.setText("Submited file");


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(), "uploading Problem", Toast.LENGTH_SHORT).show();

            }
        });




    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        file_name_tv.setText("Not selected");
        //when the user choses the file
        if (requestCode == PICK_PDF_CODE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            //if a file is selected
            if (data.getData() != null) {
                //uploading the file
                 uri=data.getData();
                 file_name_tv.setText("Selected");
            }else{
                Toast.makeText(getContext(), "No file chosen", Toast.LENGTH_SHORT).show();
            }
        }
    }

}
