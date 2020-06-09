package com.mysasse.afyasmart.ui.fragments.doctors;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.mysasse.afyasmart.data.models.Profile;
import com.mysasse.afyasmart.data.repositories.DoctorsRepository;

import java.util.List;

public class DoctorsViewModel extends ViewModel implements DoctorsRepository.DoctorsTaskCompleteListener {

    private static final String TAG = "DoctorsViewModel";
    private DoctorsRepository repository;
    private MutableLiveData<List<Profile>> _profiles;

    public DoctorsViewModel() {
        _profiles = new MutableLiveData<>();
        repository = new DoctorsRepository(this);
    }

    public LiveData<List<Profile>> getDoctors() {
        repository.getDoctorsFromFirebase();

        return _profiles;
    }

    @Override
    public void onComplete(List<Profile> profiles) {
        Log.d(TAG, "onComplete: profiles count: " + profiles.size());

        _profiles.setValue(profiles);
    }

    @Override
    public void onError(Exception error) {
        Log.e(TAG, "onError: getting doctors", error);
    }
}
