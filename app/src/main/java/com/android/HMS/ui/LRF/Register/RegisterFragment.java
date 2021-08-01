package com.android.HMS.ui.LRF.Register;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.android.HMS.R;
import com.android.HMS.data.model.AccessLevels;
import com.android.HMS.data.model.DataValidity;
import com.android.HMS.data.model.UserDetails;
import com.android.HMS.databinding.FragmentRegisterBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.text.DecimalFormat;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class RegisterFragment extends Fragment {

    private FragmentRegisterBinding binding;
    private TextInputLayout emailLayout;
    private TextInputLayout passwordLayout;
    FirebaseAuth auth;
    FirebaseDatabase firebaseDatabase;
    private String staffHMSId;
    private static final String TAG = "register";
    private int hmsValue;
    private String mVerificationId;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentRegisterBinding.inflate(inflater, container, false);
        return binding.getRoot();



    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ConstraintLayout register=binding.getRoot();
        TextInputLayout nameLayout = binding.nameLayout;
        TextInputLayout confirmPasswordLayout = binding.confirmPasswordLayout;
        TextInputLayout phoneNumberLayout = binding.phoneNumberLayout;
        emailLayout = binding.emailLayout;
        passwordLayout = binding.passwordLayout;
        TextInputEditText name= binding.name;
        TextInputEditText email = binding.email;
        TextInputEditText password = binding.password;
        TextInputEditText confirmPassword=binding.confirmPassword;
        TextInputEditText phoneNumber= binding.phoneNumber;
        TextInputEditText otp= binding.otp;
        Button registerButton = binding.register;

        auth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference userReference= FirebaseDatabase.getInstance().getReference().child("0/HMS/CietKkcasCimat/NBH/Staff_Details");

        textWatcherFunction(nameLayout,name,registerButton);
        textWatcherFunction(emailLayout,email,registerButton);
        textWatcherFunction(passwordLayout,password,registerButton);
        textWatcherFunction(confirmPasswordLayout,confirmPassword,registerButton);
        textWatcherFunction(phoneNumberLayout,phoneNumber,registerButton);
        textWatcherFunction(binding.otpLayout, otp,registerButton);

        register.setOnClickListener(this::hideKeyboard);

        binding.sendOTP.setOnClickListener(view1 -> {
            String _PhoneNumber=String.format("%s%s","+91",phoneNumber.getText().toString());
            mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

                @Override
                public void onVerificationCompleted(PhoneAuthCredential credential) {
                    // This callback will be invoked in two situations:
                    // 1 - Instant verification. In some cases the phone number can be instantly
                    //     verified without needing to send or enter a verification code.
                    // 2 - Auto-retrieval. On some devices Google Play services can automatically
                    //     detect the incoming verification SMS and perform verification without
                    //     user action.
                    Log.d(TAG, "onVerificationCompleted:" + credential);

                    signInWithPhoneAuthCredential(credential);
                }

                @Override
                public void onVerificationFailed(FirebaseException e) {
                    // This callback is invoked in an invalid request for verification is made,
                    // for instance if the the phone number format is not valid.
                    Log.w(TAG, "onVerificationFailed", e);

                    if (e instanceof FirebaseAuthInvalidCredentialsException) {
                        // Invalid request
                    } else if (e instanceof FirebaseTooManyRequestsException) {
                        // The SMS quota for the project has been exceeded
                    }

                    // Show a message and update the UI
                }

                @Override
                public void onCodeSent(@NonNull String verificationId,
                                       @NonNull PhoneAuthProvider.ForceResendingToken token) {
                    // The SMS verification code has been sent to the provided phone number, we
                    // now need to ask the user to enter the code and then construct a credential
                    // by combining the code with a verification ID.
                    Log.d(TAG, "onCodeSent:" + verificationId);
                    binding.otpLayout.setEnabled(true);
                    // Save verification ID and resending token so we can use them later
                    mVerificationId = verificationId;
                    mResendToken = token;
                }
            };

                PhoneAuthOptions options =
                        PhoneAuthOptions.newBuilder(auth)
                                .setPhoneNumber(_PhoneNumber)       // Phone number to verify
                                .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                                .setActivity(getActivity())                 // Activity (for callback binding)
                                .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                                .build();
                PhoneAuthProvider.verifyPhoneNumber(options);
            });

        binding.register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Name= Objects.requireNonNull(nameLayout.getEditText()).getText().toString();
                String Email = Objects.requireNonNull(email.getText()).toString().trim();
                String Password = Objects.requireNonNull(password.getText()).toString();
                String CPassword=Objects.requireNonNull(confirmPassword.getText()).toString();
                String PhoneNumber= Objects.requireNonNull(phoneNumberLayout.getEditText()).getText().toString();


                blankError(nameLayout,registerButton);
                blankError(emailLayout,registerButton);
                passwordError(passwordLayout,false,Password,registerButton);
                passwordError(confirmPasswordLayout,true,Password,registerButton);
                blankError(phoneNumberLayout,registerButton);

                mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

                    @Override
                    public void onVerificationCompleted(PhoneAuthCredential credential) {
                        // This callback will be invoked in two situations:
                        // 1 - Instant verification. In some cases the phone number can be instantly
                        //     verified without needing to send or enter a verification code.
                        // 2 - Auto-retrieval. On some devices Google Play services can automatically
                        //     detect the incoming verification SMS and perform verification without
                        //     user action.
                        Log.d(TAG, "onVerificationCompleted:" + credential);

                        signInWithPhoneAuthCredential(credential);
                    }

                    @Override
                    public void onVerificationFailed(FirebaseException e) {
                        // This callback is invoked in an invalid request for verification is made,
                        // for instance if the the phone number format is not valid.
                        Log.w(TAG, "onVerificationFailed", e);

                        if (e instanceof FirebaseAuthInvalidCredentialsException) {
                            // Invalid request
                        } else if (e instanceof FirebaseTooManyRequestsException) {
                            // The SMS quota for the project has been exceeded
                        }

                        // Show a message and update the UI
                    }

                    @Override
                    public void onCodeSent(@NonNull String verificationId,
                                           @NonNull PhoneAuthProvider.ForceResendingToken token) {
                        // The SMS verification code has been sent to the provided phone number, we
                        // now need to ask the user to enter the code and then construct a credential
                        // by combining the code with a verification ID.
                        Log.d(TAG, "onCodeSent:" + verificationId);

                        // Save verification ID and resending token so we can use them later
                        mVerificationId = verificationId;
                        mResendToken = token;
                    }

                    @Override
                    public void onCodeAutoRetrievalTimeOut(@NonNull @NotNull String s) {
                        super.onCodeAutoRetrievalTimeOut(s);
                        binding.sendOTP.setText("Resend OTP");
                    }
                };

                if(registerButton.isEnabled()){
                    DataValidity dataValidity=new DataValidity();
                    if (dataValidity.checkInternet(getActivity())) {
                        auth.createUserWithEmailAndPassword(Email, Password).addOnCompleteListener(getActivity(), task -> {
                            if (task.isSuccessful()) {
                                Log.d(TAG, "createUserWithEmail: Success");
                                Toast.makeText(getActivity(), "Register Success", Toast.LENGTH_SHORT).show();
                                userReference.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NotNull DataSnapshot dataSnapshot) {
                                        hmsValue = Integer.parseInt(Objects.requireNonNull(dataSnapshot.child("HMS").getValue(String.class)));
                                    }

                                    @Override
                                    public void onCancelled(@NotNull DatabaseError error) {
                                        Log.w(TAG, "Failed to read value.", error.toException());
                                    }
                                });

                                userReference.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NotNull DataSnapshot dataSnapshot) {
                                        hmsValue++;
                                        staffHMSId=String.format("%s%s","HMS",new DecimalFormat("000").format(hmsValue));
                                        UserDetails userDetails=new UserDetails(Name,Email,PhoneNumber,staffHMSId);
                                        userReference.child("HMS").setValue(new DecimalFormat("000").format(hmsValue));
                                        userReference.child(auth.getUid()).setValue(userDetails).addOnSuccessListener(aVoid -> {
                                            userReference.child(auth.getUid()).child("Access_Level").setValue(new AccessLevels(false));
                                            NavHostFragment.findNavController(RegisterFragment.this)
                                                    .navigate(R.id.action_RegisterFragment_to_LoginFragment);
                                        });
                                    }

                                    @Override
                                    public void onCancelled(@NotNull DatabaseError error) {
                                        Log.w(TAG, "Failed to read value.", error.toException());
                                    }
                                });
                            }
                            else {
                                Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                Toast.makeText(getActivity(), "Authentication failed.", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }

            }
        });
    }
    private void blankError(TextInputLayout BC,Button registerButton){
        if (Objects.requireNonNull(BC.getEditText()).getText().toString().isEmpty()) {
            BC.setError("Can't be Blank");
            BC.requestFocus();
            registerButton.setEnabled(false);
        }
        else{
            BC.setHelperTextEnabled(false);
            BC.setError(null);
        }
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        auth.signInWithCredential(credential)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            NavHostFragment.findNavController(RegisterFragment.this)
                                    .navigate(R.id.action_RegisterFragment_to_mainMenu);
                            FirebaseUser user = task.getResult().getUser();
                            // Update UI
                        } else {
                            // Sign in failed, display a message and update the UI
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                binding.otpLayout.setError("Invalid OTP");
                            }
                        }
                    }
                });
    }


    private void passwordError(TextInputLayout PE,boolean CPE,String password,Button registerButton){
        if(Objects.requireNonNull(PE.getEditText()).getText().toString().isEmpty()){
            PE.setError("Can't be Blank");
            PE.requestFocus();
            registerButton.setEnabled(false);
        }
        else if(Objects.requireNonNull(PE.getEditText()).getText().toString().length()<8&& !CPE){
            PE.setError("Password is less than 8 characters");
            PE.requestFocus();
            registerButton.setEnabled(false);
        }
        else if (CPE && !Objects.requireNonNull(PE.getEditText()).getText().toString().equals(password)){
            PE.setError("Password didn't match");
            PE.requestFocus();
            registerButton.setEnabled(false);
        }
        else{
            PE.setHelperTextEnabled(false);
            PE.setError(null);
        }
    }

    public void textWatcherFunction(TextInputLayout emptyCheckLayout, TextInputEditText emptyCheckEditText,Button registerButton) {

        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                registerButton.setEnabled(true);
                blankError(emptyCheckLayout,registerButton);
            }
        };
        emptyCheckEditText.addTextChangedListener(textWatcher);
    }

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getActivity().getSystemService(getActivity().INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}