package com.example.mobileappdev_nt118n11.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mobileappdev_nt118n11.R;
import com.example.mobileappdev_nt118n11.fragments.FragmentAdapter;
import com.google.android.material.tabs.TabLayout;


public class OrdersFragment extends Fragment {

    private TabLayout tblOrders;
    private ViewPager2 vpOrders;
    private FragmentAdapter fragmentAdapter;
    private FragmentActivity mContext;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_orders, container, false);

        tblOrders = root.findViewById(R.id.tbl_orders);
        vpOrders = root.findViewById(R.id.vp2_orders);

        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentAdapter = new FragmentAdapter(fragmentManager, getLifecycle());

        vpOrders.setAdapter(fragmentAdapter);

        tblOrders.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                vpOrders.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        vpOrders.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                tblOrders.selectTab(tblOrders.getTabAt(position));
            }
        });

        return root;
    }
}