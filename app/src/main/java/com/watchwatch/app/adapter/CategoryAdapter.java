package com.watchwatch.app.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.watchwatch.app.databinding.ItemCategoryListBinding;
import com.watchwatch.app.model.Category;

import java.util.ArrayList;
import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    public interface OnCategoryClickListener {
        void onCategoryClick(Category category);
    }

    // Dua warna selang-seling: putih dan abu muda
    private static final String COLOR_WHITE    = "#FFFFFF";
    private static final String COLOR_GRAY     = "#F5F5F5";

    private List<Category> categories = new ArrayList<>();
    private OnCategoryClickListener listener;

    public void setCategories(List<Category> categories) {
        this.categories = categories;
        notifyDataSetChanged();
    }

    public void setOnCategoryClickListener(OnCategoryClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemCategoryListBinding binding = ItemCategoryListBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(categories.get(position), position, listener);
    }

    @Override
    public int getItemCount() { return categories.size(); }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private final ItemCategoryListBinding binding;

        ViewHolder(ItemCategoryListBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(Category category, int position, OnCategoryClickListener listener) {
            binding.tvName.setText(category.getName());
            binding.tvCount.setText(category.getCount() + " artikel");

            // Selang-seling: genap = putih, ganjil = abu muda
            String bgColor = (position % 2 == 0) ? COLOR_WHITE : COLOR_GRAY;
            binding.getRoot().setBackgroundColor(Color.parseColor(bgColor));

            binding.getRoot().setOnClickListener(v -> {
                if (listener != null) listener.onCategoryClick(category);
            });
        }
    }
}
