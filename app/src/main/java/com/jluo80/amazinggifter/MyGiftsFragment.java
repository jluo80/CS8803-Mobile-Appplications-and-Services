package com.jluo80.amazinggifter;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.BottomBarTab;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */

public class MyGiftsFragment extends Fragment {

    private List<Person> persons;

    public MyGiftsFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        persons = new ArrayList<>();
        persons.add(new Person("Edward Luo", "25 years old", R.drawable.userimg));
        persons.add(new Person("Emma Wilson", "23 years old", R.drawable.userimg));
        persons.add(new Person("Lavery Maiss", "25 years old", R.drawable.userimg));
        persons.add(new Person("Lillie Watts", "35 years old", R.drawable.userimg));
        persons.add(new Person("Tom Hanks", "25 years old", R.drawable.userimg));
        persons.add(new Person("Adam Jackson", "35 years old", R.drawable.userimg));
        persons.add(new Person("Stephen Curry", "35 years old", R.drawable.userimg));

        View rootView =  inflater.inflate(R.layout.fragment_list_view, container, false);
        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(new NavigationRecyclerAdapter(persons));

        return rootView;
    }
}