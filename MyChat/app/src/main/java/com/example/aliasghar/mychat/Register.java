package com.example.aliasghar.mychat;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;

public class Register extends AppCompatActivity {

    private FirebaseAuth auth;
    private DatabaseReference myRef;

    ProgressDialog dialog;
    Toolbar toolbar;
    EditText et_name, et_email, et_password;
    Button et_register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        auth = FirebaseAuth.getInstance();

        dialog = new ProgressDialog(this);

        toolbar = (Toolbar) findViewById(R.id.registerPageToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Sign Up");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        et_name = (EditText) findViewById(R.id.et_name);
        et_email = (EditText) findViewById(R.id.et_email);
        et_password = (EditText) findViewById(R.id.et_password);

        et_register = (Button) findViewById(R.id.btn_register);

        et_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String name = et_name.getText().toString();
                String email = et_email.getText().toString();
                String password = et_password.getText().toString();

                registerAccount(name, email, password);
            }
        });
    }

    private void registerAccount(final String name, String email, String password) {

        if(name.equals("")) {
            Toast.makeText(getBaseContext(), "Enter your name", Toast.LENGTH_SHORT).show();
        }
        else if(email.equals("")) {
            Toast.makeText(getBaseContext(), "Enter your email address", Toast.LENGTH_SHORT).show();
        }
        else if(password.equals("")) {
            Toast.makeText(getBaseContext(), "Enter password", Toast.LENGTH_SHORT).show();
        }
        else {
            dialog.setTitle("Creating New Account");
            dialog.setMessage("Please wait...");
            dialog.show();
            auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {

                                String token = FirebaseInstanceId.getInstance().getToken();
                                String curreuntUserId = auth.getCurrentUser().getUid();
                                myRef = FirebaseDatabase.getInstance().getReference().child("Users").child(curreuntUserId);
                                myRef.child("username").setValue(name);
                                myRef.child("status").setValue("Hey there, I am using myChat app");
                                myRef.child("image").setValue("default_profile");
                                myRef.child("thumb").setValue("default_image");
                                myRef.child("device_token").setValue(token)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if(task.isSuccessful()) {
                                                    Intent intent = new Intent(getBaseContext(), MainActivity.class);
                                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                    startActivity(intent);
                                                    finish();
                                                }
                                                else {
                                                    Toast.makeText(getBaseContext(), "Registration failed", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                            }
                            else {
                                Toast.makeText(getBaseContext(), "Registration failed 2", Toast.LENGTH_SHORT).show();
                            }
                            dialog.dismiss();
                        }
                    });
        }
    }
}
