package com.example.bookshopapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity2 extends AppCompatActivity {


    ListView lst1;
    BooksAdapter adapter;
    ArrayList<Book> bookList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        lst1=findViewById(R.id.lst1);
        adapter = new BooksAdapter(this, bookList);
        lst1.setAdapter(adapter);
        showAllbook();
    }

    private void showAllbook() {

        new AsyncTask<Void, Void,String>(){


            @Override
            protected String doInBackground(Void... voids) {
                String result = "";
                try {
                    OkHttpClient client = new OkHttpClient();

                    // Replace with your server URL
                    Request request = new Request.Builder()
                            .url("http://reslatur.org.in/drsbook/showBooks.php")
                            .build();

                    // Synchronous network call to fetch the data
                    Response response = client.newCall(request).execute();

                    if (response.isSuccessful() && response.body() != null) {
                        result = response.body().string();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return result;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                try {
                    // Parse the JSON result
                    JSONArray jsonArray = new JSONArray(s);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String bookName = jsonObject.getString("bname");
                        String bookImage = jsonObject.getString("imagepath");
                        // Add book to the list
                        Toast.makeText(MainActivity2.this, "Image Path:" + bookImage, Toast.LENGTH_SHORT).show();
                        bookList.add(new Book(bookName, bookImage));
                    }
                    // Notify adapter that data has changed to update UI
                    adapter.notifyDataSetChanged();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.execute();

    }
}