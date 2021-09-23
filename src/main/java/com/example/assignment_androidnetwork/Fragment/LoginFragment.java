package com.example.assignment_androidnetwork.Fragment;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.assignment_androidnetwork.Constants;
import com.example.assignment_androidnetwork.Model.ServerRequest;
import com.example.assignment_androidnetwork.Model.ServerResponse;
import com.example.assignment_androidnetwork.Model.User;
import com.example.assignment_androidnetwork.R;
import com.example.assignment_androidnetwork.RequestInterface;
import com.google.android.material.snackbar.Snackbar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginFragment extends Fragment implements View.OnClickListener {

    private Button btnLogin;
    private EditText edtEmail, edtPassword;
    private TextView tvRegister, tvForgotPassword;
    private ProgressBar progressBar;
    private SharedPreferences pref;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        initViews(view);
        return view;

    }

    private void initViews(View view){
        pref = getActivity().getPreferences(0);
        Log.d("Share Preference", pref + "");
        btnLogin = view.findViewById(R.id.btnLogin);
        edtEmail = view.findViewById(R.id.edtEmail);
        edtPassword = view.findViewById(R.id.edtPassword);
        tvRegister = view.findViewById(R.id.tvRegister);
        tvForgotPassword = view.findViewById(R.id.tvForgotPassword);

        progressBar = view.findViewById(R.id.progress);

        progressBar.setVisibility(View.INVISIBLE);

        btnLogin.setOnClickListener(this);
        tvRegister.setOnClickListener(this);
        tvForgotPassword.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnLogin:
                String email = edtEmail.getText().toString();
                String password = edtPassword.getText().toString();

                if(!email.isEmpty() && !password.isEmpty()){
                    progressBar.setVisibility(View.VISIBLE);
                    loginProcess(email, password);
                }else {
                    Snackbar.make(getView(), "Fields are empty !", Snackbar.LENGTH_LONG).show();
                }
                break;
            case R.id.tvRegister:
                Snackbar.make(getView(), "Hello", Snackbar.LENGTH_LONG).show();
                goToRegister();
                break;
            case R.id.tvForgotPassword:
                goToResetPassword();
                break;
        }
    }

    private void loginProcess(String email, String password){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RequestInterface requestInterface = retrofit.create(RequestInterface.class);

        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        ServerRequest request = new ServerRequest();
        request.setOperation(Constants.LOGIN_OPERATION);
        request.setUser(user);

        Call<ServerResponse> response = requestInterface.operation(request);

        response.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                ServerResponse resp = response.body();
                Log.d("LOGIN CONSTANTS", resp.getResult());
                Snackbar.make(getView(), resp.getMessage(), Snackbar.LENGTH_LONG).show();

                if(resp.getResult().equals(Constants.SUCCESS)){
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putBoolean(Constants.IS_LOGGED_IN, true);
                    editor.putString(Constants.EMAIL, resp.getUser().getEmail());
                    editor.putString(Constants.NAME, resp.getUser().getName());
                    editor.putString(Constants.UNIQUE_ID, resp.getUser().getUnique_id());
                    editor.apply();
                    goToProfile();
                }
                progressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                progressBar.setVisibility(View.INVISIBLE);
                Log.d(Constants.TAG, "failed");
                Snackbar.make(getView(), t.getMessage(), Snackbar.LENGTH_LONG).show();
            }
        });
    }

    private void goToRegister(){
        Fragment register = new RegisterFragment();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_frame, register);
        ft.commit();
    }

    private void goToProfile(){
        Fragment profile = new com.example.assignment_androidnetwork.Fragment.ProfileFragment();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_frame, profile);
        ft.commit();
    }

    private void goToResetPassword(){
        Fragment reset = new com.example.assignment_androidnetwork.Fragment.ResetPasswordFragment();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_frame, reset);
        ft.commit();
    }

}
