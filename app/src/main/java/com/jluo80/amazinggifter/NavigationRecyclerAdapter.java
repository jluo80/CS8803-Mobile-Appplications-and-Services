package com.jluo80.amazinggifter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.List;


public class NavigationRecyclerAdapter extends RecyclerView.Adapter<NavigationRecyclerAdapter.ViewHolder> {

    private List<Person> mPersons;

    NavigationRecyclerAdapter(List<Person> persons) {
        mPersons = persons;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_view, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        Person person = mPersons.get(i);
        viewHolder.personName.setText(person.name);
        viewHolder.personAge.setText(person.age);
        viewHolder.personPhoto.setImageResource(person.photoId);
        viewHolder.personName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = view.getContext();
                context.startActivity(new Intent(context, AddGiftsActivity.class));
            }
        });
    }

    @Override
    public int getItemCount() {
        return mPersons.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

//        private final TextView mTextView;
        CardView mCardView;
        TextView personName;
        TextView personAge;
        ImageView personPhoto;

        ViewHolder(View view) {
            super(view);
            mCardView = (CardView)itemView.findViewById(R.id.cv);
            personName = (TextView)itemView.findViewById(R.id.person_name);
            personAge = (TextView)itemView.findViewById(R.id.person_age);
            personPhoto = (ImageView)itemView.findViewById(R.id.person_photo);
        }
    }

}
