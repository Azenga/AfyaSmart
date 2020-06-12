package com.mysasse.afyasmart.ui.fragments.diseases;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mysasse.afyasmart.R;
import com.mysasse.afyasmart.data.models.Disease;

import java.util.List;

public class DiseaseAdapter extends RecyclerView.Adapter<DiseaseAdapter.DiseaseViewHolder> {

    private List<Disease> diseaseList;

    public DiseaseAdapter(List<Disease> diseaseList) {
        this.diseaseList = diseaseList;
    }

    @NonNull
    @Override
    public DiseaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new DiseaseViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.single_disease, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull DiseaseViewHolder holder, int position) {
        holder.nameTv.setText(trimToSizeIfLong(diseaseList.get(position).getName(), 15));
        holder.descriptionTv.setText(trimToSizeIfLong(diseaseList.get(position).getDescription(), 72));
    }

    private String trimToSizeIfLong(String content, int size) {
        return (content.length() > size) ? content.substring(0, size) + "..." : content;
    }

    @Override
    public int getItemCount() {
        return diseaseList.size();
    }

    static class DiseaseViewHolder extends RecyclerView.ViewHolder {
        TextView nameTv, descriptionTv;

        DiseaseViewHolder(@NonNull View itemView) {
            super(itemView);

            nameTv = itemView.findViewById(R.id.name_tv);
            descriptionTv = itemView.findViewById(R.id.description_tv);
        }
    }
}
