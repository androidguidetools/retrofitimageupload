package com.retrofitdemo.retrofitlearnigtopics;

import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.retrofitdemo.retrofitlearnigtopics.model.ImageUploadModel;
import com.retrofitdemo.retrofitlearnigtopics.rest.ApiClient2;
import com.retrofitdemo.retrofitlearnigtopics.rest.ApiInterface3;

import java.io.ByteArrayOutputStream;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RetrofitImageUploadDemo extends AppCompatActivity implements View.OnClickListener {

    private EditText etTitle;
    private Button btChooseImage, btUploadImage;
    private ImageView imageUpload;
    private Bitmap bitmap;
    private static final int IMG_REQUEST = 777;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrofit_image_upload_demo);

        etTitle = (EditText) findViewById(R.id.etTitle);

        btChooseImage = (Button) findViewById(R.id.btnChooseImage);
        btUploadImage = (Button) findViewById(R.id.btnUploadImage);

        imageUpload = (ImageView) findViewById(R.id.imageUpload);


        btChooseImage.setOnClickListener(this);
        btUploadImage.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {

        int id = v.getId();
        if (id == R.id.btnChooseImage) {
            imageSelect();

        } else if (id == R.id.btnUploadImage) {

            imageUpload();
        }
    }

    private void imageUpload() {
        String image = imageToString();
        String title = etTitle.getText().toString();
        ApiInterface3 apiInterface3 = ApiClient2.getApiClient().create(ApiInterface3.class);


        Call<ImageUploadModel> call = apiInterface3.uploadImage(title,image);
        call.enqueue(new Callback<ImageUploadModel>() {
            @Override
            public void onResponse(Call<ImageUploadModel> call, Response<ImageUploadModel> response) {

                ImageUploadModel imageUploadModel = response.body();
                Toast.makeText(RetrofitImageUploadDemo.this, ""+imageUploadModel.getResponse(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ImageUploadModel> call, Throwable t) {
                Toast.makeText(RetrofitImageUploadDemo.this, "error", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void imageSelect() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, IMG_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == IMG_REQUEST && resultCode == RESULT_OK && data != null) {

            Uri path = data.getData();

            try{
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),path);

                imageUpload.setImageBitmap(bitmap);

            }catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    private String imageToString()
    {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);

        byte[] imgByte = byteArrayOutputStream.toByteArray();

        return Base64.encodeToString(imgByte,Base64.DEFAULT);
    }
}
