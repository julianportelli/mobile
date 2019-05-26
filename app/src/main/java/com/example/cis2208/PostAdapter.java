package com.example.cis2208;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

//Where we have a recycleview, we must have a recycleview adapter, which is our bridge between our data and the recyclerview itself
public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {

    private Context mContext;
    private List<Post> mPosts;

    private OnItemClickListener mListener;

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
        //load image from URL
        Picasso.get()
                .load(postCurrent.getmImageUrl())
                .placeholder(R.mipmap.ic_launcher)
                .fit()
                .centerCrop()
                .into(holder.ImageViewPostImage);
    }

    @Override
    public int getItemCount() {
        return mPosts.size();
    }

    public class PostViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, //post functionality
            View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener {

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

            itemView.setOnClickListener(this);
            itemView.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onClick(View v) {
            if(mListener != null){
                int position = getAdapterPosition();
                if(position != RecyclerView.NO_POSITION){
                    // Do nothing
                }
            }
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) { //Context menu display when post is long tapped
            menu.setHeaderTitle("Select Action");
            MenuItem delete = menu.add(Menu.NONE, 1, 1, "Delete post");
            delete.setOnMenuItemClickListener(this);
        }

        @Override
        public boolean onMenuItemClick(MenuItem item) { //method to delete post
            if(mListener != null){
                int position = getAdapterPosition();
                if(position != RecyclerView.NO_POSITION){
                    switch (item.getItemId()){
                        case 1:
                            mListener.onDeleteClick(position);
                            return true;
                    }
                }
            }
            return false;
        }
    }

    public interface OnItemClickListener{
        void onDeleteClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }

}
