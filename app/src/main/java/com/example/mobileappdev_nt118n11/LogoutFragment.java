package com.example.mobileappdev_nt118n11;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.mobileappdev_nt118n11.ui.profile.Phone;

public class LogoutFragment extends Fragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Phone.Key_Phone = "";
        Intent intent = new Intent(getActivity(), MainActivity.class);
        startActivity(intent);

    }

}