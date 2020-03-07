package com.mysasse.afyasmart.ui.fragments.doctors;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mysasse.afyasmart.R;

public class DoctorsFragment extends Fragment {

    private DoctorsViewModel mViewModel;

    public static DoctorsFragment newInstance() {
        return new DoctorsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.doctors_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(DoctorsViewModel.class);
        // TODO: Use the ViewModel
    }

}
