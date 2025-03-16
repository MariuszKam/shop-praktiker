package com.practice.shop.entities.product;

public class Book extends Product {

    private String author;
    private int isbn;

    public Book(int id, String name, float price, String author, int isbn) {
        super(id, name, price);
        this.author = author;
        this.isbn = isbn;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getIsbn() {
        return isbn;
    }

    public void setIsbn(int isbn) {
        this.isbn = isbn;
    }
}
