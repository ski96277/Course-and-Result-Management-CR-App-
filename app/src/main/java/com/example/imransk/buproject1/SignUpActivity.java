package com.example.imransk.buproject1;


import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignUpActivity extends Activity {
    private EditText email_input, password_input;
    private Button signIn_btn, signUp_btn;
    private ProgressBar progressBar;
    private RadioGroup radioGroup;
    private RadioButton radioButton_ST, radioButton_FT;
    String radioText = "";

    String userID="";
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
            }
        });
//Call signup method
        signUp_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUP();
            }
        });
    }

    public void signUP() {
        final String email = email_input.getText().toString().trim();
        String password = password_input.getText().toString().trim();

//validation signUp
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

        if (radioButton_FT.isChecked() || radioButton_ST.isChecked()){

//get radio button status
            String rdGroup = "";
            rdGroup = ((RadioButton) findViewById(radioGroup.getCheckedRadioButtonId())).getText().toString();
            Toast.makeText(this, "" + rdGroup, Toast.LENGTH_SHORT).show();

            progressBar.setVisibility(View.VISIBLE);
            //create user
            final String finalRdGroup = rdGroup;
            auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information

                                Toast.makeText(SignUpActivity.this, " SignUp Success", Toast.LENGTH_SHORT).show();

                                //Add data to database
                                firebaseUser =FirebaseAuth.getInstance().getCurrentUser();
                                userID=firebaseUser.getUid();
                                DatabaseReference databaseReference=firebaseDatabase.getReference(finalRdGroup);

                                final String status="0";
                                final SignUpPojo signUpPojo=new SignUpPojo(status,userID,finalRdGroup,email);

                                databaseReference.child(userID).setValue(signUpPojo);

                                progressBar.setVisibility(View.GONE);


                                //go Login Activity and than go LoginSuccess Activity
                                startActivity(new Intent(getApplicationContext(), LogInActivity.class));
                            } else {
                                // If sign in fails, display a message to the user.

                                Toast.makeText(SignUpActivity.this, "Sorry Having some problem \nSignUp Failed", Toast.LENGTH_SHORT).show();

                                progressBar.setVisibility(View.GONE);
                            }

                            // ...
                        }
                    });

        }else {
            radioButton_FT.setError("check radio Button");
            radioButton_ST.setError("check radio Button");
            radioButton_ST.requestFocus();
            radioButton_FT.requestFocus();
            Toast.makeText(this, "Please checked the radio button", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        progressBar.setVisibility(View.GONE);
    }
}
