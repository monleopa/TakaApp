package com.example.takaapp;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.takaapp.Dto.UserRequest;
import com.example.takaapp.Dto.UserResponse;
import com.example.takaapp.Dto.UserUpdateRequest;
import com.example.takaapp.Service.APIService;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class EditUser extends AppCompatActivity {
    Button btnUpload, btnPick;
    ImageView imgEditAvatar;
    EditText edtEditName, edtEditPhone, edtEditEmail;

    Uri imgUri;

    private final int PICK_IMAGE_REQUEST = 71;

    FirebaseStorage storage;
    StorageReference storageReference;
    SharedPreferences sharedPreferences;
    String avatar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user);

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        btnUpload = findViewById(R.id.btnUpload);
        imgEditAvatar = findViewById(R.id.imgEditAvatar);
        btnPick = findViewById(R.id.btnPick);

        edtEditName = findViewById(R.id.edtEditName);
        edtEditPhone = findViewById(R.id.edtEditPhone);
        edtEditEmail = findViewById(R.id.edtEditEmail);

        sharedPreferences = getSharedPreferences("loginPre", MODE_PRIVATE);
        final String userId = sharedPreferences.getString("login", "");
        Log.d("Login", userId);
        String loginType = sharedPreferences.getString("loginType", "");

        UserRequest user = new UserRequest(userId, loginType);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(GlobalVariable.url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APIService apiService = retrofit.create(APIService.class);

        Call<UserResponse> callUser = apiService.getUser(user);

        callUser.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {

                if (String.valueOf(response.code()).equals("200")) {
                    UserResponse userResponse = response.body();
                    edtEditName.setText(userResponse.getName());
                    edtEditEmail.setText(userResponse.getEmail());
                    edtEditPhone.setText(userResponse.getPhone());
                    if(userResponse.getAvatar() != null) {
                        Glide.with(EditUser.this).load(userResponse.getAvatar()).into(imgEditAvatar);
                    }

                    avatar = userResponse.getAvatar();
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });

        btnPick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLibrary();
            }
        });

        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImage(userId);
            }
        });
    }

    public void openLibrary(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null){
            imgUri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imgUri);
                imgEditAvatar.setImageBitmap(bitmap);
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }


    }

    public void uploadImage(final String userId) {
        final String name = edtEditName.getText().toString();
        final String phone = edtEditPhone.getText().toString();
        final String email = edtEditEmail.getText().toString();

        if(imgUri != null) {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.show();
            final StorageReference ref = storageReference.child("images/"+ UUID.randomUUID().toString());
            ref.putFile(imgUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progres = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
                            progressDialog.setMessage("Đang cập nhật ... "+(int)progres+"%");
                        }
                    })
                    .continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                        @Override
                        public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                            if (!task.isSuccessful()){
                                throw task.getException();
                            }
                            return ref.getDownloadUrl();
                        }
                    })
                    .addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            if (task.isSuccessful()){
                                Uri downUri = task.getResult();

                                String avatarEdit = downUri.toString();

                                if(avatarEdit == null){
                                    avatarEdit = avatar;
                                }
                                Log.d("avatar", "avatar: "+avatarEdit);
                                UserUpdateRequest userUpdateRequest = new UserUpdateRequest(name, phone, email, avatarEdit);

                                Retrofit retrofit = new Retrofit.Builder()
                                        .baseUrl(GlobalVariable.url)
                                        .addConverterFactory(GsonConverterFactory.create())
                                        .build();

                                APIService apiService = retrofit.create(APIService.class);

                                Call<UserResponse> callUpdateUser = apiService.editUser(userId, userUpdateRequest);

                                callUpdateUser.enqueue(new Callback<UserResponse>() {
                                    @Override
                                    public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                                        Log.d("log", "log: "+response.code());
                                        if(response.code() == 200)
                                        {
                                            Toast.makeText(EditUser.this, "Cập nhật thông tin thành công", Toast.LENGTH_SHORT).show();
                                        }
                                        else if(response.code() == 404){
                                            Toast.makeText(EditUser.this, "Lỗi, Không được để trống", Toast.LENGTH_SHORT).show();
                                        }
                                        else
                                            {
                                            Toast.makeText(EditUser.this, "Lỗi !!!", Toast.LENGTH_SHORT).show();
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<UserResponse> call, Throwable t) {

                                    }
                                });
                            }
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(EditUser.this, "Lỗi upload !!! " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });

        }
    }
}
