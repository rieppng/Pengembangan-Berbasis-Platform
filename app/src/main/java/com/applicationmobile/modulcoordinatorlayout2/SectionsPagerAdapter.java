package com.applicationmobile.modulcoordinatorlayout2;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class SectionsPagerAdapter extends FragmentStateAdapter {

    public SectionsPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        // Mengembalikan fragment yang sama untuk ke-3 tab sebagai contoh
        // Di aplikasi nyata, Anda bisa menggunakan switch(position) untuk memanggil Fragment berbeda
        return new TabFragment();
    }

    @Override
    public int getItemCount() {
        return 3; // Menampilkan 3 tab
    }
}
