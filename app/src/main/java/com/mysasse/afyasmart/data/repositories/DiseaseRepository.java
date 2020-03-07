package com.mysasse.afyasmart.data.repositories;

import android.util.Log;

import com.google.firebase.firestore.FirebaseFirestore;
import com.mysasse.afyasmart.data.models.Disease;

import java.util.List;

public class DiseaseRepository {

    private DiseaseTasklListener diseaseTasklListener;

    private static final String TAG = "DiseaseRepository";

    private FirebaseFirestore mFirestore;

    public DiseaseRepository(DiseaseTasklListener diseaseTasklListener) {
        mFirestore = FirebaseFirestore.getInstance();

        this.diseaseTasklListener = diseaseTasklListener;
    }

    public void getAllDiseases() {

        mFirestore.collection("diseases")
                .addSnapshotListener((queryDocumentSnapshots, e) -> {
                    if (e != null) {
                        Log.e(TAG, "getAllDiseases: Failed:", e);
                        diseaseTasklListener.showError(e);
                        return;
                    }
                    assert queryDocumentSnapshots != null;
                    Log.d(TAG, "getAllDiseases: Fetched: count => " + queryDocumentSnapshots.size());
                    List<Disease> diseaseList = queryDocumentSnapshots.toObjects(Disease.class);
                    diseaseTasklListener.showDiseases(diseaseList);
                });
    }


    public interface DiseaseTasklListener {
        void showDiseases(List<Disease> diseases);

        void showError(Exception exception);
    }
}
