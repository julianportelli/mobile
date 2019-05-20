package com.example.cis2208;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class newPostActivity extends AppCompatActivity { //??? don't use for now

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

    private Spinner spinner;
    private static final String[] boards = {"b", "pol", "g"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

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
}
