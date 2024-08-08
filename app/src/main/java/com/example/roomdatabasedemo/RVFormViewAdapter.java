package com.example.roomdatabasedemo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class RVFormViewAdapter extends ListAdapter<DetailsModal, RVFormViewAdapter.ViewHolder> {

    private OnItemClickListener listener;
    RVFormViewAdapter(){
        super(DIFF_CALLBACK);
    }
    private static final DiffUtil.ItemCallback<DetailsModal> DIFF_CALLBACK = new DiffUtil.ItemCallback<DetailsModal>() {
        @Override
        public boolean areItemsTheSame(DetailsModal oldItem, DetailsModal newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(DetailsModal oldItem, DetailsModal newItem) {
            // below line is to check the course name, description and course duration.
            return oldItem.getName().equals(newItem.getName()) &&
                    oldItem.getEmail().equals(newItem.getEmail()) &&
                    oldItem.getPhoneNumber().equals(newItem.getPhoneNumber());
        }
    };


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row, parent, false);
        return new ViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DetailsModal modal = getFormAt(position);
        holder.name.setText(modal.getName());
        holder.email.setText(modal.getEmail());
        holder.phonenumber.setText(modal.getPhoneNumber());


    }
    public DetailsModal getFormAt(int position) {
        return getItem(position);
    }



    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView name, email, phonenumber;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.idTVFormName);
            email = itemView.findViewById(R.id.idTVFormEmail);
            phonenumber = itemView.findViewById(R.id.idTVCFormNumber);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(getItem(position));
                    }

                }
            });
        }
    }
    public interface OnItemClickListener extends AdapterView.OnItemClickListener {
        void onItemClick(DetailsModal modal);
    }
    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }
}
