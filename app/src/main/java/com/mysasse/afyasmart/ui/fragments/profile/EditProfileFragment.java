package com.mysasse.afyasmart.ui.fragments.profile;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.mysasse.afyasmart.R;
import com.mysasse.afyasmart.data.models.Profile;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class EditProfileFragment extends Fragment {
    private static final String TAG = "EditProfileFragment";

    //Fire-base Instances
    private FirebaseAuth mAuth;
    private FirebaseStorage mFiles;
    private FirebaseFirestore mDatabase;

    private static final int SELECT_PROFILE_IMAGE_RC = 22;
    private Uri userAvatarUri;
    private CircleImageView userAvatarCiv;
    private TextInputEditText nameTxt;
    private TextInputEditText phoneTxt;
    private ProgressBar updateProfileProgressBar;
    private Profile mProfile = null;
    private TextInputEditText bioTxt;

    public EditProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_edit_profile, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        assert getArguments() != null;
        mProfile = EditProfileFragmentArgs.fromBundle(getArguments()).getProfile();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Instantiate fire-base variables
        mAuth = FirebaseAuth.getInstance();
        mFiles = FirebaseStorage.getInstance();
        mDatabase = FirebaseFirestore.getInstance();

        //Register necessary views
        userAvatarCiv = view.findViewById(R.id.user_avatar_civ);

        updateProfileProgressBar = view.findViewById(R.id.update_profile_progress_bar);

        nameTxt = view.findViewById(R.id.name_txt);
        phoneTxt = view.findViewById(R.id.phone_txt);
        bioTxt = view.findViewById(R.id.bio_txt);

        //Update with existing details
        updateUI();

        Button updateProfileButton = view.findViewById(R.id.update_profile_button);

        userAvatarCiv.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");

            startActivityForResult(intent, SELECT_PROFILE_IMAGE_RC);
        });

        updateProfileButton.setOnClickListener(v -> {

            String name = String.valueOf(nameTxt.getText());
            String phone = String.valueOf(phoneTxt.getText());
            String bio = String.valueOf(bioTxt.getText());

            if (!hasValidData(name, phone)) return;

            //Check whether there is an image in the imageUri and upload it or else just upload the rest of the details
            Profile profile = new Profile(name, phone, "Patient", bio);

            if (userAvatarUri == null) {
                Toast.makeText(getActivity(), "Select a profile picture before updating", Toast.LENGTH_SHORT).show();
                return;
            }

            assert mAuth.getCurrentUser() != null;

            String currentUid = mAuth.getCurrentUser().getUid();
            //Upload the image

            updateProfileProgressBar.setVisibility(View.VISIBLE);
            StorageReference userAvatarRef = mFiles.getReference("avatar/" + currentUid);
            userAvatarRef.putFile(userAvatarUri).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    //Get the download url for the image from the storage reference which is a network task
                    userAvatarRef.getDownloadUrl().addOnCompleteListener(downLoadUrlTask -> {
                        if (task.isSuccessful()) {
                            assert downLoadUrlTask.getResult() != null;
                            profile.setAvatar(downLoadUrlTask.getResult().toString());
                            updateUserProfile(profile);
                        } else {
                            Log.e(TAG, "onViewCreated: Error While downloading the image url: ", downLoadUrlTask.getException());
                            Toast.makeText(getActivity(), "Error while downloading the image url contact admin", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    Log.e(TAG, "onViewCreated: Error While uploading image: ", task.getException());
                    Toast.makeText(getActivity(), "Error while uploading the image try again later", Toast.LENGTH_SHORT).show();
                }
            });

        });
    }

    private void updateUI() {

        nameTxt.setText(mProfile.getName());
        if (!mProfile.getPhone().isEmpty())
            phoneTxt.setText(mProfile.getPhone());

        if (!mProfile.getBio().isEmpty())
            bioTxt.setText(mProfile.getBio());

        Glide.with(this)
                .load(mProfile.getAvatar())
                .centerCrop()
                .placeholder(R.drawable.ic_account_circle)
                .into(userAvatarCiv);

    }

    private void updateUserProfile(Profile profile) {

        assert mAuth.getCurrentUser() != null;

        String currentUid = mAuth.getCurrentUser().getUid();

        mDatabase.collection("profiles").document(currentUid).set(profile).addOnCompleteListener(task -> {

            updateProfileProgressBar.setVisibility(View.GONE);
            if (task.isSuccessful()) {
                assert getActivity() != null;
                Toast.makeText(getActivity(), "User profile successfully updated", Toast.LENGTH_SHORT).show();
                getActivity().onBackPressed();
            } else {
                Log.e(TAG, "updateUserProfile: Error While uploading the profile details to fire-store: ", task.getException());
                Toast.makeText(getActivity(), "Error while uploading all the profile details", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean hasValidData(String name, String phone) {
        if (TextUtils.isEmpty(name)) {
            nameTxt.setError("Name is required");
            nameTxt.requestFocus();
            return false;
        }

        if (name.length() < 3) {
            nameTxt.setError("At least 3 chars required for name");
            nameTxt.requestFocus();
            return false;
        }

        if (!Patterns.PHONE.matcher(phone).matches()) {
            phoneTxt.setError("A valid phone number required please");
            phoneTxt.requestFocus();
            return false;
        }

        return true;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        assert getActivity() != null;
        if (resultCode == RESULT_OK) {

            if (requestCode == SELECT_PROFILE_IMAGE_RC) {
                assert data != null;
                userAvatarUri = data.getData();
                userAvatarCiv.setImageURI(userAvatarUri);
            } else {
                super.onActivityResult(requestCode, resultCode, data);
            }
        } else {
            Log.d(TAG, "onActivityResult: failed => result is not OK");
            Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
        }
    }
}
