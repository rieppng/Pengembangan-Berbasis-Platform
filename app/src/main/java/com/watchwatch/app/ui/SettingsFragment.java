package com.watchwatch.app.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.watchwatch.app.BuildConfig;
import com.watchwatch.app.databinding.FragmentSettingsBinding;
import com.watchwatch.app.utils.Constants;

public class SettingsFragment extends Fragment {

    private FragmentSettingsBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentSettingsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupStaticPageLinks();
        setupVersionInfo();
    }

    private void setupStaticPageLinks() {
        binding.itemAbout.setOnClickListener(v ->
                openStaticPage(Constants.SLUG_ABOUT, "Tentang Kami"));

        binding.itemDisclaimer.setOnClickListener(v ->
                openStaticPage(Constants.SLUG_DISCLAIMER, "Disclaimer"));

        binding.itemPrivacy.setOnClickListener(v ->
                openStaticPage(Constants.SLUG_PRIVACY, "Privacy Policy"));

        binding.itemContact.setOnClickListener(v ->
                openStaticPage(Constants.SLUG_CONTACT, "Kontak"));
    }

    private void openStaticPage(String slug, String title) {
        Intent intent = new Intent(requireContext(), StaticPageActivity.class);
        intent.putExtra(StaticPageActivity.EXTRA_SLUG, slug);
        intent.putExtra(StaticPageActivity.EXTRA_TITLE, title);
        startActivity(intent);
    }

    private void setupVersionInfo() {
        binding.tvVersion.setText(BuildConfig.VERSION_NAME);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
