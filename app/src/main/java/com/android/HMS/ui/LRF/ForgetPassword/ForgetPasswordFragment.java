package com.android.HMS.ui.LRF.ForgetPassword;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.android.HMS.R;
import com.android.HMS.databinding.FragmentForgetPasswordBinding;

public class ForgetPasswordFragment extends Fragment {

    private FragmentForgetPasswordBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentForgetPasswordBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.forgetPassword.setOnClickListener(view1 ->
                NavHostFragment.findNavController(ForgetPasswordFragment.this)
                .navigate(R.id.action_RegisterFragment_to_LoginFragment));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}