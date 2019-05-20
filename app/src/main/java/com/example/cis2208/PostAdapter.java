package com.example.cis2208;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> { //pass data to fragment

    private Context mContext;
    private List<Post> mPosts;

    public PostAdapter(){}

    public PostAdapter(Context context, List<Post> posts){
        mContext = context;
        mPosts = posts;
    }

    @Override
    public PostViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.post_item, parent, false);
        return new PostViewHolder(v);
    }

    @Override
    public void onBindViewHolder(PostViewHolder holder, int position) { //data out of our uploaded posts and into our card items
        Post postCurrent = mPosts.get(position);
        holder.TextViewPostTitle.setText(postCurrent.getmPostTitle());
        holder.TextViewPostImageName.setText(postCurrent.getmImageName());
        holder.TextViewPostDescription.setText(postCurrent.getmPostDescription());
        Picasso.get()
                .load(postCurrent.getmImageUrl())
                .fit()
                .centerCrop()
                .into(holder.ImageViewPostImage);
    }

    @Override
    public int getItemCount() {
        return mPosts.size();
    }

    public class PostViewHolder extends RecyclerView.ViewHolder{

        public TextView TextViewPostTitle;
        public ImageView ImageViewPostImage;
        public TextView TextViewPostImageName;
        public TextView TextViewPostDescription;

        public PostViewHolder(View itemView){
            super(itemView);

            TextViewPostTitle = itemView.findViewById(R.id.text_view_post_titleX);
            ImageViewPostImage = itemView.findViewById(R.id.image_view_post_imageX);
            TextViewPostImageName = itemView.findViewById(R.id.text_view_post_image_nameX);
            TextViewPostDescription = itemView.findViewById(R.id.text_view_post_descriptionX);
        }
    }
}
