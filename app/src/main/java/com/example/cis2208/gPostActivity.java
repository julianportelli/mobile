package com.example.cis2208;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class gPostActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;
    private EditText mEditTextPostTitle;
    private EditText mEditTextPostDescription;
    private Spinner mBoardSpinner;
    private Button mButtonChooseImage;
    private EditText mEditTextImageName;
    private Button mButtonPost;
    private TextView mTextViewShowUploads;
    private ImageView mImageView;
    private ProgressBar mProgressBar;
    private Uri mImageUri;

    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        gPostActivity.this.setTitle("New Post");

        mEditTextPostTitle = findViewById(R.id.post_titleX);
        mEditTextPostDescription = findViewById(R.id.post_descriptionX);
        mBoardSpinner = findViewById(R.id.boardSpinnerX);
        mButtonChooseImage = findViewById(R.id.post_button_choose_imageX);
        mEditTextImageName = findViewById(R.id.post_image_nameX);
        mButtonPost = findViewById(R.id.post_buttonX);
        mTextViewShowUploads = findViewById(R.id.text_view_show_uploadsX);
        mImageView = findViewById(R.id.post_image_viewX);
        mProgressBar = findViewById(R.id.progress_barX);

        mStorageRef = FirebaseStorage.getInstance().getReference("posts");
        mDatabaseRef = FirebaseDatabase.getInstance().getReference();

        mButtonChooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });

        mButtonPost.setOnClickListener(new View.OnClickListener() { //upload post
            @Override
            public void onClick(View v) {
                uploadPost();
            }
        });


        mTextViewShowUploads.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPostsActivity();
            }
        });
    }

    private void openFileChooser(){
        Intent intent = new Intent();
        intent.setType("image/*"); //only images in file chooser
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) { //will be called when we pick our file
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null){ //check image request, if user actually picked image, and if we actually get smth back
            mImageUri = data.getData();
            Picasso.get().load(mImageUri).into(mImageView);
        }
    }

    private String getFileExtension(Uri uri){ //get extension from image ilfe
        ContentResolver cr = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(uri));
    }

    private void uploadPost(){
        if(mImageUri != null){ //if image actually picked
            StorageReference fileReference = mStorageRef.child(System.currentTimeMillis() + "." + getFileExtension(mImageUri));
            fileReference.putFile(mImageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {

                        private static final String TAG ="Posts Activity";

                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) { //delay reset of progress bar for 500ms
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    mProgressBar.setProgress(0);
                                }
                            }, 500);

                            Toast.makeText(gPostActivity.this, "Upload Successful!", Toast.LENGTH_LONG).show();

                            Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                            while(!uriTask.isSuccessful());
                            Uri downloadUrl = uriTask.getResult();

                            Log.d(TAG, "onSuccess: firebase download url: " + downloadUrl.toString());

                            Post post = new Post(
                                    mEditTextPostTitle.getText().toString().trim(),
                                    mEditTextPostDescription.getText().toString(),
                                    "g",
                                    mEditTextImageName.getText().toString().trim(),
                                    downloadUrl.toString());
                            String postId = mDatabaseRef.push().getKey();
                            mDatabaseRef.child("posts").child(postId).setValue(post);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(gPostActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount(); //gives us progress
                            mProgressBar.setProgress((int)progress);
                        }
                    });
        }else{
            Toast.makeText(this, "No file selected", Toast.LENGTH_SHORT).show();
        }
    }

    private void openPostsActivity(){
        Intent intent = new Intent(this, PostsActivity.class);
        startActivity(intent);
    }
}
