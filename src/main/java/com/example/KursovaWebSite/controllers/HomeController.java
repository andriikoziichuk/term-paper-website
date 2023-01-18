package com.example.KursovaWebSite.controllers;

import com.example.KursovaWebSite.dto.BookDTO;
import com.example.KursovaWebSite.repositories.BookRepository;
import com.example.KursovaWebSite.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
public class HomeController {

    private final BookService bookService;

    @Autowired
    public HomeController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("")
    public String index(Model model) {

        model.addAttribute("books", bookService.getAll());

        return "index";
    }

    @GetMapping("/list")
    public String list(@RequestParam Optional<String> title, Model model, Principal principal) {

        if (principal != null) {
//            LikedDTO likedDTO = likedService.getLikedByUser(principal.getName());
//            model.addAttribute("liked", likedDTO.getLikedBooks());
        }
        List<BookDTO> list = bookService.getByName(title.orElse("_"));
        model.addAttribute("books", list);
        return "index";
    }

    @GetMapping("/{id}/bucket")
    public String addBucket(@PathVariable Long id, Principal principal) {
        if (principal == null)
            return "redirect:/";

        bookService.addToUserBucket(id, principal.getName());
        return "redirect:/";
    }

    @GetMapping("/{id}/removeBucket")
    public String removeBucket(@PathVariable Long id, Principal principal) {
        if (principal == null)
            return "redirect:/bucket";

        bookService.delFromUserBucket(id, principal.getName());
        return "redirect:/bucket";
    }

    @GetMapping("/{id}/liked")
    public String addLiked(@PathVariable Long id, Principal principal) {
        if (principal == null)
            return "redirect:/";

        bookService.addToUserLiked(id, principal.getName());
        return "redirect:/";
    }
}
