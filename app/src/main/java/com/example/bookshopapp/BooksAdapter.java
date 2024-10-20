package com.example.bookshopapp;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

public class BooksAdapter extends BaseAdapter {

    private Activity context;
    private List<Book> books;

    public BooksAdapter(Activity context, List<Book> books) {
        this.context = context;
        this.books = books;
    }

    @Override
    public int getCount() {
        return books.size();
    }

    @Override
    public Object getItem(int i) {
        return books.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view1 = inflater.inflate(R.layout.list_item, null, true);


        TextView bookName = view1.findViewById(R.id.bookName);
        ImageView bookImage = view1.findViewById(R.id.bookImage);

        Book book = books.get(i);

        bookName.setText(book.getBookName());

        // Use Glide or Picasso to load the image from URL
        Glide.with(context)
                .load(book.getBookImage())
                .into(bookImage);

        return view1;
    }
}
