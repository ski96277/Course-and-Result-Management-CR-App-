package com.example.imransk.buproject1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class LoginSuccessActivity extends AppCompatActivity {
    FirebaseAuth auth;
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_success);

        auth = FirebaseAuth.getInstance();

        tv = findViewById(R.id.emailshow);
        tv.setText("Login success " + auth.getCurrentUser().getEmail());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.logout:

                auth.signOut();
                if (auth.getCurrentUser() == null) {
                    startActivity(new Intent(getApplicationContext(), LogInActivity.class));
                    finish();
                } else {

                    Toast.makeText(this, "" + auth.getCurrentUser().getEmail(), Toast.LENGTH_LONG).show();
                }
                break;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}