package com.watchwatch.app.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.watchwatch.app.databinding.ItemPostLatestBinding;
import com.watchwatch.app.model.Post;
import com.watchwatch.app.utils.DateUtils;

import java.util.ArrayList;
import java.util.List;

/** Adapter untuk RecyclerView vertikal section Artikel Terbaru di HomeFragment */
public class LatestPostAdapter extends RecyclerView.Adapter<LatestPostAdapter.ViewHolder> {

    public interface OnPostClickListener {
        void onPostClick(Post post);
    }

    private List<Post> posts = new ArrayList<>();
    private OnPostClickListener listener;

    public void setPosts(List<Post> posts) {
        this.posts = posts;
        notifyDataSetChanged();
    }

    public void setOnPostClickListener(OnPostClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemPostLatestBinding binding = ItemPostLatestBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(posts.get(position), listener);
    }

    @Override
    public int getItemCount() { return posts.size(); }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private final ItemPostLatestBinding binding;

        ViewHolder(ItemPostLatestBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(Post post, OnPostClickListener listener) {
            binding.tvTitle.setText(post.getTitleRendered());
            binding.tvCategory.setText(post.getCategoryName());
            binding.tvDate.setText(DateUtils.formatDate(post.getDate()));

            Glide.with(binding.getRoot().getContext())
                    .load(post.getFeaturedImageUrl())
                    .centerCrop()
                    .into(binding.ivThumbnail);

            binding.getRoot().setOnClickListener(v -> {
                if (listener != null) listener.onPostClick(post);
            });
        }
    }
}
