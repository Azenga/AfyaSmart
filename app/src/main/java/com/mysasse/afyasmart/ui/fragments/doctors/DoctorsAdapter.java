package com.mysasse.afyasmart.ui.fragments.doctors;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mysasse.afyasmart.R;
import com.mysasse.afyasmart.data.models.Profile;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class DoctorsAdapter extends RecyclerView.Adapter<DoctorsAdapter.DoctorViewHolder> {

    private List<Profile> profiles;

    public DoctorsAdapter(List<Profile> profiles) {

        this.profiles = profiles;
    }

    @NonNull
    @Override
    public DoctorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new DoctorViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.single_doctor_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull DoctorViewHolder holder, int position) {
        Profile profile = profiles.get(position);

        /*
        holder.doctorNameTv.setText(profile.getName());
        holder.doctorExpertiseTv.setText(profile.getExpertise());
        holder.doctorRoleTv.setText(profile.getRole());


        Glide.with(holder.doctorAvatarCiv)
                .load(profile.getAvatar())
                .centerCrop()
                .placeholder(R.drawable.ic_account_circle)
                .into(holder.doctorAvatarCiv);

         */
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    static class DoctorViewHolder extends RecyclerView.ViewHolder {

        CircleImageView doctorAvatarCiv;
        TextView doctorNameTv;
        TextView doctorRoleTv;
        TextView doctorExpertiseTv;

        DoctorViewHolder(@NonNull View itemView) {
            super(itemView);

            doctorAvatarCiv = itemView.findViewById(R.id.doctor_avatar_civ);
            doctorNameTv = itemView.findViewById(R.id.doctor_name_tv);
            doctorRoleTv = itemView.findViewById(R.id.doctor_role_tv);
            doctorExpertiseTv = itemView.findViewById(R.id.doctor_expertise_tv);
        }
    }
}
