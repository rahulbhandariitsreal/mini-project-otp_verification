package com.example.otpverification;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.otpverification.databinding.ActivitySuccessBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SuccessActivity extends AppCompatActivity {



    private FirebaseUser user;
    private FirebaseAuth mauth;

    private ActivitySuccessBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivitySuccessBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        mauth=FirebaseAuth.getInstance();
        user=mauth.getCurrentUser();
     binding.mobileNumber.setText(user.getPhoneNumber());


    }

    public void logoutme(View view) {
       mauth.signOut();
        startActivity(new Intent(SuccessActivity.this,MainActivity.class));
    }
}