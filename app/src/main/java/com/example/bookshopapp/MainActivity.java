package com.example.bookshopapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.content.AsyncTaskLoader;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

   ImageView img1;
    EditText txtbname;
    Bitmap img;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtbname=findViewById(R.id.txtbname);
        img1=findViewById(R.id.img1);
    }

    public void getimage(View view) {

    Intent i=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
    startActivityForResult(i,100);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        img =(Bitmap)data.getExtras().get("data");
        img1.setImageBitmap(img);

    }

    public void savebookdetail(View view) {

    String bname=txtbname.getText().toString();
    SaveBook(bname,img);


    }

    private void SaveBook(String bname, Bitmap img) {
        String imageBase64 = encodeImageToBase64(img);

        new AsyncTask<Void,Void,String>()
        {
            @Override
            protected String doInBackground(Void... voids) {

                OkHttpClient client = new OkHttpClient();
                RequestBody requestBody=new FormBody.Builder()
                        .add("bname",bname)
                        .add("image",imageBase64)
                        .build();

                Request request = new Request.Builder()
                        .url("http://reslatur.org.in/drsbook/insertBook.php")  // Change to your server URL
                        .post(requestBody)
                        .build();

                try {
                    Response response = client.newCall(request).execute();
                    return response.body().string();
                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }

            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                if (s != null) {
                    Toast.makeText(MainActivity.this, s, Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(MainActivity.this, "Error uploading book details.", Toast.LENGTH_LONG).show();
                }
            }
        }.execute();

    }
    private String encodeImageToBase64(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }

    public void showallbooks(View view) {

        Intent i=new Intent(MainActivity.this, MainActivity2.class);
        startActivity(i);
    }
}