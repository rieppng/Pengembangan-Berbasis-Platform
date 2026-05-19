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

import com.bumptech.glide.Glide;
import com.watchwatch.app.R;
import com.watchwatch.app.adapter.LatestPostAdapter;
import com.watchwatch.app.adapter.TrendingAdapter;
import com.watchwatch.app.databinding.FragmentHomeBinding;
import com.watchwatch.app.model.Post;
import com.watchwatch.app.utils.Constants;
import com.watchwatch.app.viewmodel.HomeViewModel;

import java.util.List;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private HomeViewModel viewModel;
    private TrendingAdapter trendingAdapter;
    private LatestPostAdapter latestPostAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupViewModel();
        setupRecyclerViews();
        setupSwipeRefresh();
        setupClickListeners();
        observeData();
    }

    private void setupViewModel() {
        viewModel = new ViewModelProvider(this).get(HomeViewModel.class);
    }

    private void setupRecyclerViews() {
        trendingAdapter = new TrendingAdapter();
        binding.rvTrending.setLayoutManager(
                new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        binding.rvTrending.setAdapter(trendingAdapter);

        latestPostAdapter = new LatestPostAdapter();
        binding.rvLatest.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rvLatest.setAdapter(latestPostAdapter);
    }

    private void setupSwipeRefresh() {
        binding.swipeRefresh.setOnRefreshListener(() -> viewModel.refresh());
    }

    private void setupClickListeners() {
        binding.cardHero.setOnClickListener(v -> {
            if (binding.cardHero.getTag() instanceof Post)
                navigateToDetail((Post) binding.cardHero.getTag());
        });

        binding.tvSeeAll.setOnClickListener(v ->
                Navigation.findNavController(requireView()).navigate(R.id.searchFragment));

        trendingAdapter.setOnPostClickListener(this::navigateToDetail);
        latestPostAdapter.setOnPostClickListener(this::navigateToDetail);
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

        viewModel.getTrendingPosts().observe(getViewLifecycleOwner(),
                posts -> trendingAdapter.setPosts(posts));

        viewModel.getLatestPosts().observe(getViewLifecycleOwner(),
                this::displayLatestWithHero);
    }

    private void displayLatestWithHero(List<Post> posts) {
        if (posts == null || posts.isEmpty()) return;

        Post heroPost = posts.get(0);
        binding.tvHeroTitle.setText(heroPost.getTitleRendered());
        binding.cardHero.setTag(heroPost);

        Glide.with(this).load(heroPost.getFeaturedImageUrl())
                .centerCrop().into(binding.ivHero);

        if (posts.size() > 1)
            latestPostAdapter.setPosts(posts.subList(1, posts.size()));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
