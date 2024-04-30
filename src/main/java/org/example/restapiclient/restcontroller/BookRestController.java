package org.example.restapiclient.restcontroller;

import org.example.restapiclient.models.Book;
import org.example.restapiclient.services.BookService;
import org.example.tomekcat.annotations.rest.GetMapping;
import org.example.tomekcat.annotations.rest.RestController;

import java.util.List;

@RestController
public class BookRestController {

    private final BookService bookService = new BookService();

    public BookRestController()
    {
        System.out.println("ELo");
    }
    @GetMapping("/books")
    public List<Book> getAllBooksToUser()
    {
        return bookService.getAllBooks();
    }


}
