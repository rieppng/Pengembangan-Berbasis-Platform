package com.applicationmobile.modulcoordinatorlayout2;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 1. Inisialisasi View
        ViewPager2 viewPager = findViewById(R.id.container);
        TabLayout tabLayout = findViewById(R.id.tabs);

        // 2. Pasang Adapter ke ViewPager2
        SectionsPagerAdapter adapter = new SectionsPagerAdapter(this);
        viewPager.setAdapter(adapter);

        // 3. Hubungkan TabLayout dengan ViewPager2 menggunakan TabLayoutMediator
        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            // Mengatur Icon untuk setiap tab berdasarkan posisinya
            switch (position) {
                case 0:
                    tab.setIcon(R.drawable.camera);
                    break;
                case 1:
                    tab.setIcon(R.drawable.cart);
                    break;
                case 2:
                    tab.setIcon(R.drawable.option);
                    break;
            }
        }).attach();
    }
}