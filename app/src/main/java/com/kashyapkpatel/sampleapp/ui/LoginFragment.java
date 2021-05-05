package com.kashyapkpatel.sampleapp.ui;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.kashyapkpatel.sampleapp.R;
import com.kashyapkpatel.sampleapp.databinding.FragmentLoginBinding;
import com.kashyapkpatel.sampleapp.interfaces.IFragmentCallbacks;

import static com.kashyapkpatel.sampleapp.ui.HomeActivity.KEY_IS_MAIN;

public class LoginFragment extends BaseFragment {

    public static final String TAG = "LoginFragment";
    private static final int PASSWORD_MIN_LENGTH = 8;
    private static final int PASSWORD_MAX_LENGTH = 15;

    private FragmentLoginBinding binding;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof IFragmentCallbacks) {
            iFragmentCallbacks = (IFragmentCallbacks) context;
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setUpViews();
    }

    private void setUpViews() {
        iFragmentCallbacks.updateTitle(getString(R.string.app_name));
        binding.btnLogin.setEnabled(false);
        binding.etEmailId.addTextChangedListener(validationTextWatcher);
        binding.etPassword.addTextChangedListener(validationTextWatcher);
        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // No Need to check validation as this button is enabled only when all validations are satisfied
                goToNextScreen();
            }
        });
    }

    private void validateEmailAndPassword() {
        if (!binding.etEmailId.isFocused() && !binding.etPassword.isFocused()) {
            return;
        }
        boolean emailValid = isValidEmail(binding.etEmailId.getText());
        boolean passwordValid = isValidPassword(binding.etPassword.getText());
        binding.inputLayoutEmailId.setError(emailValid ? null : getString(R.string.error_invalid_email));
        binding.inputLayoutPassword.setError(passwordValid ? null : getString(R.string.error_invalid_password));
        binding.btnLogin.setEnabled(emailValid && passwordValid);
    }

    private void goToNextScreen() {
        Bundle args = new Bundle();
        args.putBoolean(KEY_IS_MAIN, true);
        MovieListFragment movieListFragment = new MovieListFragment();
        movieListFragment.setArguments(args);
        iFragmentCallbacks.showMainFragment(movieListFragment, MovieListFragment.TAG);
    }

    private static boolean isValidEmail(CharSequence target) {
        return !TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

    private static boolean isValidPassword(CharSequence target) {
        boolean validLength = target.length() >= PASSWORD_MIN_LENGTH && target.length() <= PASSWORD_MAX_LENGTH;
        return !TextUtils.isEmpty(target) && validLength;
    }

    private TextWatcher validationTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            validateEmailAndPassword();
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };
}
