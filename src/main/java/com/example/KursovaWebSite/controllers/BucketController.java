package com.example.KursovaWebSite.controllers;

import com.example.KursovaWebSite.dtos.BucketDTO;
import com.example.KursovaWebSite.services.BookService;
import com.example.KursovaWebSite.services.BucketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/bucket")
public class BucketController {

    private final BucketService bucketService;
    private final BookService bookService;

    @Autowired
    public BucketController(BucketService bucketService, BookService bookService) {
        this.bucketService = bucketService;
        this.bookService = bookService;
    }

    @GetMapping
    public String bucket(Model model, Principal principal) {

        if (principal != null) {
            BucketDTO bucketDTO = bucketService.getBucketByUser(principal.getName());
            model.addAttribute("bucket", bucketDTO);
        } else
            throw new RuntimeException("Login or Register please");

        return "/bucket/bucket";
    }

    @GetMapping("/add/{id}")
    public String addFromBucket(@PathVariable Long id, Principal principal) {
        if (principal == null)
            return "redirect:/";

        bookService.addToUserBucket(id, principal.getName());
        return "redirect:/";
    }

    @GetMapping("/remove/{id}")
    public String removeFromBucket(@PathVariable Long id, Principal principal) {
        if (principal == null)
            return "redirect:/bucket";

        bookService.delFromUserBucket(id, principal.getName());
        return "redirect:/bucket";
    }
}
