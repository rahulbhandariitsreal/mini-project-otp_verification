package com.example.otpverification;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.example.otpverification.databinding.ActivityMainBinding;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {


    private ActivityMainBinding binding;
    private FirebaseAuth mauth;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    private String phone_Number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        mauth=FirebaseAuth.getInstance();



    }

    public void sendotp_mth(View view) {
        phone_Number = binding.phoneEditview.getText().toString().trim();

        if (TextUtils.isEmpty(phone_Number)) {
            Toast.makeText(this, "Empty Field ", Toast.LENGTH_SHORT).show();
        } else if (phone_Number.length() > 10) {
            Toast.makeText(this, "Invalid Number", Toast.LENGTH_SHORT).show();
        } else {
            otpSend();
        }


    }

    private void otpSend() {
binding.progressBar.setVisibility(View.VISIBLE);
binding.sendBtn.setVisibility(View.INVISIBLE);

        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential credential) {
            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                binding.progressBar.setVisibility(View.GONE);
                binding.sendBtn.setVisibility(View.VISIBLE);
                Toast.makeText(MainActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onCodeSent(@NonNull String verificationId,
                                   @NonNull PhoneAuthProvider.ForceResendingToken token) {
                binding.progressBar.setVisibility(View.GONE);
                binding.sendBtn.setVisibility(View.VISIBLE);
                Intent i=new Intent(MainActivity.this,Otp_Verification_Activity.class);
                i.putExtra("phonenumber",phone_Number);
                i.putExtra("code",verificationId);
                startActivity(i);
            }
        };

        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mauth)
                        .setPhoneNumber("+91"+phone_Number)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // (optional) Activity for callback binding
                        // If no activity is passed, reCAPTCHA verification can not be used.
                        .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }
}