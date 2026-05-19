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
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.watchwatch.app.adapter.LatestPostAdapter;
import com.watchwatch.app.databinding.FragmentListReviewBinding;
import com.watchwatch.app.model.Post;
import com.watchwatch.app.utils.Constants;
import com.watchwatch.app.viewmodel.ListReviewViewModel;

public class ListReviewFragment extends Fragment {

    public static final String ARG_CATEGORY_ID   = "category_id";
    public static final String ARG_CATEGORY_NAME = "category_name";

    private FragmentListReviewBinding binding;
    private ListReviewViewModel viewModel;
    private LatestPostAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentListReviewBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Ambil data dari Bundle yang dikirim HomeFragment / CategoryFragment
        int categoryId = 0;
        String categoryName = "Review";
        if (getArguments() != null) {
            categoryId   = getArguments().getInt(ARG_CATEGORY_ID);
            categoryName = getArguments().getString(ARG_CATEGORY_NAME, "Review");
        }

        setupToolbar(categoryName);
        setupViewModel(categoryId);
        setupRecyclerView();
        setupSwipeRefresh();
        observeData();
    }

    private void setupToolbar(String categoryName) {
        binding.toolbar.setTitle(categoryName);
        // Tombol back: kembali ke halaman sebelumnya via NavController
        binding.toolbar.setNavigationOnClickListener(v ->
                Navigation.findNavController(requireView()).navigateUp());
    }

    private void setupViewModel(int categoryId) {
        viewModel = new ViewModelProvider(this).get(ListReviewViewModel.class);
        viewModel.init(categoryId);
    }

    private void setupRecyclerView() {
        adapter = new LatestPostAdapter();
        binding.rvPosts.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rvPosts.setAdapter(adapter);

        adapter.setOnPostClickListener(this::navigateToDetail);
    }

    private void setupSwipeRefresh() {
        binding.swipeRefresh.setOnRefreshListener(() -> viewModel.refresh());
    }

    private void navigateToDetail(Post post) {
        Intent intent = new Intent(requireContext(), DetailActivity.class);
        intent.putExtra(Constants.EXTRA_POST_ID, post.getId());
        intent.putExtra(Constants.EXTRA_POST_TITLE, post.getTitleRendered());
        startActivity(intent);
    }

    private void observeData() {
        viewModel.getIsLoading().observe(getViewLifecycleOwner(), isLoading -> {
            binding.progressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
            if (!isLoading) binding.swipeRefresh.setRefreshing(false);
        });

        viewModel.getPosts().observe(getViewLifecycleOwner(), posts -> {
            adapter.setPosts(posts);
            // Tampilkan pesan jika tidak ada artikel
            binding.tvEmpty.setVisibility(posts.isEmpty() ? View.VISIBLE : View.GONE);
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
