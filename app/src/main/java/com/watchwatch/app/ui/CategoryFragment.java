package com.watchwatch.app.ui;

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

import com.watchwatch.app.R;
import com.watchwatch.app.adapter.CategoryAdapter;
import com.watchwatch.app.databinding.FragmentCategoryBinding;
import com.watchwatch.app.viewmodel.CategoryViewModel;

public class CategoryFragment extends Fragment {

    private FragmentCategoryBinding binding;
    private CategoryViewModel viewModel;
    private CategoryAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentCategoryBinding.inflate(inflater, container, false);
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
        viewModel = new ViewModelProvider(this).get(CategoryViewModel.class);
    }

    private void setupRecyclerView() {
        adapter = new CategoryAdapter();
        // Single column — lebih bersih dari grid
        binding.rvCategories.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rvCategories.setAdapter(adapter);

        adapter.setOnCategoryClickListener(category -> {
            Bundle args = new Bundle();
            args.putInt(ListReviewFragment.ARG_CATEGORY_ID, category.getId());
            args.putString(ListReviewFragment.ARG_CATEGORY_NAME, category.getName());
            Navigation.findNavController(requireView())
                    .navigate(R.id.action_category_to_listReview, args);
        });
    }

    private void observeData() {
        viewModel.getIsLoading().observe(getViewLifecycleOwner(), isLoading ->
                binding.progressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE));

        viewModel.getCategories().observe(getViewLifecycleOwner(),
                categories -> adapter.setCategories(categories));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
