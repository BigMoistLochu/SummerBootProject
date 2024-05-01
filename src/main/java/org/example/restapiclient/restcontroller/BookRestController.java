package org.example.restapiclient.restcontroller;

import org.example.restapiclient.models.Book;
import org.example.restapiclient.services.BookService;
import org.example.tomekcat.annotations.rest.GetMapping;
import org.example.tomekcat.annotations.rest.PostMapping;
import org.example.tomekcat.annotations.rest.RestController;

import java.util.List;

@RestController
public class BookRestController {

    private final BookService bookService = new BookService();

    public BookRestController()
    {
        System.out.println("Klasa BookRestController zostala utworzona");
    }
    @GetMapping("/books")
    public List<Book> getAllBooksToUser()
    {
        return bookService.getAllBooks();
    }

    @PostMapping("/add/book")
    public void addBookToLibrary(Book book){bookService.addBookToLibrary(book);}


}
