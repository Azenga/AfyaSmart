package com.mysasse.afyasmart.ui.fragments.diseases.details.symptoms;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mysasse.afyasmart.R;

public class AddDiseaseSymptomFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_disease_symptom, container, false);
    }
}