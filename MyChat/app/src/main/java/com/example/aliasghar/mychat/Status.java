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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.StorageReference;

public class Status extends AppCompatActivity {

    Toolbar toolbar;
    EditText et_status;
    Button btn_changeStatus;

    private DatabaseReference changeStatus;
    private FirebaseAuth auth;
    private StorageReference profileImageStorage;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);

        auth = FirebaseAuth.getInstance();
        String onlineUserId = auth.getCurrentUser().getUid();
        changeStatus = FirebaseDatabase.getInstance().getReference().child("Users").child(onlineUserId);

        toolbar = (Toolbar) findViewById(R.id.statusPageToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Change Status");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        dialog = new ProgressDialog(this);

        et_status = (EditText) findViewById(R.id.et_status);
        btn_changeStatus = (Button) findViewById(R.id.btn_changeStatus);

        String oldStatus = getIntent().getExtras().get("STATUS").toString();
        et_status.setText(oldStatus);

        btn_changeStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String status = et_status.getText().toString();
                changeStatus(status);
            }
        });
    }

    private void changeStatus(String status) {

        if(status.equals("")) {
            Toast.makeText(getBaseContext(), "Enter your status", Toast.LENGTH_SHORT).show();
        }
        else {
            dialog.setTitle("Changing Profile Status");
            dialog.setMessage("Please wait...");
            dialog.show();
            changeStatus.child("status").setValue(status)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()) {
                                dialog.dismiss();
                                Intent intent = new Intent(getBaseContext(), Settings.class);
                                startActivity(intent);
                                Toast.makeText(getBaseContext(), "Status changed successfully", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                Toast.makeText(getBaseContext(), "Status changing failed", Toast.LENGTH_SHORT).show();
                            }
                            dialog.dismiss();
                        }
                    });
        }
    }
}
