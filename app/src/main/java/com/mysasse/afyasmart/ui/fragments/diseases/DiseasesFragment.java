package com.mysasse.afyasmart.ui.fragments.diseases;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mysasse.afyasmart.R;

public class DiseasesFragment extends Fragment {

    private RecyclerView diseasesRecyclerView;

    public DiseasesFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.diseases_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        DiseasesViewModel mViewModel = new ViewModelProvider(this).get(DiseasesViewModel.class);

        mViewModel.getDiseases().observe(getViewLifecycleOwner(), diseases -> {
            DiseaseAdapter adapter = new DiseaseAdapter(diseases);
            diseasesRecyclerView.setAdapter(adapter);
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Register and setup all the views
        FloatingActionButton addDiseaseFab = view.findViewById(R.id.add_disease_fab);
        addDiseaseFab.setOnClickListener(v -> Navigation.findNavController(v).navigate(R.id.addDiseaseFragment));

        diseasesRecyclerView = view.findViewById(R.id.diseases_recycler_view);
        diseasesRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        diseasesRecyclerView.setHasFixedSize(true);

    }
}
