package com.example.stanislavlukanov.kodeup.Auth;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.stanislavlukanov.kodeup.R;


public class AuthFragment extends Fragment {

    private EditText mLogin;
    private EditText mPassword;
    private Button mEnter;
    private Button mRegistry;
    private SharedPreferencesHelper mSharedPreferencesHelper;

    public static AuthFragment newInstance() {

        Bundle args = new Bundle();

        AuthFragment fragment = new AuthFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private View.OnClickListener mOnEnterClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            boolean isLoginSuccess = false;

            for (User user : mSharedPreferencesHelper.getUsers()){

                if (user.getmLogin().equalsIgnoreCase(mLogin.getText().toString()) && user.getmPassword().equals(mPassword.getText().toString())){
                    isLoginSuccess = true;

                    if(isEmailValid() && isPasswordValid()){
                        Intent startProfileIntent =
                                new Intent(getActivity(), AppActivity.class);
                        startProfileIntent.putExtra(AppActivity.USER_KEY, new User(mLogin.getText().toString(),mPassword.getText().toString()));
                        startActivity(startProfileIntent);
                    } else {
                        showMessage(R.string.input_error);
                    }
                    break;
                }
            }

            if (!isLoginSuccess) {
                showMessage(R.string.login_input_error);
            }
        }
    };

    private View.OnClickListener mOnRegisterClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            getFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, RegistrationFragment.newInstance())
                    .addToBackStack(RegistrationFragment.class.getName())
                    .commit();
        }
    };

    private boolean isEmailValid() {
        return !TextUtils.isEmpty(mLogin.getText())
                && Patterns.EMAIL_ADDRESS.matcher(mLogin.getText()).matches();
    };

    private boolean isPasswordValid() {
        return !TextUtils.isEmpty(mPassword.getText());
    }

    private void showMessage(@StringRes int string) {
        Toast.makeText(getActivity(),string,Toast.LENGTH_LONG).show();
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fr_auth, container, false);

        mSharedPreferencesHelper = new SharedPreferencesHelper(getActivity());

        mLogin = v.findViewById(R.id.etLogin);
        mPassword = v.findViewById(R.id.etPassword);
        mEnter = v.findViewById(R.id.button_enter);
        mRegistry = v.findViewById(R.id.button_registry);

        mEnter.setOnClickListener(mOnEnterClickListener);
        mRegistry.setOnClickListener(mOnRegisterClickListener);

        return v;
    }
}

