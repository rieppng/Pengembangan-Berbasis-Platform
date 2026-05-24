package com.watchwatch.app.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.watchwatch.app.adapter.BookmarkAdapter;
import com.watchwatch.app.databinding.FragmentBookmarkBinding;
import com.watchwatch.app.utils.Constants;
import com.watchwatch.app.viewmodel.BookmarkViewModel;

public class BookmarkFragment extends Fragment {

    private FragmentBookmarkBinding binding;
    private BookmarkViewModel viewModel;
    private BookmarkAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentBookmarkBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupViewModel();
        setupRecyclerView();
        observeData();
    }

    private void setupViewModel() {
        viewModel = new ViewModelProvider(this).get(BookmarkViewModel.class);
    }

    private void setupRecyclerView() {
        adapter = new BookmarkAdapter();
        binding.rvBookmarks.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rvBookmarks.setAdapter(adapter);

        adapter.setOnBookmarkClickListener(bookmark -> {
            Intent intent = new Intent(requireContext(), DetailActivity.class);
            intent.putExtra(Constants.EXTRA_POST_ID, bookmark.id);
            intent.putExtra(Constants.EXTRA_POST_TITLE, bookmark.title);
            startActivity(intent);
        });

        adapter.setOnDeleteClickListener(bookmark ->
                viewModel.removeBookmark(bookmark.id));
    }

    private void observeData() {
        // LiveData dari Room
        viewModel.getAllBookmarks().observe(getViewLifecycleOwner(), bookmarks -> {
            adapter.setBookmarks(bookmarks);

            boolean isEmpty = bookmarks == null || bookmarks.isEmpty();
            binding.tvEmpty.setVisibility(isEmpty ? View.VISIBLE : View.GONE);
            binding.rvBookmarks.setVisibility(isEmpty ? View.GONE : View.VISIBLE);
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
