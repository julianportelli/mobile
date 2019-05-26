package com.example.cis2208;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class bPostsActivity extends AppCompatActivity implements PostAdapter.OnItemClickListener {

    private RecyclerView mRecyclerview;
    private PostAdapter mAdapter;

    private ProgressBar mProgressCircle;

    private FirebaseStorage mStorage;
    private DatabaseReference mDatabaseRef;
    private ValueEventListener mDBListener;
    private List<Post> mPosts;

    // Method to create all of the instances
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posts);

        bPostsActivity.this.setTitle("All Posts");

        mRecyclerview = findViewById(R.id.recycler_viewX);
        mRecyclerview.setHasFixedSize(true);
        mRecyclerview.setLayoutManager(new LinearLayoutManager(this));

        mProgressCircle = findViewById(R.id.progress_circle);

        mPosts = new ArrayList<>();
        mAdapter = new PostAdapter(bPostsActivity.this, mPosts);
        mRecyclerview.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(bPostsActivity.this);

        mStorage = FirebaseStorage.getInstance();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("posts");

        mDBListener = mDatabaseRef.addValueEventListener(new ValueEventListener() {
            // Detect from the database if the data has changed
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mPosts.clear();
                // Loop to display all of the posts which are of board /b/
                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    Post post = ds.getValue(Post.class);
                    if(post.getmBoard().equalsIgnoreCase("b")){
                        post.setmKey(ds.getKey());
                        mPosts.add(post);
                    }
                }

                // Inform the database that is has been changed
                mAdapter.notifyDataSetChanged();
                mProgressCircle.setVisibility(View.INVISIBLE);
            }

            // Display error in Toast if it fails
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(bPostsActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                mProgressCircle.setVisibility(View.INVISIBLE);
            }
        });
    }

    // Activates the deletion method to remove a specific post
    @Override
    public void onDeleteClick(int position) {
        Post selectedPost = mPosts.get(position); //get post at clicked position
        final String selectedKey = selectedPost.getmKey();
        StorageReference imageRef = mStorage.getReferenceFromUrl(selectedPost.getmImageUrl());
        // On successful deletion list post a Toast notification method
        imageRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                mDatabaseRef.child(selectedKey).removeValue();
                Toast.makeText(bPostsActivity.this, "Post deleted", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mDatabaseRef.removeEventListener(mDBListener);
    }
}
