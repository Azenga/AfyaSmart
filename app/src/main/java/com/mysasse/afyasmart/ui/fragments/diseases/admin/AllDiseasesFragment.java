package com.mysasse.afyasmart.ui.fragments.diseases.admin;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mysasse.afyasmart.R;

public class AllDiseasesFragment extends Fragment {

    private RecyclerView allDiseasesRecyclerView;

    public AllDiseasesFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.all_diseases_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        //Register the views
        allDiseasesRecyclerView = view.findViewById(R.id.all_diseases_recycler_view);
        allDiseasesRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        allDiseasesRecyclerView.setHasFixedSize(true);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        AllDiseasesViewModel mViewModel = new ViewModelProvider(this).get(AllDiseasesViewModel.class);

        mViewModel.getAllDiseases().observe(getViewLifecycleOwner(), diseases -> {
            AllDiseasesAdapter adapter = new AllDiseasesAdapter(diseases);
            allDiseasesRecyclerView.setAdapter(adapter);
        });
    }

}
