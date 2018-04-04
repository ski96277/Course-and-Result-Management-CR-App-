package com.example.imransk.buproject1;


import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.imransk.buproject1.pojoClass.SignUpPojo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;

public class SignUpActivity extends Activity {
    private EditText email_input, password_input;
    private Button signIn_btn, signUp_btn;
    private ProgressBar progressBar;
    private RadioGroup radioGroup;
    private RadioButton radioButton_ST, radioButton_FT;


    String rdGroup = "";
    String email;
    String password;

    //EditText for custom dialog alert
    private EditText full_name_ET;
    private EditText depart_ET;
    private EditText batch_ET;
    String fullName = "";
    String departmetnName = "";
    String batchNumber = "";
    CircleImageView circleImageView;
    int profile_image_Code = 2;
    Uri imageUri;
    Uri imageUri_download_Link;


    String userID = "";
    private FirebaseAuth auth;
    private FirebaseUser firebaseUser;
    private FirebaseDatabase firebaseDatabase;
    private StorageReference storageReference_root;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_sign_up);

        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();
        //for storage
        storageReference_root = FirebaseStorage.getInstance().getReference();

        firebaseDatabase = FirebaseDatabase.getInstance();

        signIn_btn = (Button) findViewById(R.id.sign_in_button);
        signUp_btn = (Button) findViewById(R.id.sign_up_button);
        email_input = (EditText) findViewById(R.id.email);
        password_input = (EditText) findViewById(R.id.password);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        radioGroup = findViewById(R.id.radiogroup);

        radioButton_ST = findViewById(R.id.radio_student);
        radioButton_FT = findViewById(R.id.radio_faculty);

        //Go Login Page
        signIn_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), LogInActivity.class));
                finish();
            }
        });


//Call signup method
        signUp_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                signUpFormValidation();
            }
        });
    }

    private void signUpFormValidation() {
        email = email_input.getText().toString().trim();
        password = password_input.getText().toString().trim();

//signUpFormValidation signUp
        if (TextUtils.isEmpty(email)) {
            email_input.setError("Enter E-mail");
            email_input.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(password)) {

            password_input.setError("Enter password minmum 6 characters");
            password_input.requestFocus();
            return;
        }

        if (password.length() < 6) {
            password_input.setError("Enter password minmum 6 characters");
            password_input.requestFocus();
            return;
        }

        if (radioButton_FT.isChecked() || radioButton_ST.isChecked()) {
            showTheInformationField();
        } else {
            radioButton_FT.setError("check radio Button");
            radioButton_ST.setError("check radio Button");
            radioButton_ST.requestFocus();
            radioButton_FT.requestFocus();
            Toast.makeText(this, "Please checked the radio button", Toast.LENGTH_SHORT).show();
        }
    }

    private void showTheInformationField() {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this, R.style.AlertDialog);
        final LayoutInflater inflater = this.getLayoutInflater();

        String called_from = getIntent().getStringExtra("called");
        final View dialogView;


        dialogView = inflater.inflate(R.layout.customdialogwithinput, null);
        dialogBuilder.setView(dialogView);
        full_name_ET = (EditText) dialogView.findViewById(R.id.fullName_ET);
        depart_ET = (EditText) dialogView.findViewById(R.id.department_ET);
        batch_ET = (EditText) dialogView.findViewById(R.id.batch_number_ET);
        circleImageView = (CircleImageView) dialogView.findViewById(R.id.circle_image_view);

//set Action on Image view
        circleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*")
                        .setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "select image"), profile_image_Code);
            }
        });
        dialogBuilder.setCancelable(false);
        dialogBuilder.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {

                signUP();
            }
        });
        dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //pass
            }
        }).create();

        dialogBuilder.show();
    }

    public void signUP() {

//get radio button status

        rdGroup = ((RadioButton) findViewById(radioGroup.getCheckedRadioButtonId())).getText().toString();


        //Get Text from Information alert dialog

        fullName = full_name_ET.getText().toString();
        departmetnName = depart_ET.getText().toString();
        batchNumber = batch_ET.getText().toString();

//        Toast.makeText(this, "" + rdGroup, Toast.LENGTH_SHORT).show();

        progressBar.setVisibility(View.VISIBLE);


//        Toast.makeText(this, "" + full_name_ET.getText(), Toast.LENGTH_SHORT).show();


        //create user
        final String finalRdGroup = rdGroup;

        if (TextUtils.isEmpty(fullName)) {
            full_name_ET.setError("your name");
            full_name_ET.requestFocus();
            return;
        }
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information

                            Toast.makeText(SignUpActivity.this, " SignUp Success", Toast.LENGTH_SHORT).show();

                            //Add data to database
                            firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                            userID = firebaseUser.getUid();
                            final DatabaseReference databaseReference = firebaseDatabase.getReference(finalRdGroup);

//                            upload_profile_image();
//                            Log.e("imageUri_download_Link", "onComplete: " + imageUri_download_Link);
                            final String status = "0";

//image upload
                            StorageReference file_path = storageReference_root.child(userID).child("image").child("profile/profilepic.jpg");

                                file_path.putFile(imageUri)
                                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                            @Override
                                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                                Toast.makeText(SignUpActivity.this, "Profile image Uploaded", Toast.LENGTH_SHORT).show();

                                                imageUri_download_Link = taskSnapshot.getDownloadUrl();
                                                Log.e("image uri ------ ", "onSuccess: " + imageUri_download_Link);

                                                final SignUpPojo signUpPojo = new SignUpPojo(status, userID, finalRdGroup, email, fullName, departmetnName, batchNumber,String.valueOf(imageUri_download_Link));

                                                databaseReference.child(userID).setValue(signUpPojo);
                                                progressBar.setVisibility(View.GONE);


                                                //go Login Activity and than go LoginSuccess Activity
                                                startActivity(new Intent(getApplicationContext(), LogInActivity.class));
                                                finish();

                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {

                                    }
                                });
//image upload end


                        } else {
                            // If sign in fails, display a message to the user.

                            Toast.makeText(SignUpActivity.this, "Sorry Having some problem \nSignUp Failed", Toast.LENGTH_SHORT).show();

                            progressBar.setVisibility(View.GONE);
                        }

                        // ...
                    }
                });


    }

    //upload image to storage
   /* private void upload_profile_image() {
      StorageReference  file_path = storageReference_root.child(userID).child("image").child("profile/profilepic.jpg");
        file_path.putFile(imageUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Toast.makeText(SignUpActivity.this, "Profile image Uploaded", Toast.LENGTH_SHORT).show();

                        imageUri_download_Link=taskSnapshot.getDownloadUrl();
                        Log.e("image uri ------ ", "onSuccess: "+imageUri_download_Link );

                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });

    }*/

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        imageUri = data.getData();

        if (requestCode == profile_image_Code && resultCode == RESULT_OK && data != null && data.getData() != null) {
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                circleImageView.setImageBitmap(bitmap);
//                Picasso.get().load(imageUri).into(circleImageView);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}
