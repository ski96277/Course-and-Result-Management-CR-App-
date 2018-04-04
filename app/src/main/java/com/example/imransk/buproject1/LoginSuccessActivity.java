package com.example.imransk.buproject1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.imransk.buproject1.FragmentClass.EditProfileF;
import com.example.imransk.buproject1.FragmentClass.HomePageF;
import com.example.imransk.buproject1.FragmentClass.ShowProfileF;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.StorageReference;

public class LoginSuccessActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    FirebaseAuth firebaseAuth;
    /*FirebaseUser firebaseUser;
    DatabaseReference databaseReference;
    String userId;
    String status_student = "";
    String status_faculty = "";
    String status_admin = "";
    TextView statusTV;*/

    ImageView imageView_nav;
    TextView textView_user_name_nav;
    TextView textView_user_Email_nav;
    String u_id;

    StorageReference storageReference;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_success);
        //hide notification bar
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View headerView = navigationView.getHeaderView(0);

        imageView_nav = headerView.findViewById(R.id.imageView_nav);
        textView_user_Email_nav = headerView.findViewById(R.id.textView_user_email);
        textView_user_name_nav = headerView.findViewById(R.id.texView_User_name);

        firebaseAuth = FirebaseAuth.getInstance();


        //Set Default Fragment
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.screenArea, new HomePageF());
        fragmentTransaction.commit();


    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.login_success, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        Fragment fragment = null;
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home_page) {
            // Handle the camera action
            fragment = new HomePageF();
//            checkStatus();


        } else if (id == R.id.nav_edit_profile) {
            fragment = new EditProfileF();

        } else if (id == R.id.nav_log_out) {
            firebaseAuth.signOut();
            finish();
            if (firebaseAuth.getCurrentUser() == null) {
                Toast.makeText(getApplicationContext(), "Log out Success", Toast.LENGTH_SHORT).show();

                startActivity(new Intent(getApplicationContext(), LogInActivity.class));
                finish();
            }

        } else if (id == R.id.nav_show_profile) {
            fragment=new ShowProfileF();

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }
        // skip null pointer exception and set fragment
        if (fragment != null) {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.screenArea, fragment);
            fragmentTransaction.commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
