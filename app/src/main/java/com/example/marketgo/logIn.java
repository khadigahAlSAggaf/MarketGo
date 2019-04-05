package com.example.marketgo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class logIn extends AppCompatActivity {

    private FirebaseAuth auth;
    private EditText Email;
    private EditText Password;
    private Button LogIn;
    private TextView CreateAccount;

    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        //Firebase argument
        auth = FirebaseAuth.getInstance();

        Email = findViewById(R.id.Reg_Email_editText);
        Password = findViewById(R.id.Reg_password_editText);
        LogIn = findViewById(R.id.CreateAccount_button);
        CreateAccount = findViewById(R.id.create_account_button);

        //progressBar
        progressBar = findViewById(R.id.progressBar);

        LogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String logInEmail = Email.getText().toString();
                String logInPassword = Password.getText().toString();

                if (!TextUtils.isEmpty(logInEmail) && !TextUtils.isEmpty(logInPassword)) {
                    progressBar.setVisibility(View.VISIBLE);

                    auth.signInWithEmailAndPassword(logInEmail, logInPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                sendToMain();

                            } else {
                                String e = task.getException().getMessage();
                                Toast.makeText(logIn.this, e, Toast.LENGTH_LONG).show();
                            }
                            progressBar.setVisibility(View.INVISIBLE);

                        }

                    });

                }
            }
        });//end logIn button

        CreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(logIn.this, Regestraion.class));
            }
        });
    }

    //check if the user already logIn
    @Override
    protected void onStart() {
        super.onStart();
        sendToMain();
    }

    protected void sendToMain() {
        FirebaseUser currentUser = auth.getCurrentUser();
        if (currentUser != null) {
            startActivity(new Intent(logIn.this, Home.class));
            finish();
        }
    }
}
