package com.example.imransk.buproject1;


import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.imransk.buproject1.pojoClass.SignUpPojo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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
    String fullName="";
    String departmetnName="";
    String batchNumber="";



    String userID = "";
    private FirebaseAuth auth;
    private FirebaseUser firebaseUser;
    private FirebaseDatabase firebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_sign_up);

        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();


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

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this, android.R.style.Theme_Material_Dialog_Alert);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.customdialogwithinput, null);
        dialogBuilder.setView(dialogView);

        full_name_ET = (EditText) dialogView.findViewById(R.id.fullName_ET);
        depart_ET = (EditText) dialogView.findViewById(R.id.department_ET);
        batch_ET = (EditText) dialogView.findViewById(R.id.batch_number_ET);

        dialogBuilder.setTitle("Enter Your Information");
        dialogBuilder.setMessage("Enter text below");
        dialogBuilder.setCancelable(false);
        dialogBuilder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //do something with edt.getText().toString();
               /* if (TextUtils.isEmpty(fullName)){
                    full_name_ET.setError("Your name");
                    full_name_ET.requestFocus();
                    return;
                }if (TextUtils.isEmpty(departmetnName)){
                    depart_ET.setError("Department Name");
                    depart_ET.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(batchNumber)){
                    batch_ET.setError("Batch Name");
                }*/
                signUP();
            }
        });
        dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //pass
            }
        });
        dialogBuilder.show();
    }
    public void signUP() {

//get radio button status

        rdGroup = ((RadioButton) findViewById(radioGroup.getCheckedRadioButtonId())).getText().toString();


 //Get Text from Information alert dialog

        fullName=full_name_ET.getText().toString();
        departmetnName=depart_ET.getText().toString();
        batchNumber=batch_ET.getText().toString();

//        Toast.makeText(this, "" + rdGroup, Toast.LENGTH_SHORT).show();

        progressBar.setVisibility(View.VISIBLE);


//        Toast.makeText(this, "" + full_name_ET.getText(), Toast.LENGTH_SHORT).show();


        //create user
        final String finalRdGroup = rdGroup;

        if (TextUtils.isEmpty(fullName)){
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
                            DatabaseReference databaseReference = firebaseDatabase.getReference(finalRdGroup);

                            final String status = "0";
                            final SignUpPojo signUpPojo = new SignUpPojo(status, userID, finalRdGroup, email,fullName,departmetnName,batchNumber);

                            databaseReference.child(userID).setValue(signUpPojo);

                            progressBar.setVisibility(View.GONE);


                            //go Login Activity and than go LoginSuccess Activity
                            startActivity(new Intent(getApplicationContext(), LogInActivity.class));
                            finish();
                        } else {
                            // If sign in fails, display a message to the user.

                            Toast.makeText(SignUpActivity.this, "Sorry Having some problem \nSignUp Failed", Toast.LENGTH_SHORT).show();

                            progressBar.setVisibility(View.GONE);
                        }

                        // ...
                    }
                });


    }
   /* @Override
    protected void onResume() {
        super.onResume();
        progressBar.setVisibility(View.GONE);
    }*/
}
