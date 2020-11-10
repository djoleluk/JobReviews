package com.pistolshrimpstudio.jobreviews.custom;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.pistolshrimpstudio.jobreviews.R;
import java.util.List;

public class JobListAdapter extends RecyclerView.Adapter<JobListAdapter.JobListViewHolder> {
    private List<JobListItem> mJobListItems;
    private JobListAdapter.OnItemTouchListener mOnItemTouchListener;

    public void setOnItemTouchListener(OnItemTouchListener onItemTouchListener) {
        mOnItemTouchListener = onItemTouchListener;
    }

    @NonNull
    @Override
    public JobListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.job_list_item, parent, false);
        JobListViewHolder jobListViewHolder = new JobListViewHolder(view, mOnItemTouchListener);
        return jobListViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull JobListViewHolder holder, int position) {
        JobListItem jobListItem = mJobListItems.get(position);
        holder.jobTitle.setText(jobListItem.getTitle());
        if (jobListItem.getImageBitmap() != null) {
            holder.logoImage.setImageBitmap(jobListItem.getImageBitmap());
        } else {
            holder.logoImage.setImageResource(R.drawable.it);
        }
    }

    @Override
    public int getItemCount() {
        return mJobListItems.size();
    }

    public JobListAdapter(List<JobListItem> jobListItems) {
        mJobListItems = jobListItems;
    }

    public interface OnItemTouchListener {
        default void onItemTouch(View view, MotionEvent event, int position){}
    }

    static class JobListViewHolder extends RecyclerView.ViewHolder {
        private ImageView logoImage;
        private TextView jobTitle;

        @SuppressLint("ClickableViewAccessibility")
        public JobListViewHolder(View itemView, OnItemTouchListener onItemTouchListener) {
            super(itemView);

            logoImage = itemView.findViewById(R.id.job_logo_image);
            jobTitle = itemView.findViewById(R.id.job_item_title);

            itemView.setOnTouchListener((v, event) -> {
                if (onItemTouchListener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        onItemTouchListener.onItemTouch(v, event, position);
                    }
                }
                return true;
            });
        }
    }
}

