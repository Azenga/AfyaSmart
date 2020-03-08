package com.mysasse.afyasmart.ui.fragments.profile;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.mysasse.afyasmart.R;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {


    private CircleImageView userAvatarCiv;

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Register Views
        userAvatarCiv = view.findViewById(R.id.user_avatar_civ);
        TextView userNameTv = view.findViewById(R.id.user_name_tv);
        TextView userPhoneTv = view.findViewById(R.id.user_phone_tv);
        TextView userRoleTv = view.findViewById(R.id.user_role_tv);
        TextView userBioTv = view.findViewById(R.id.user_bio_tv);

        Button editAccountBtn = view.findViewById(R.id.edit_account_btn);

        editAccountBtn.setOnClickListener(v -> Navigation.findNavController(v).navigate(R.id.editProfileFragment));

    }
}
