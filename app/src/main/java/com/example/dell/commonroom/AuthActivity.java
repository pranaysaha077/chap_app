package com.example.dell.commonroom;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.TextureView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.TimeUnit;

public class AuthActivity extends AppCompatActivity {

    private EditText mPhoneText;
    private EditText mCodeText;
    private Button mbtn;

    private TextView mErrorText;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallBacks;

    private String mVerificationId;

    private PhoneAuthProvider.ForceResendingToken mResendToken;

    private FirebaseAuth mAuth;
    private String phonenumber;

    private  EditText mName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        mPhoneText=(EditText)findViewById(R.id.auth_phone);
        mCodeText=(EditText)findViewById(R.id.auth_Verification);

       // mErrorText=(TextView) findViewById(R.id.errorText);

        mbtn=findViewById(R.id.auth_button);

        mName=findViewById(R.id.editName);


        mAuth= FirebaseAuth.getInstance();

        mbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mPhoneText.setEnabled(false);
                mbtn.setEnabled(false);
               // mCodeText.setEnabled(false);

                phonenumber=mPhoneText.getText().toString();

                PhoneAuthProvider.getInstance().verifyPhoneNumber(
                        phonenumber,
                        60,
                        TimeUnit.SECONDS,
                        AuthActivity.this,
                                mCallBacks
                );

            }
        });

        mCallBacks= new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {

                signInWithPhoneAuthCredential(phoneAuthCredential);



            }

            @Override
            public void onVerificationFailed(FirebaseException e) {


                mErrorText.setText("There Was Some Error In Verificatrion");
                mErrorText.setText(View.VISIBLE);

            }

            @Override
            public void onCodeSent(String verificationId,
                                   PhoneAuthProvider.ForceResendingToken token) {

                // Save verification ID and resending token so we can use them later
                mVerificationId = verificationId;
                mResendToken = token;

                mCodeText.setVisibility(View.VISIBLE);
                mbtn.setText("Verify Code");
                mbtn.setEnabled(true);
                mName.setEnabled(false);

                // ...
            }

        };
    }
    //phone auth

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information


                            FirebaseUser user = task.getResult().getUser();

                            Intent subIntent=new Intent(AuthActivity.this,TalkActivity.class);
                            writeUserTodatabase();
                            startActivity(subIntent);
                            finish();
                            // ...
                        } else {
                            // Sign in failed, display a message and update the UI

                            mErrorText.setText("There Was Some Error In Loggin In");
                            mErrorText.setText(View.VISIBLE);

                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                            }
                        }
                    }
                });
    }
    private void writeUserTodatabase()
    {
        FirebaseDatabase.getInstance().getReference().child("users").child(phonenumber).setValue(mName.getText().toString());
    }

}

