package com.example.bookshopapp;

public class Book {
    private String bookName;
    private String bookImage;

    public Book(String bookName, String bookImage) {
        this.bookName = bookName;
        this.bookImage = bookImage;
    }

    public String getBookName() {
        return bookName;
    }

    public String getBookImage() {
        return bookImage;
    }
}
