package com.engage.engage;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class LoginRegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login_register);
        getSupportFragmentManager().beginTransaction().replace(R.id.register_frame_layout,new SignInFragment()).commit();
    }
}