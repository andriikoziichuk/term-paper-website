package com.example.KursovaWebSite.controllers;

import com.example.KursovaWebSite.dtos.BookDTO;
import com.example.KursovaWebSite.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;

@Controller
public class HomeController {

    private final BookService bookService;

    @Autowired
    public HomeController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("")
    public String index(Model model, Principal principal) {

        if (principal != null)
            model.addAttribute("books", bookService.getFavouriteBooks(principal.getName()));

        model.addAttribute("books", bookService.getAll());

        return "index";
    }

    @GetMapping("/{id}/liked")
    public String addLiked(@PathVariable Long id, Principal principal) {
        if (principal == null)
            return "redirect:/";

        bookService.addToUserLiked(id, principal.getName());
        return "redirect:/";
    }

    @GetMapping("/search-result")
    public String searchProducts(@RequestParam("keyword") String keyword,
                                 Model model) {

        List<BookDTO> books = bookService.searchProduct(keyword);

        model.addAttribute("books", books);

        return "result";
    }
}
