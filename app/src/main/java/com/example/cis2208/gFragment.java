package com.example.cis2208;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

// Fragment for the /g/ board

public class gFragment extends Fragment {

    FloatingActionButton newPostBtn;

    private RecyclerView mRecyclerView;
    private PostAdapter mAdapter;
    private DatabaseReference mDatabaseRef;
    private List<Post> mPosts;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_g, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mRecyclerView = view.findViewById(R.id.recycler_g);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mPosts = new ArrayList<>();

        mDatabaseRef = FirebaseDatabase.getInstance().getReference().child("posts"); //find correct 'table' in database where to store posts

        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) { //lists posts in order that they were created, based on the name of the board
                for(DataSnapshot dsp : dataSnapshot.getChildren()){
                    Post post = dsp.getValue(Post.class);
                    if(post.getmBoard().equalsIgnoreCase("g")){
                        mPosts.add(post);
                    }
                }
                mAdapter = new PostAdapter(getActivity(), mPosts);
                mRecyclerView.setAdapter(mAdapter); //display the posts
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getActivity(), databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        //Button which opens the posts activity to add a new post
        newPostBtn = view.findViewById(R.id.newPostButton);
        newPostBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNewPostActivity();
            }
        });
    }

    public void openNewPostActivity(){
        Intent intent = new Intent(getActivity(), gPostActivity.class);
        startActivity(intent);
    }
}
