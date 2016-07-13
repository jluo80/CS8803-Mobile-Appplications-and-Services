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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

/**
 * Created by Jiahao on 7/8/2016.
 */
public class EbayRecyclerAdapter extends RecyclerView.Adapter<EbayRecyclerAdapter.ViewHolder>{

    private Context mContext;
    private ArrayList<Gift> mGifts;
    private ImageLoader mImageLoader;
    private DatabaseReference mDatabase;

    EbayRecyclerAdapter(Context context, ArrayList<Gift> gifts) {
        this.mContext = context;
        this.mGifts = gifts;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.ebay_search_card_view, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, int i) {

        final Gift gift = mGifts.get(i);
        viewHolder.giftTitle.setText(gift.getName());
        viewHolder.currentPrice.setText("US $"+ gift.getPrice());

        // Get the ImageLoader through your singleton class.
        mImageLoader = MySingleton.getInstance(viewHolder.giftTitle.getContext()).getImageLoader();
        viewHolder.giftPicture.setImageUrl(gift.getPicture_url(), mImageLoader);

        // Set up onClick events
        viewHolder.giftTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Uri itemUri = Uri.parse(gift.getItem_url());
                Context context = view.getContext();
                context.startActivity(new Intent(Intent.ACTION_VIEW, itemUri));
            }
        });

        viewHolder.giftPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = view.getContext();
                context.startActivity(new Intent(mContext, AddGiftsActivity.class));
            }
        });

        // Set onClickListener for the image button to confirm the gift for the user.
        viewHolder.buyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /** Save Gift data to Firebase. */

                mDatabase = FirebaseDatabase.getInstance().getReference();
                String uniqueKey = mDatabase.child("gift").push().getKey();
                mDatabase.child("gift").child(uniqueKey).child("item_id").setValue(gift.getItem_id());
                mDatabase.child("gift").child(uniqueKey).child("due_date").setValue(gift.getDue_date());
                mDatabase.child("gift").child(uniqueKey).child("item_url").setValue(gift.getItem_url());
                mDatabase.child("gift").child(uniqueKey).child("initiator_id").setValue(gift.getInitiator_id());
                mDatabase.child("gift").child(uniqueKey).child("name").setValue(gift.getName());
                mDatabase.child("gift").child(uniqueKey).child("picture_url").setValue(gift.getPicture_url());
                mDatabase.child("gift").child(uniqueKey).child("post_time").setValue(gift.getPost_time());
                mDatabase.child("gift").child(uniqueKey).child("price").setValue(gift.getPrice());
//                Double.parseDouble(gift.getCurrentPrice())
                mDatabase.child("gift").child(uniqueKey).child("progress").setValue(0);
                mDatabase.child("gift").child(uniqueKey).child("reason").setValue("birthday");
                mDatabase.child("gift").child(uniqueKey).child("receiver_id").setValue("10208340919458244");

                mDatabase.child("user/" + gift.getInitiator_id() + "/my_gift/wish_list").child(uniqueKey).setValue(true);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mGifts.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        //        private final TextView mTextView;
        CardView mCardView;
        TextView giftTitle;
        TextView currentPrice;
        NetworkImageView giftPicture;
        TextView buyButton;

        ViewHolder(View view) {
            super(view);
            mCardView = (CardView)itemView.findViewById(R.id.cv);
            giftTitle = (TextView)itemView.findViewById(R.id.gift_title);
            currentPrice = (TextView)itemView.findViewById(R.id.current_price);
            giftPicture = (NetworkImageView)itemView.findViewById(R.id.gift_picture);
            buyButton = (TextView)itemView.findViewById(R.id.buy);
        }
    }

}
