package com.android.HMS.ui.LRF.Login;

import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

public class LoginViewModel {

    public static void dataChanged(TextInputLayout emptyCheck) {
        if(Objects.requireNonNull(emptyCheck.getEditText()).getText().toString().isEmpty()) {
            emptyCheck.setHelperTextEnabled(true);
            emptyCheck.setError("Can't Leave Blank");
        }
        else {
            emptyCheck.setHelperTextEnabled(false);
            emptyCheck.setError(null);
        }

    }

}
