package com.example.cis2208;

public class Post {

    private String mPostTitle;
    private String mPostDescription;
    private String mBoard;
    private String mImageName;
    private String mImageUrl;

   public Post(){
    //empty constructor needed for Firebase
   }

    public Post(String PostTitle, String PostDescription, String Board, String ImageName, String ImageUrl){
        if(ImageName.trim().equals(""))
            ImageName = "No image name";

        if(ImageName.trim().equals(""))
            ImageName = "No post name";

        PostTitle = mPostTitle;
        PostDescription = mPostDescription;
        Board = mBoard;
        ImageName = mImageName;
        ImageUrl = mImageUrl;
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




}
