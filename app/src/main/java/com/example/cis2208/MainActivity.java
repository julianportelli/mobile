package com.example.cis2208;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;
    private static final int PICK_IMAGE_REQUEST = 1;

    private EditText mEditTextPostName;
    private EditText mEditTextPostDescription;
    private Button mButtonChooseImage;
    private Button mButtonPost;
    private TextView mTextViewShowUploads;
    private EditText mEditTextFileName;
    private ImageView mImageView;
    private ProgressBar mProgressBar;
    private Uri mImageUri;

    private Button newPostButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mEditTextPostName = findViewById(R.id.post_title);
        mEditTextPostDescription = findViewById(R.id.post_description);
        mButtonChooseImage = findViewById(R.id.post_button_choose_image);
        mButtonPost = findViewById(R.id.post_button);
        mTextViewShowUploads = findViewById(R.id.text_view_show_uploads);
        mEditTextFileName = findViewById(R.id.post_image_file_name);
        mImageView = findViewById(R.id.post_image_view);
        mProgressBar = findViewById(R.id.progress_bar);

        mButtonChooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });

        mButtonPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        mTextViewShowUploads.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        if(savedInstanceState == null) { //if starting activity for first item or if leaving by pressing back button and get back to it. if device rotated it will not be null
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new bFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_b);
        }
    }

    private void openFileChooser(){
        Intent intent = new Intent();
        intent.setType("image/*"); //only images in file chooser
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null){ //check image request, if user actually picked image, and if we actually get smth back
            mImageUri = data.getData();
            Picasso.get().load(mImageUri).into(mImageView);
        }
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.nav_b:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new bFragment()).commit();
                break;
            case R.id.nav_pol:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new polFragment()).commit();
                break;
            case R.id.nav_g:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new gFragment()).commit();
                break;
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if(drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }else{
            super.onBackPressed();
        }
    }
}
