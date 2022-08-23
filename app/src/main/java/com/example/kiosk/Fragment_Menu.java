package com.example.kiosk;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

public class Fragment_Menu extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menu, container, false);

        TabLayout tabLayout = view.findViewById(R.id.tab_layout);
        Fragment_tab1 frag1 = new Fragment_tab1();
        Fragment_tab2 frag2 = new Fragment_tab2();
        Fragment_tab3 frag3 = new Fragment_tab3();
        Fragment_tab4 frag4 = new Fragment_tab4();

        getChildFragmentManager().beginTransaction().replace(R.id.tab_container, frag1).commit();

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()){
                    case 0:
                        getChildFragmentManager().beginTransaction().replace(R.id.tab_container, frag1).commit();
                        break;

                    case 1:
                        getChildFragmentManager().beginTransaction().replace(R.id.tab_container, frag2).commit();
                        break;

                    case 2:
                        getChildFragmentManager().beginTransaction().replace(R.id.tab_container, frag3).commit();
                        break;

                    case 3:
                        getChildFragmentManager().beginTransaction().replace(R.id.tab_container, frag4).commit();
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        return view;
    }
}
