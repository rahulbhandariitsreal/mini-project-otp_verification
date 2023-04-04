package com.example.otpverification;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.otpverification.databinding.ActivityOtpVerificationBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

public class Otp_Verification_Activity extends AppCompatActivity {



    private FirebaseAuth mauth;
    private ActivityOtpVerificationBinding binding;

    private  PhoneAuthCredential credential;
    String phone_number,verificationid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityOtpVerificationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mauth=FirebaseAuth.getInstance();
         phone_number=getIntent().getStringExtra("phonenumber");
         verificationid=getIntent().getStringExtra("code");

         binding.phoneNumber.setText("An otp has been sent to"+phone_number);




    }

    public void resend_meth(View view) {
        Toast.makeText(this, "otp send succesfully", Toast.LENGTH_SHORT).show();
    }

    public void VerifyOtp_meth(View view) {
      String code=  binding.otpView.getText().toString();
         credential = PhoneAuthProvider.getCredential(verificationid, code);

        mauth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = task.getResult().getUser();
                            Intent intent=new Intent(Otp_Verification_Activity.this,SuccessActivity.class);
                            intent.putExtra("user",user);
                            startActivity(intent);
                            // Update UI
                        } else {
                            // Sign in failed, display a message and update the UI
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                            }
                        }
                    }
                });
    }
}