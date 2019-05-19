package com.example.cis2208;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class bFragment extends Fragment {

    FloatingActionButton newPostBtn;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_b, container, false);
        newPostBtn = view.findViewById(R.id.newPostButton);
        newPostBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNewPostActivity();
            }
        });
        return view;
    }

    public void openNewPostActivity(){
        Intent intent = new Intent(getActivity(), PostActivity.class);
        startActivity(intent);
    }
}
