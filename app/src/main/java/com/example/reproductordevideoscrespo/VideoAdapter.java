package com.example.reproductordevideoscrespo;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class VideoAdapter extends ArrayAdapter<VideoItem> {

    private Context context;
    private int layoutResourceId;
    private List<VideoItem> videoItems, filteredVideoItems;
    private VideoFilter videoFilter;

    public VideoAdapter(Context context, int layoutResourceId, List<VideoItem> videoItems) {
        super(context, layoutResourceId, videoItems);
        this.context = context;
        this.layoutResourceId = layoutResourceId;
        this.videoItems = videoItems;
        this.filteredVideoItems = videoItems;
        videoFilter = new VideoFilter();
    }

    @Override
    public Filter getFilter() {
        return videoFilter;
    }

    @Override
    public int getCount() {
        return filteredVideoItems.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        VideoItemHolder holder;

        if (row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new VideoItemHolder();
            holder.titleTextView = row.findViewById(R.id.titleTextView);
            holder.thumbnailImageView = row.findViewById(R.id.thumbnailImageView);
            holder.locationTextView = row.findViewById(R.id.locationTextView);

            row.setTag(holder);
        } else {
            holder = (VideoItemHolder) row.getTag();
        }

        VideoItem videoItem = filteredVideoItems.get(position);
        holder.titleTextView.setText(videoItem.getTitle());
        holder.locationTextView.setText(videoItem.getPath());

        if (videoItem.getThumbnail() != null) {
            holder.thumbnailImageView.setImageBitmap(videoItem.getThumbnail());
        } else {
            holder.thumbnailImageView.setImageResource(R.drawable.baseline_video_file_24);
        }

        return row;
    }

    static class VideoItemHolder {
        TextView titleTextView, locationTextView;
        ImageView thumbnailImageView;
    }

    private class VideoFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();

            if (constraint == null || constraint.length() == 0) {
                results.values = videoItems;
                results.count = videoItems.size();
            } else {
                List<VideoItem> filteredVideos = new ArrayList<>();

                for (VideoItem video : videoItems) {
                    if (video.getTitle().toLowerCase().contains(constraint.toString().toLowerCase().trim())) {
                        filteredVideos.add(video);
                    }
                }

                results.values = filteredVideos;
                results.count = filteredVideos.size();
            }

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            filteredVideoItems = (List<VideoItem>) results.values;
            notifyDataSetChanged();
        }
    }

}
