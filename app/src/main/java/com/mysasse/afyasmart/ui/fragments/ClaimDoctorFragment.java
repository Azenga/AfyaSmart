package com.mysasse.afyasmart.ui.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.mysasse.afyasmart.R;
import com.mysasse.afyasmart.data.models.Notification;
import com.mysasse.afyasmart.data.models.Profile;

/**
 * A simple {@link Fragment} subclass.
 */
public class ClaimDoctorFragment extends Fragment {
    private static final String TAG = "ClaimDoctorFragment";

    private FirebaseAuth mAuth;
    private FirebaseFirestore mDatabase;

    public ClaimDoctorFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_claim_doctor, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        //Init fire-base instances
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseFirestore.getInstance();

        //Register necessary view
        TextInputEditText expertiseAreaTxt = view.findViewById(R.id.expertise_area_txt);

        Button sendRequestBtn = view.findViewById(R.id.send_request_btn);

        sendRequestBtn.setOnClickListener(v -> {

            String expertise = String.valueOf(expertiseAreaTxt.getText());

            if (TextUtils.isEmpty(expertise)) {
                expertiseAreaTxt.setError("You must have something to offer");
                expertiseAreaTxt.requestFocus();
                return;
            }

            //Get current user details
            assert mAuth.getCurrentUser() != null;
            mDatabase.collection("profiles").document(mAuth.getCurrentUser().getUid())
                    .addSnapshotListener((documentSnapshot, e) -> {

                        if (e != null) {
                            Log.e(TAG, "onViewCreated: getting current user: ", e);
                            return;
                        }
                        assert documentSnapshot != null;

                        Profile profile = documentSnapshot.toObject(Profile.class);

                        assert profile != null;
                        Notification notification = new Notification(mAuth.getCurrentUser().getUid(), profile.getName(), "I am a doctor, I need elevations", "role_change");

                        notification.setExpertise(expertise);

                        addNotification(notification);
                    });


            Log.d(TAG, "onViewCreated: expertise " + expertise);
        });
    }

    private void addNotification(Notification notification) {
        mDatabase.collection("notifications")
                .add(notification)
                .addOnCompleteListener(task -> {

                    if (task.isSuccessful()) {
                        Toast.makeText(getContext(), "Request sent successfully", Toast.LENGTH_SHORT).show();
                        assert getActivity() != null;

                        getActivity().onBackPressed();

                    } else {
                        Log.e(TAG, "addNotification: failed:", task.getException());
                        Toast.makeText(getContext(), "Request sendng failed try again later", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
