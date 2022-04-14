package itas.angelp.itas188_finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

public class OTPAuthentication extends AppCompatActivity {

    TextView changeNumber;
    EditText getOTP;
    android.widget.Button verifyOTP;
    String enteredOTP;

    FirebaseAuth firebaseAuth;
    ProgressBar progressBarOfOTPAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.authentication);

        changeNumber = findViewById(R.id.changenumber);
        verifyOTP = findViewById(R.id.verifyotp);
        getOTP = findViewById(R.id.getotp);
        progressBarOfOTPAuth = findViewById(R.id.progressbarofotpauth);

        firebaseAuth = FirebaseAuth.getInstance();

        changeNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OTPAuthentication.this, MainActivity.class);

                startActivity(intent);
            }
        });

        verifyOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                enteredOTP = getOTP.getText().toString();
                if (enteredOTP.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Enter your OTP First ", Toast.LENGTH_SHORT).show();
                } else {
                    progressBarOfOTPAuth.setVisibility(View.VISIBLE);
                    String codeReceived = getIntent().getStringExtra("otp");
                    PhoneAuthCredential credential = PhoneAuthProvider.getCredential(codeReceived, enteredOTP);
                    signInWithPhoneAuthCredential(credential);

                }
            }
        });


    }


    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    progressBarOfOTPAuth.setVisibility(View.INVISIBLE);
                    Toast.makeText(getApplicationContext(), "Login success", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(OTPAuthentication.this, SetProfile.class);
                    startActivity(intent);
                    finish();
                } else {
                    if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                        progressBarOfOTPAuth.setVisibility(View.INVISIBLE);
                        Toast.makeText(getApplicationContext(), "Login Failed", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });


    }


}