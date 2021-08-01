package com.android.HMS.ui.LRF.Login;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.android.HMS.R;
import com.android.HMS.data.model.DataValidity;
import com.android.HMS.databinding.FragmentLoginBinding;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

import static android.content.ContentValues.TAG;
import static android.content.Context.MODE_PRIVATE;

public class LoginFragment extends Fragment {

    private FragmentLoginBinding binding;
    FirebaseAuth auth=FirebaseAuth.getInstance();
    private TextInputEditText email;
    private TextInputEditText password;
    private TextInputLayout emailLayout;
    private Editor loginPrefsEditor;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences pref= getActivity().getSharedPreferences("loginPrefs", MODE_PRIVATE);
        if (pref.getBoolean("saveLogin",false)){
            NavHostFragment.findNavController(LoginFragment.this)
                    .navigate(R.id.action_LoginFragment_to_mainMenu);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentLoginBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        emailLayout = binding.emailLayout;
        TextInputLayout passwordLayout = binding.passwordLayout;
        email= binding.email;
        password= binding.password;

        binding.register.setOnClickListener(v -> NavHostFragment.findNavController(LoginFragment.this)
                .navigate(R.id.action_LoginFragment_to_RegisterFragment));

        binding.forgetPassword.setOnClickListener(v -> NavHostFragment.findNavController(LoginFragment.this)
                .navigate(R.id.action_LoginFragment_to_forgetPasswordFragment));

        binding.signin.setOnClickListener(v -> {
            Toast.makeText(getActivity(), "Redirecting....", Toast.LENGTH_SHORT).show();
            DataValidity dataValidity=new DataValidity();
            if (dataValidity.checkInternet(getActivity())){
                if (buttonSignInEnabled(emailLayout,passwordLayout)){
                    loginUser();
                }
                else{
                    LoginViewModel.dataChanged(emailLayout);
                    LoginViewModel.dataChanged(passwordLayout);
                }
            }

        });

        textWatcherFunction(emailLayout, email);
        textWatcherFunction(passwordLayout,password);
    }

    private void loginUser() {
        SharedPreferences loginPreferences=getActivity().getSharedPreferences("loginPrefs", MODE_PRIVATE);
        loginPrefsEditor = loginPreferences.edit();
        String Email = Objects.requireNonNull(email.getText()).toString().trim();
        String Password = Objects.requireNonNull(password.getText()).toString();
        auth.signInWithEmailAndPassword(Email, Password).addOnCompleteListener(getActivity(), task -> {
            if (task.isSuccessful()) {
                if (binding.rememberMe.isChecked()) {
                    loginPrefsEditor.putBoolean("saveLogin", true);
                    loginPrefsEditor.apply();
                }
                else {
                    loginPrefsEditor.clear();
                }
                loginPrefsEditor.putString("uid", auth.getUid());
                loginPrefsEditor.apply();
                NavHostFragment.findNavController(LoginFragment.this).navigate(R.id.action_LoginFragment_to_mainMenu);
            }
            else {
                // If sign in fails, display a message to the user.
                Log.w(TAG, "signInWithEmail:failure", task.getException());
                password.setText(null);
                email.setText(null);
                emailLayout.setError("Invalid Username or Password");
                email.requestFocus();
                Toast.makeText(getActivity(), "Authentication failed.", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void textWatcherFunction(TextInputLayout emptyCheckLayout, TextInputEditText emptyCheckEditText) {

        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                binding.signin.setEnabled(false);

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                LoginViewModel.dataChanged(emptyCheckLayout);
                binding.signin.setEnabled(buttonSignInEnabled(emailLayout, binding.passwordLayout));
            }
        };
        emptyCheckEditText.addTextChangedListener(textWatcher);
    }
    private boolean buttonSignInEnabled(TextInputLayout emailLayout, TextInputLayout passwordLayout) {
        if (Objects.requireNonNull(emailLayout.getEditText()).getText().toString().isEmpty()){
            return false;
        }
        return !Objects.requireNonNull(passwordLayout.getEditText()).getText().toString().isEmpty();
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}