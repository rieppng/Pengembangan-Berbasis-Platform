package com.watchwatch.app.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.watchwatch.app.database.BookmarkEntity;
import com.watchwatch.app.databinding.ItemBookmarkBinding;
import com.watchwatch.app.utils.DateUtils;

import java.util.ArrayList;
import java.util.List;

public class BookmarkAdapter extends RecyclerView.Adapter<BookmarkAdapter.ViewHolder> {

    public interface OnBookmarkClickListener {
        void onItemClick(BookmarkEntity bookmark);
    }

    public interface OnDeleteClickListener {
        void onDeleteClick(BookmarkEntity bookmark);
    }

    private List<BookmarkEntity> bookmarks = new ArrayList<>();
    private OnBookmarkClickListener clickListener;
    private OnDeleteClickListener deleteListener;

    public void setBookmarks(List<BookmarkEntity> bookmarks) {
        this.bookmarks = bookmarks;
        notifyDataSetChanged();
    }

    public void setOnBookmarkClickListener(OnBookmarkClickListener listener) {
        this.clickListener = listener;
    }

    public void setOnDeleteClickListener(OnDeleteClickListener listener) {
        this.deleteListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemBookmarkBinding binding = ItemBookmarkBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(bookmarks.get(position), clickListener, deleteListener);
    }

    @Override
    public int getItemCount() { return bookmarks.size(); }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private final ItemBookmarkBinding binding;

        ViewHolder(ItemBookmarkBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(BookmarkEntity bookmark,
                  OnBookmarkClickListener clickListener,
                  OnDeleteClickListener deleteListener) {

            binding.tvTitle.setText(bookmark.title);
            binding.tvCategory.setText(bookmark.categoryName);
            binding.tvDate.setText(DateUtils.formatDate(bookmark.date));

            Glide.with(binding.getRoot().getContext())
                    .load(bookmark.imageUrl)
                    .centerCrop()
                    .into(binding.ivThumbnail);

            // Buka DetailActivity saat item diklik
            binding.getRoot().setOnClickListener(v -> {
                if (clickListener != null) clickListener.onItemClick(bookmark);
            });

            // Hapus bookmark saat ikon ❌ diklik
            binding.btnDelete.setOnClickListener(v -> {
                if (deleteListener != null) deleteListener.onDeleteClick(bookmark);
            });
        }
    }
}
