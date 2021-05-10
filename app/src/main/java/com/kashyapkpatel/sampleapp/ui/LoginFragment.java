package com.kashyapkpatel.sampleapp.ui;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.kashyapkpatel.sampleapp.R;
import com.kashyapkpatel.sampleapp.databinding.FragmentLoginBinding;
import com.kashyapkpatel.sampleapp.interfaces.IFragmentCallbacks;
import com.kashyapkpatel.sampleapp.viewmodel.LoginViewModel;

import static com.kashyapkpatel.sampleapp.ui.HomeActivity.KEY_IS_MAIN;

public class LoginFragment extends BaseFragment {

    public static final String TAG = "LoginFragment";
    private static final int PASSWORD_MIN_LENGTH = 8;
    private static final int PASSWORD_MAX_LENGTH = 15;

    private FragmentLoginBinding binding;

    private LoginViewModel loginViewModel;

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
        loginViewModel = new ViewModelProvider(requireActivity()).get(LoginViewModel.class);
        binding.setViewModel(loginViewModel);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupBindingsAndObservables();
    }

    private void setupBindingsAndObservables() {
        iFragmentCallbacks.updateTitle(getString(R.string.app_name));
        binding.setIsLoginEnabled(false);
        binding.setOnTextChanged((s, start, before, count) -> validateEmailAndPassword());
        // Observe the snackbar action from the view model and show a snackbar when a new action comes in
        loginViewModel.getActionLiveDataLoginClicked().observe(this, unit -> {
            // No Need to check validation as this button is enabled only when all validations are satisfied
            goToNextScreen();
        });
    }

    private void validateEmailAndPassword() {
        if (!binding.etEmailId.isFocused() && !binding.etPassword.isFocused()) {
            return;
        }
        boolean emailValid = isValidEmail(binding.etEmailId.getText());
        boolean passwordValid = isValidPassword(binding.etPassword.getText());
        binding.setEmailError(emailValid ? null : getString(R.string.error_invalid_email));
        binding.setPasswordError(passwordValid ? null : getString(R.string.error_invalid_password));
        binding.setIsLoginEnabled(emailValid && passwordValid);
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
}
