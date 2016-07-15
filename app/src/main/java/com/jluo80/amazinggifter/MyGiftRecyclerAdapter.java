package com.jluo80.amazinggifter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.util.ArrayList;


public class MyGiftRecyclerAdapter extends RecyclerView.Adapter<MyGiftRecyclerAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<Gift> mGifts;
    private ImageLoader mImageLoader;

    MyGiftRecyclerAdapter(Context context, ArrayList<Gift> gifts) {
        this.mContext = context;
        this.mGifts = gifts;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.my_gift_card_view, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {

        final Gift gift = mGifts.get(i);

        /** Gift picture setup.
         * Get the ImageLoader through your singleton class. */
        mImageLoader = MySingleton.getInstance(viewHolder.giftTitle.getContext()).getImageLoader();
        viewHolder.giftPicture.setImageUrl(gift.getPicture_url(), mImageLoader);

        /** Gift title and gift price setup*/
        viewHolder.giftTitle.setText(gift.getName());
        viewHolder.currentPrice.setText("US $" + Double.toString(gift.getPrice()));

        /** Progress bar setup. */
//        viewHolder.progressBar.setProgress(30);
        viewHolder.progressBar.setProgress((int)(gift.getProgress()/gift.getPrice()));
        viewHolder.currentTotal.setText(Double.toString(gift.getProgress()/gift.getPrice()) + "%");

        /** Gift title and gift price setup*/
        viewHolder.reason.setText(gift.getReason());
        viewHolder.dueDate.setText(gift.getDue_date());

        /** Setup onClickListener to gift picture and gift title. */
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
                Intent intent = new Intent(mContext, ContributionActivity.class);
                intent.putExtra("unique_key", gift.getUnique_key());
                intent.putExtra("category", gift.getCategory());
                intent.putExtra("due_date", gift.getDue_date());
                intent.putExtra("initiator_id", gift.getInitiator_id());
                intent.putExtra("item_id", gift.getItem_id());
                intent.putExtra("item_url", gift.getItem_url());
                intent.putExtra("name", gift.getName());
                intent.putExtra("picture_url", gift.getPicture_url());
                intent.putExtra("post_time", gift.getPost_time());
                intent.putExtra("price", Double.toString(gift.getPrice()));
                intent.putExtra("progress", gift.getProgress());
                intent.putExtra("reason", gift.getReason());
                intent.putExtra("receiver_id", gift.getReceiver_id());

                context.startActivity(intent);
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
        ProgressBar progressBar;
        TextView currentTotal;
        TextView reason;
        TextView dueDate;


        ViewHolder(View view) {
            super(view);
            mCardView = (CardView)itemView.findViewById(R.id.cv);
            giftTitle = (TextView)itemView.findViewById(R.id.gift_title);
            currentPrice = (TextView)itemView.findViewById(R.id.current_price);
            giftPicture = (NetworkImageView)itemView.findViewById(R.id.gift_picture);
            progressBar = (ProgressBar) itemView.findViewById(R.id.progress);
            currentTotal = (TextView) itemView.findViewById(R.id.current_total);
            reason = (TextView) itemView.findViewById(R.id.reason);
            dueDate = (TextView) itemView.findViewById(R.id.due_date);
        }
    }

}
