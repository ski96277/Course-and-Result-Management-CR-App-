package com.example.imransk.buproject1;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class Forgot_Pass_Activity extends AppCompatActivity {

    EditText email_ET_pass;
    String email;
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot__pass);
        email_ET_pass=findViewById(R.id.email_ET_Reset);

    }

    public void Reset_Password(View view) {

        email=email_ET_pass.getText().toString();
        auth=FirebaseAuth.getInstance();
        if (TextUtils.isEmpty(email)){
            email_ET_pass.setError("Enter email address...!");
            email_ET_pass.requestFocus();
            return;
        }

        auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                Toast.makeText(Forgot_Pass_Activity.this, "Check Your mail", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Forgot_Pass_Activity.this,LogInActivity.class));
            }
        });


    }
}
