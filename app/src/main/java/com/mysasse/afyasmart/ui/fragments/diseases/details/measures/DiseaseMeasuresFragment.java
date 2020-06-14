package com.mysasse.afyasmart.ui.fragments.diseases.details.measures;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mysasse.afyasmart.R;

public class DiseaseMeasuresFragment extends Fragment {

    private DiseaseMeasuresViewModel mViewModel;

    public static DiseaseMeasuresFragment newInstance() {
        return new DiseaseMeasuresFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.disease_measures_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(DiseaseMeasuresViewModel.class);
        // TODO: Use the ViewModel
    }

}