package com.example.imransk.buproject1.Activity;


import android.app.Activity;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.icu.text.SimpleDateFormat;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.imransk.buproject1.R;
import com.example.imransk.buproject1.pojoClass.SignUpPojo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.hdodenhof.circleimageview.CircleImageView;

public class SignUpActivity extends Activity {
    private EditText email_input;
    private EditText password_input;
    private Button signIn_btn, signUp_btn;
    private ProgressBar progressBar;
    private RadioGroup radioGroup;
    private RadioButton radioButton_ST, radioButton_FT;


    String rdGroup = "";
    String email;
    String password;

    //EditText for custom dialog alert
    private EditText full_name_ET;
    private TextView select_img_ET;
    private EditText depart_ET;
    private EditText batch_ET;
    private EditText address_ET;
    private EditText phone_num_ET;
    String fullName = "";
    String departmetnName = "";
    String batchNumber = "";
    String phoneNumber = "";
    String iD = "";
    String address = "";

    String semester_number;
    String roll_number;

    int year;
    int month;


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

        Calendar now = Calendar.getInstance();

        Log.e("      Year ", "onCreate: " + now.get(Calendar.YEAR));
        year = now.get(Calendar.YEAR);
        // month start from 0 to 11
        Log.e("      month ", "onCreate: " + (now.get(Calendar.MONTH) + 1));
        month = now.get(Calendar.MONTH) + 1;

        Log.e("      day ", "onCreate: " + now.get(Calendar.DATE));


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
        if (!validEmail(email)) {
            email_input.setError("Enter valid E-mail");
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

    //E-mail validation
    private boolean validEmail(String email) {
        String email_pattern = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";
        Pattern pattern = Pattern.compile(email_pattern);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    private void showTheInformationField() {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this, R.style.AlertDialog);
        final LayoutInflater inflater = this.getLayoutInflater();

        final View dialogView;


        dialogView = inflater.inflate(R.layout.customdialogwithinput, null);
        dialogBuilder.setView(dialogView);

        rdGroup = ((RadioButton) findViewById(radioGroup.getCheckedRadioButtonId())).getText().toString();

        if (rdGroup.equals("faculty")){
            LinearLayout linearLayout=(LinearLayout)dialogView.findViewById(R.id.batch_ET_layout);
            linearLayout.setVisibility(View.GONE);
        }

        full_name_ET = (EditText) dialogView.findViewById(R.id.fullName_ET);
        select_img_ET = (TextView) dialogView.findViewById(R.id.select_image_TV);
        depart_ET = (EditText) dialogView.findViewById(R.id.department_ET);
        batch_ET = dialogView.findViewById(R.id.batch_ET);
        address_ET = dialogView.findViewById(R.id.address_ET);
        phone_num_ET = (EditText) dialogView.findViewById(R.id.phone_number_ET);
        address_ET = dialogView.findViewById(R.id.address_ET);


        circleImageView = (CircleImageView) dialogView.findViewById(R.id.circle_image_view);

//set Action on Image view
        circleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*")
                        .setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "select image"), profile_image_Code);
                select_img_ET.setVisibility(View.GONE);
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

        FirebaseDatabase firebaseDatabase2 = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase2.getReference();

//      semester_number create

        if (month == 1 || month == 2 || month == 3 || month == 4) {
            semester_number = "1";
        }
        if (month == 5 || month == 6 || month == 7 || month == 8) {
            semester_number = "2";
        }
        if (month == 9 || month == 10 || month == 11 || month == 12) {
            semester_number = "3";
        }
        Log.e("        semester Numebr", "signUP:    " + semester_number);


        //Get Text from Information alert dialog

        batchNumber = batch_ET.getText().toString();
        fullName = full_name_ET.getText().toString();
        departmetnName = depart_ET.getText().toString();
        phoneNumber = phone_num_ET.getText().toString();
        address = address_ET.getText().toString();

//get radio button status

