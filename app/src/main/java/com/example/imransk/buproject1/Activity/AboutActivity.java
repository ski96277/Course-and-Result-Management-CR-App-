package com.example.imransk.buproject1.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.imransk.buproject1.R;
import com.google.firebase.auth.FirebaseAuth;

public class AboutActivity extends AppCompatActivity {
FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("About Page");
        setContentView(R.layout.activity_about);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main,menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        firebaseAuth=FirebaseAuth.getInstance();

//        firebaseUser =firebaseAuth.getCurrentUser();

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.logout) {
            firebaseAuth.signOut();
            if (firebaseAuth.getCurrentUser() == null) {
                Toast.makeText(getApplicationContext(), "Log out Success", Toast.LENGTH_SHORT).show();

                startActivity(new Intent(getApplicationContext(), LogInActivity.class));
                finish();
            }

            return true;
        }else if (id==R.id.home_page){
            startActivity(new Intent(getApplicationContext(),LoginSuccessActivity.class));
            finish();
        }

        return super.onOptionsItemSelected(item);

    }
}
