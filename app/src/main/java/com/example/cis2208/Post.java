package com.example.cis2208;

import com.google.firebase.database.Exclude;

public class Post {

    private String mPostTitle;
    private String mPostDescription;
    private String mBoard;
    private String mImageName;
    private String mImageUrl;
    private String mKey;


    public Post() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Post(String PostTitle, String PostDescription, String Board, String ImageName, String ImageUrl) {
        if (ImageName.trim().equals("")) {
            ImageName = "No image name";
        }

        if (PostTitle.trim().equals("")) {
            PostTitle = "No post name";
        }

        mPostTitle = PostTitle;
        mPostDescription = PostDescription;
        mBoard = Board;
        mImageName = ImageName;
        mImageUrl = ImageUrl;
    }

    public String getmPostTitle() {
        return mPostTitle;
    }

    public void setmPostTitle(String mPostTitle) {
        this.mPostTitle = mPostTitle;
    }

    public String getmPostDescription() {
        return mPostDescription;
    }

    public void setmPostDescription(String mPostDescription) {
        this.mPostDescription = mPostDescription;
    }

    public String getmBoard() {
        return mBoard;
    }

    public void setmBoard(String mBoard) {
        this.mBoard = mBoard;
    }

    public String getmImageName() {
        return mImageName;
    }

    public void setmImageName(String mImageName) {
        this.mImageName = mImageName;
    }

    public String getmImageUrl() {
        return mImageUrl;
    }

    public void setmImageUrl(String mImageUrl) {
        this.mImageUrl = mImageUrl;
    }

    @Exclude
    public String getmKey() { //excluded from DB
        return mKey;
    }

    public void setmKey(String key) {
        mKey = key;
    }



}
