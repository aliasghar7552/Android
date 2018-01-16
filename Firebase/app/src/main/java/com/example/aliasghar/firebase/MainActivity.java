package com.example.aliasghar.firebase;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MSG" ;
    private FirebaseAuth Auth;
    private ProgressBar progressBar;
    Button signin;
    EditText etEmail, etPassword;
    TextView tvSignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Auth = FirebaseAuth.getInstance();

        signin = (Button) findViewById(R.id.button);

        etEmail = (EditText) findViewById(R.id.editText3);
        etPassword = (EditText) findViewById(R.id.editText4);

        tvSignup = (TextView) findViewById(R.id.textView2);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);

    }
    public void onClick(View view) {

        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if(email.isEmpty()) {
            Toast.makeText(getBaseContext(), "Enter email", Toast.LENGTH_SHORT).show();
        }
        else if(password.isEmpty()) {
            Toast.makeText(getBaseContext(), "Enter password", Toast.LENGTH_SHORT).show();
        }
        else if(password.length() < 5) {
            Toast.makeText(getBaseContext(), "Password must be atleast 6 digits", Toast.LENGTH_SHORT).show();
        }
        else {
            progressBar.setVisibility(View.VISIBLE);
            Auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "signInWithEmail:success");
                                progressBar.setVisibility(View.GONE);
                                Intent intent = new Intent(getBaseContext(), Welcome.class);
                                startActivity(intent);
                                // FirebaseUser user = mAuth.getCurrentUser();
                                // updateUI(user);
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "signInWithEmail:failure", task.getException());
                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(getBaseContext(), "Authentication failed.", Toast.LENGTH_SHORT).show();
                                // updateUI(null);
                            }
                        }
                    });
            }
    }
    public void signup(View view) {
        Intent intent = new Intent(getBaseContext(), SignUp.class);
        startActivity(intent);

    }

    public void phoneAuth(View view) {
        Intent intent = new Intent(getBaseContext(), PhoneAuth.class);
        startActivity(intent);

    }

}
