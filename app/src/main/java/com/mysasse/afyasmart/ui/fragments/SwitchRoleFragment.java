package com.mysasse.afyasmart.ui.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.FirebaseFirestore;
import com.mysasse.afyasmart.R;
import com.mysasse.afyasmart.utils.UIHelpers;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class SwitchRoleFragment extends Fragment {
    private static final String TAG = "SwitchRoleFragment";

    private String expertise;
    private String userUid;

    private FirebaseFirestore mDatabase;
    private ProgressBar switchRoleProgressBar;

    public SwitchRoleFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        assert getArguments() != null;

        userUid = SwitchRoleFragmentArgs.fromBundle(getArguments()).getUserId();
        expertise = SwitchRoleFragmentArgs.fromBundle(getArguments()).getExpertise();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_switch_role, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        //Init firebase instances
        mDatabase = FirebaseFirestore.getInstance();

        //Register the necessary views
        AutoCompleteTextView rolesAtv = view.findViewById(R.id.role_atv);
        //Set autocomplete for roles
        assert getContext() != null;
        String[] roles = getResources().getStringArray(R.array.roles);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), R.layout.simple_dropdown_menu_popup_item, roles);
        rolesAtv.setAdapter(adapter);

        TextInputEditText expertiseTxt = view.findViewById(R.id.expertise_area_txt);
        if (expertise != null) expertiseTxt.setText(expertise);

        Button changeRoleButton = view.findViewById(R.id.change_role_btn);
        switchRoleProgressBar = view.findViewById(R.id.switch_role_progress_bar);

        changeRoleButton.setOnClickListener(v -> {

            //Get the inputs
            String role = String.valueOf(rolesAtv.getText());
            String expertise = String.valueOf(expertiseTxt.getText());

            Map<String, Object> profileUpdateMap = new HashMap<>();
            profileUpdateMap.put("role", role);
            profileUpdateMap.put("expertise", expertise);

            updateUserProfile(profileUpdateMap);

        });

    }

    private void updateUserProfile(Map<String, Object> profileUpdateMap) {
        switchRoleProgressBar.setVisibility(View.VISIBLE);
        mDatabase.collection("profiles").document(userUid)
                .update(profileUpdateMap)
                .addOnCompleteListener(task -> {
                    switchRoleProgressBar.setVisibility(View.GONE);
                    if (task.isSuccessful()) {
                        Toast.makeText(getContext(), "Role successfully switched", Toast.LENGTH_SHORT).show();
                        assert getActivity() != null;
                        getActivity().onBackPressed();
                        return;
                    }
                    assert task.getException() != null;

                    Log.e(TAG, "updateUserProfile: failed", task.getException());
                    UIHelpers.toast("Operation failed: " + task.getException().getLocalizedMessage());
                });
    }
}
