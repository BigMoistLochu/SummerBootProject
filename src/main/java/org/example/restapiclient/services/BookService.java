package org.example.restapiclient.services;

import org.example.restapiclient.models.Book;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class BookService {

    private List<Book> library = new ArrayList<>();

    public BookService()
    {
        library.add(new Book("Wzorce Projektowe Java","Eric Freeman", BigDecimal.valueOf(57.32)));
        library.add(new Book("Java Podstawy","Jessica Friko", BigDecimal.valueOf(27.50)));
    }

    public List<Book> getAllBooks()
    {
        return library;
    }

    public void addBookToLibrary(Book new_book)
    {
        library.add(new_book);
    }





}
