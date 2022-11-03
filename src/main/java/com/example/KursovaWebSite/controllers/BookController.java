package com.example.KursovaWebSite.controllers;

import com.example.KursovaWebSite.dto.BookDTO;
import com.example.KursovaWebSite.service.BookService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public String list(Model model) {
        List<BookDTO> list = bookService.getAll();
        model.addAttribute("books", list);
        return "books";
    }

    @GetMapping("/{id}/bucket")
    public String addBucket(@PathVariable Long id, Principal principal) {
        if (principal == null)
            return "redirect:/books";

        bookService.addToUserBucket(id, principal.getName());
        return "redirect:/books";
    }
}
