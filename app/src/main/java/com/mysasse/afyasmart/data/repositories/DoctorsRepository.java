package com.mysasse.afyasmart.data.repositories;

import android.util.Log;

import com.google.firebase.firestore.FirebaseFirestore;
import com.mysasse.afyasmart.data.models.Profile;

import java.util.List;

public class DoctorsRepository {

    private static final String TAG = "DoctorsRepository";

    private FirebaseFirestore mDatabase;
    private DoctorsTaskCompleteListener listener;

    public DoctorsRepository(DoctorsTaskCompleteListener listener) {
        this.listener = listener;
        mDatabase = FirebaseFirestore.getInstance();
    }

    public void getDoctorsFromFirebase() {
        mDatabase.collection("profiles")
                .whereEqualTo("role", "Doctor")
                .addSnapshotListener((queryDocumentSnapshots, e) -> {
                    if (e != null) {
                        Log.e(TAG, "getDoctorsFromFirebase: exception", e);
                        listener.onError(e);
                        return;
                    }

                    assert queryDocumentSnapshots != null;
                    listener.onComplete(queryDocumentSnapshots.toObjects(Profile.class));

                });
    }

    public interface DoctorsTaskCompleteListener {
        void onComplete(List<Profile> profiles);

        void onError(Exception error);
    }
}
