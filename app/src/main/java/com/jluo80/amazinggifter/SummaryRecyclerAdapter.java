package com.jluo80.amazinggifter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;


public class SummaryRecyclerAdapter extends RecyclerView.Adapter<SummaryRecyclerAdapter.ViewHolder>{

    private Context mContext;
    private ArrayList<Gift> mGifts;
    private ImageLoader mImageLoader;

    SummaryRecyclerAdapter(Context context, ArrayList<Gift> gifts) {
        this.mContext = context;
        this.mGifts = gifts;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fragment_about_me, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {

        int size = mGifts.size();
        final Gift gift = mGifts.get(size - (i + 1));

        /** Gift status setup*/
        String initiatorId = gift.getInitiator_id();
        String receiverId = gift.getReceiver_id();
        String dueDate = gift.getDue_date();
        String currentDate = getCurrentDate();
        Double price = gift.getPrice();
        Double progress = gift.getProgress();
        String reason = gift.getReason();

        if(dueDate.compareTo(currentDate) >= 0 && price == progress) {
            viewHolder.giftStatus.setText("Received");
        } else if(initiatorId.equals(receiverId)) {
            viewHolder.giftStatus.setText("My Wish Gift");
        } else {
            viewHolder.giftStatus.setText("Gifts From Friends");
        }

        /** Gift picture setup.
         * Get the ImageLoader through your singleton class. */
        mImageLoader = MySingleton.getInstance(viewHolder.giftTitle.getContext()).getImageLoader();
        viewHolder.giftPicture.setImageUrl(gift.getPicture_url(), mImageLoader);

        /** Gift title and gift price setup*/
        viewHolder.giftTitle.setText(gift.getName());
        viewHolder.currentPrice.setText("US $" + Double.toString(price));

        /** Progress bar setup. */
        DecimalFormat df = new DecimalFormat("#.#");
        viewHolder.progressBar.setProgress((int)(progress / price * 100));
        viewHolder.currentTotal.setText(df.format(progress / price * 100) + "%");

        /** Gift title and gift price setup*/
        viewHolder.reason.setText(reason);
        viewHolder.dueDate.setText(dueDate);

        viewHolder.mCardView.setOnClickListener(new View.OnClickListener() {
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
                intent.putExtra("progress", Double.toString(gift.getProgress()));
                intent.putExtra("reason", gift.getReason());
                intent.putExtra("receiver_id", gift.getReceiver_id());
                intent.putExtra("me_friend_tab", "me");

                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mGifts.size();
   }

    public String getCurrentDate() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat("MM/dd/yy");
        return mdformat.format(calendar.getTime());
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        CardView mCardView;
        TextView giftTitle;
        TextView currentPrice;
        NetworkImageView giftPicture;
        ProgressBar progressBar;
        TextView currentTotal;
        TextView reason;
        TextView dueDate;
        TextView giftStatus;

        ViewHolder(View view) {
            super(view);
            mCardView = (CardView) itemView.findViewById(R.id.cv);
            giftStatus = (TextView) itemView.findViewById(R.id.status);
            giftTitle = (TextView) itemView.findViewById(R.id.gift_title);
            currentPrice = (TextView) itemView.findViewById(R.id.current_price);
            giftPicture = (NetworkImageView) itemView.findViewById(R.id.gift_picture);
            progressBar = (ProgressBar) itemView.findViewById(R.id.progress);
            currentTotal = (TextView) itemView.findViewById(R.id.current_total);
            reason = (TextView) itemView.findViewById(R.id.reason);
            dueDate = (TextView) itemView.findViewById(R.id.due_date);
        }
    }
}
