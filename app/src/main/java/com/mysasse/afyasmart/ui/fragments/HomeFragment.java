package com.mysasse.afyasmart.ui.fragments;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.card.MaterialCardView;
import com.mysasse.afyasmart.R;

public class HomeFragment extends Fragment {


    public HomeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Register the cards
        MaterialCardView doctorsCardView = view.findViewById(R.id.doctors_card_view);
        MaterialCardView diseasesCardView = view.findViewById(R.id.diseases_card_view);
        MaterialCardView messagesCardView = view.findViewById(R.id.messages_card_view);
        MaterialCardView blogCardView = view.findViewById(R.id.blog_card_view);

        //Register click listeners for ach card view
        doctorsCardView.setOnClickListener(v -> Navigation.findNavController(v).navigate(R.id.doctorsFragment));
        diseasesCardView.setOnClickListener(v -> Navigation.findNavController(v).navigate(R.id.diseasesFragment));
        messagesCardView.setOnClickListener(v -> Navigation.findNavController(v).navigate(R.id.messagesFragment));
        blogCardView.setOnClickListener(v -> Navigation.findNavController(v).navigate(R.id.postsFragment));
    }
}
