package com.lucky.e_wallet.ui.savings;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.lucky.e_wallet.databinding.FragmentSavingsBinding;
import com.lucky.e_wallet.ui.savings.SavingsViewModel;

public class SavingsFragment extends Fragment {

    private FragmentSavingsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        SavingsViewModel homeViewModel =
                new ViewModelProvider(this).get(SavingsViewModel.class);

        binding = FragmentSavingsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textSavings;
        homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}