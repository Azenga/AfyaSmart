package com.mysasse.afyasmart.ui.fragments.diseases;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.mysasse.afyasmart.data.models.Disease;
import com.mysasse.afyasmart.data.repositories.DiseaseRepository;

import java.util.List;

public class DiseasesViewModel extends ViewModel implements DiseaseRepository.DiseaseTasklListener {
    private static final String TAG = "DiseasesViewModel";
    private MutableLiveData<List<Disease>> _diseases;
    private DiseaseRepository diseaseRepository;

    public DiseasesViewModel() {
        _diseases = new MutableLiveData<>();
        diseaseRepository = new DiseaseRepository(this);
    }

    public LiveData<List<Disease>> getDiseases() {
        diseaseRepository.getAllDiseases();

        return _diseases;
    }

    @Override
    public void showDiseases(List<Disease> diseases) {
        Log.d(TAG, "showDiseases: diseases count: " + diseases.size());

        _diseases.setValue(diseases);
    }

    @Override
    public void showError(Exception exception) {
        Log.e(TAG, "showError: fetching diseases", exception);
    }
}
