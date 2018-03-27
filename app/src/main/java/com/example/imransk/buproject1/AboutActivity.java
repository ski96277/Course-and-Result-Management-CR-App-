package com.example.imransk.buproject1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AboutActivity extends AppCompatActivity {
FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

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
        }

        return super.onOptionsItemSelected(item);

    }
}