        rdGroup = ((RadioButton) findViewById(radioGroup.getCheckedRadioButtonId())).getText().toString();


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (rdGroup.equals("Student")) {
                    roll_number = dataSnapshot.child("Roll Number").child("student_roll")
                            .getValue().toString();
                    iD = String.valueOf(year).toString() + semester_number + "10" + batchNumber + roll_number.toString();
                }

                if (rdGroup.equals("faculty")){
                    roll_number = dataSnapshot.child("Roll Number").child("faculty_roll")
                            .getValue().toString();
                    iD = String.valueOf(year).toString() +"10" +roll_number.toString();
                }

                Log.e("     batch database ", "onDataChange: " + batchNumber);
                Log.e("     id database ", "onDataChange: " + iD);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


//        Toast.makeText(this, "" + rdGroup, Toast.LENGTH_SHORT).show();

        progressBar.setVisibility(View.VISIBLE);


//        Toast.makeText(this, "" + full_name_ET.getText(), Toast.LENGTH_SHORT).show();


        //create user
        final String finalRdGroup = rdGroup;

        if (rdGroup.equals("faculty")){
            batchNumber="having no batch";
        }

        // Image and text field is not complete  it's can't sign in
        if (imageUri != null && !fullName.isEmpty() && !departmetnName.isEmpty() && !batchNumber.isEmpty() && !phoneNumber.isEmpty() && !address.isEmpty()) {

            auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information


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

                                                final SignUpPojo signUpPojo = new SignUpPojo(status, userID, finalRdGroup, email, fullName, departmetnName, batchNumber, phoneNumber, iD, String.valueOf(imageUri_download_Link), address);

                                                databaseReference.child(userID).setValue(signUpPojo);
                                                progressBar.setVisibility(View.GONE);
                                                Toast.makeText(SignUpActivity.this, " SignUp Success", Toast.LENGTH_SHORT).show();

                                                //Roll Number Increment
                                                int roll = Integer.parseInt(roll_number) + 1;
                                                String roll_string = String.valueOf(roll);

                                                Log.e("Length ", "onSuccess: " + roll_string.length());
//set 3 digit number
                                                if (roll_string.length() == 1) {
                                                    roll_string = "00" + roll_string;
                                                }
                                                if (roll_string.length() == 2) {
                                                    roll_string = "0" + roll_string;
                                                }
                                                if (rdGroup.equals("Student")){
                                                    firebaseDatabase.getReference().child("Roll Number").child("student_roll").setValue(roll_string);
                                                }
                                                if (rdGroup.equals("faculty")){
                                                    firebaseDatabase.getReference().child("Roll Number").child("faculty_roll").setValue(roll_string);

                                                }
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
        } else {
            progressBar.setVisibility(View.GONE);

            if (imageUri == null) {
                Toast.makeText(this, "select Profile Image", Toast.LENGTH_SHORT).show();

            } else if (fullName.isEmpty()) {
                Toast.makeText(this, "Enter Name", Toast.LENGTH_SHORT).show();

            } else if (departmetnName.isEmpty()) {
                Toast.makeText(this, "Enter department Name", Toast.LENGTH_SHORT).show();

            } else if (batchNumber.isEmpty()) {
                Toast.makeText(this, "Enter batch number", Toast.LENGTH_SHORT).show();

            } else if (phoneNumber.isEmpty()) {
                Toast.makeText(this, "Enter phone number", Toast.LENGTH_SHORT).show();

            } else if (address.isEmpty()) {
                Toast.makeText(this, "Enter Address number", Toast.LENGTH_SHORT).show();

            }

        }


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);



      /*  if (imageUri==null){
            imageUri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE +
                    "://" + getResources().getResourcePackageName(R.drawable.boy)
                    + '/' + getResources().getResourceTypeName(R.drawable.boy)
                    + '/' + getResources().getResourceEntryName(R.drawable.boy) );
            Log.e("           ", "onActivityResult: "+imageUri );

        }*/

        if (requestCode == profile_image_Code && resultCode == RESULT_OK && data != null && data.getData() != null) {


            imageUri = data.getData();

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
