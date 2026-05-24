package com.watchwatch.app.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.watchwatch.app.adapter.LatestPostAdapter;
import com.watchwatch.app.databinding.FragmentSearchBinding;
import com.watchwatch.app.model.Post;
import com.watchwatch.app.utils.Constants;
import com.watchwatch.app.viewmodel.SearchViewModel;

public class SearchFragment extends Fragment {


    private static final long DEBOUNCE_DELAY = 500;

    private FragmentSearchBinding binding;
    private SearchViewModel viewModel;
    private LatestPostAdapter adapter;
    private final Handler debounceHandler = new Handler(Looper.getMainLooper());

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentSearchBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupViewModel();
        setupRecyclerView();
        setupSearch();
        observeData();
    }

    private void setupViewModel() {
        viewModel = new ViewModelProvider(this).get(SearchViewModel.class);
    }

    private void setupRecyclerView() {
        adapter = new LatestPostAdapter();
        binding.rvResults.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rvResults.setAdapter(adapter);

        adapter.setOnPostClickListener(this::navigateToDetail);
    }

    private void setupSearch() {
        binding.etSearch.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void afterTextChanged(Editable s) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                debounceHandler.removeCallbacksAndMessages(null);

                String query = s.toString().trim();

                if (query.isEmpty()) {
                    viewModel.clearResults();
                    return;
                }

                if (query.length() < 2) {
                    return;
                }

                debounceHandler.postDelayed(() -> viewModel.search(query), DEBOUNCE_DELAY);
            }
        });
    }

    private void navigateToDetail(Post post) {
        Intent intent = new Intent(requireContext(), DetailActivity.class);
        intent.putExtra(Constants.EXTRA_POST_ID, post.getId());
        intent.putExtra(Constants.EXTRA_POST_TITLE, post.getTitleRendered());
        startActivity(intent);
    }

    private void observeData() {
        viewModel.getIsLoading().observe(getViewLifecycleOwner(), isLoading ->
                binding.progressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE));

        viewModel.getResults().observe(getViewLifecycleOwner(),
                posts -> adapter.setPosts(posts));

        viewModel.getIsEmpty().observe(getViewLifecycleOwner(), isEmpty ->
                binding.tvEmpty.setVisibility(isEmpty ? View.VISIBLE : View.GONE));

        viewModel.getShowHint().observe(getViewLifecycleOwner(), showHint ->
                binding.tvHint.setVisibility(showHint ? View.VISIBLE : View.GONE));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        debounceHandler.removeCallbacksAndMessages(null);
        binding = null;
    }
}
