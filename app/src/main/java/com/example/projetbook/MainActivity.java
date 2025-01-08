package com.example.projetbook;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.example.projetbook.fragment.AuteurFragment;
import com.example.projetbook.fragment.CategorieFragment;
import com.example.projetbook.fragment.HistoireFragment;
import com.example.projetbook.fragment.PaysFragment;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TabLayout tabLayout = findViewById(R.id.tabLayout);
        ViewPager2 viewPager = findViewById(R.id.viewPager);

        // Set up the adapter directly in MainActivity
        viewPager.setAdapter(new FragmentStateAdapter(this) {
            @NonNull
            @Override
            public Fragment createFragment(int position) {
                switch (position) {
                    case 0:
                        return new HistoireFragment();
                  case 1:
                      return new AuteurFragment();
                    case 2:
                        return new PaysFragment();
                    case 3:
                        return new CategorieFragment();
                    default:
                        return new HistoireFragment(); // Fallback
                }
            }

            @Override
            public int getItemCount() {
                return 4; // Total number of tabs
            }
        });

        // Setup TabLayout with ViewPager2
        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> {
                    switch (position) {
                        case 0:
                            tab.setText("Histoires");
                            break;
                        case 1:
                            tab.setText("Auteurs");
                            break;
                        case 2:
                            tab.setText("Pays");
                            break;
                        case 3:
                            tab.setText("Catégories");
                            break;
                    }
                }).attach();
    }
}
