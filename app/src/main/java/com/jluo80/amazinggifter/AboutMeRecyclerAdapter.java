package com.jluo80.amazinggifter;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import org.w3c.dom.Text;

import java.util.ArrayList;


public class AboutMeRecyclerAdapter extends RecyclerView.Adapter<AboutMeRecyclerAdapter.ViewHolder>{

    private Context mContext;
    private User mUser;
    private ImageLoader mImageLoader;

    AboutMeRecyclerAdapter(Context context, User user) {
        this.mContext = context;
        this.mUser = user;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fragment_about_me, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {

        viewHolder.username.setText(mUser.getName());
        viewHolder.email.setText(mUser.getEmail());
        viewHolder.birthday.setText(mUser.getBirthday());

        // Get the ImageLoader through your singleton class.
        mImageLoader = MySingleton.getInstance(viewHolder.username.getContext()).getImageLoader();
        viewHolder.profilePicture.setImageUrl(mUser.getProfilePictureUrl(), mImageLoader);
        viewHolder.coverPicture.setImageUrl(mUser.getCoverPictureUrl(), mImageLoader);

    }

    @Override
    public int getItemCount() {
//        return mUser.size();
        return 1;
   }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView username;
        TextView email;
        TextView birthday;
        NetworkImageView profilePicture;
        NetworkImageView coverPicture;

        TextView mobile;
        TextView address;

        ViewHolder(View view) {
            super(view);
            username = (TextView)itemView.findViewById(R.id.username);
            email = (TextView)itemView.findViewById(R.id.email);
            birthday = (TextView)itemView.findViewById(R.id.birthday);
            profilePicture = (NetworkImageView)itemView.findViewById(R.id.profile_picture);
            coverPicture = (NetworkImageView)itemView.findViewById(R.id.cover_picture);
//            mobile = (TextView)itemView.findViewById(R.id.mobile);
//            address = (TextView)itemView.findViewById(R.id.address);
        }
    }

}
