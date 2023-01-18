package com.example.KursovaWebSite.controllers;

import com.example.KursovaWebSite.repositories.BookRepository;
import com.example.KursovaWebSite.models.book.Book;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.Optional;

@Controller
@RequestMapping
public class BookController {

    private final BookRepository bookRepository;

    public BookController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @GetMapping("/book/{id}")
    public String bookDetails(@PathVariable(value = "id") Long id, Model model) {
        Optional<Book> book = bookRepository.findById(id);
        ArrayList<Book> books = new ArrayList<>();
        book.ifPresent(books::add);
        model.addAttribute("books", books);
        return "book-details";
    }
}
