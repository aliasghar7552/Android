package com.example.aliasghar.mychat;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import id.zelory.compressor.Compressor;

public class Settings extends AppCompatActivity {

    private Button btn_changeProfileImage, btn_changeStatus;
    private TextView tv_username, tv_status;
    private CircleImageView displayProfileImage;

    private DatabaseReference getUserData;
    private FirebaseAuth auth;
    private StorageReference profileImageStorage;
    private StorageReference thumbImageStorage;

    Bitmap thumbBitmap;

    ProgressDialog dialog;

    private static final int gallery_image = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        profileImageStorage = FirebaseStorage.getInstance().getReference().child("Profile_Images");
        thumbImageStorage = FirebaseStorage.getInstance().getReference().child("Thumb_Images");

        auth = FirebaseAuth.getInstance();
        String onlineUserId = auth.getCurrentUser().getUid();
        getUserData = FirebaseDatabase.getInstance().getReference().child("Users").child(onlineUserId);
        getUserData.keepSynced(true);

        btn_changeProfileImage = (Button) findViewById(R.id.btn_changeProfileImage);
        btn_changeStatus = (Button) findViewById(R.id.btn_changeStatus);
        tv_username = (TextView) findViewById(R.id.tv_profileUsername);
        tv_status = (TextView) findViewById(R.id.tv_profileStatus);
        displayProfileImage = (CircleImageView) findViewById(R.id.profile_image);

        dialog = new ProgressDialog(this);

        getUserData.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String profileUsername = dataSnapshot.child("username").getValue().toString();
                String profileStatus = dataSnapshot.child("status").getValue().toString();
                final String profileImage = dataSnapshot.child("image").getValue().toString();
                String thumdImage = dataSnapshot.child("thumb").getValue().toString();

                tv_username.setText(profileUsername);
                tv_status.setText(profileStatus);

                if(! profileImage.equals("default_profile")) {

                    Picasso.with(Settings.this).load(profileImage).networkPolicy(NetworkPolicy.OFFLINE)
                            .placeholder(R.drawable.default_profile).into(displayProfileImage, new Callback() {
                        @Override
                        public void onSuccess() {

                        }

                        @Override
                        public void onError() {
                            Picasso.with(Settings.this).load(profileImage).placeholder(R.drawable.default_profile).into(displayProfileImage);

                        }
                    });

                }

        }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        btn_changeProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, gallery_image);
            }
        });

        btn_changeStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String oldStatus = tv_status.getText().toString().trim();
                Intent intent = new Intent(getBaseContext(), Status.class);
                intent.putExtra("STATUS", oldStatus);
                startActivity(intent);
            }
        });



    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent intent = new Intent(getBaseContext(), MainActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == gallery_image && resultCode == RESULT_OK && data != null) {
            Uri imageUri = data.getData();

            CropImage.activity()
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .setAspectRatio(1, 1)
                    .start(this);
        }

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {

                dialog.setMessage("Please wait...");
                dialog.setTitle("Uploading Image");
                dialog.show();

                Uri resultUri = result.getUri();

                File thumbFilePathURI = new File(resultUri.getPath());
                String userId = auth.getCurrentUser().getUid();

                try {
                    thumbBitmap = new Compressor(this)
                            .setMaxWidth(200)
                            .setMaxHeight(200)
                            .setQuality(50)
                            .compressToBitmap(thumbFilePathURI);
                }
                catch (IOException e) {
                    e.printStackTrace();
                }

                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                thumbBitmap.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream);
                final byte[] thumbByte = byteArrayOutputStream.toByteArray();

                StorageReference filePath = profileImageStorage.child(userId + ".jpg");
                final StorageReference thumbFilePath = thumbImageStorage.child(userId + ".jpg");

                filePath.putFile(resultUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                        if(task.isSuccessful()) {
                            Toast.makeText(getBaseContext(), "Profile image saved", Toast.LENGTH_SHORT).show();

                            final String downloadURL = task.getResult().getDownloadUrl().toString();

                            UploadTask uploadTask = thumbFilePath.putBytes(thumbByte);
                            uploadTask.addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> thumbTask) {

                                    String thumbDownloadURL = thumbTask.getResult().getDownloadUrl().toString();

                                    if(thumbTask.isSuccessful()) {
                                        Map updateUserData = new HashMap();
                                        updateUserData.put("image", downloadURL);
                                        updateUserData.put("thumb", thumbDownloadURL);

                                        getUserData.updateChildren(updateUserData)
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        Toast.makeText(getBaseContext(), "Profile image uploaded successfully", Toast.LENGTH_SHORT).show();
                                                    }
                                                });
                                    }
                                    else {
                                        Toast.makeText(getBaseContext(), "Error accured while uploading your profile picture", Toast.LENGTH_SHORT).show();
                                    }
                                    dialog.dismiss();
                                }
                            });
                                    }

                                }
                            });

            }
            else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }
}
