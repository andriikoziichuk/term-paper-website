package com.example.KursovaWebSite.controllers;

import com.example.KursovaWebSite.models.book.Book;
import com.example.KursovaWebSite.models.user.User;
import com.example.KursovaWebSite.services.AuthorService;
import com.example.KursovaWebSite.services.BookService;
import com.example.KursovaWebSite.services.GenreService;
import com.example.KursovaWebSite.services.UserService;
import com.example.KursovaWebSite.utils.BookValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.security.Principal;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final AuthorService authorService;
    private final BookService bookService;
    private final GenreService genreService;
    private final BookValidator bookValidator;

    @Autowired
    public AdminController(UserService userService, AuthorService authorService, BookService bookService, GenreService genreService, BookValidator bookValidator) {
        this.userService = userService;
        this.authorService = authorService;
        this.bookService = bookService;
        this.genreService = genreService;
        this.bookValidator = bookValidator;
    }

    @GetMapping("/users")
    public String userList(Model model) {
        model.addAttribute("users", userService.getAll());
        return "admin/user-list";
    }

    @GetMapping("/user/{user}")
    public String editUser(Model model,
                           @PathVariable("user") User user) {
        model.addAttribute("user", user);
        return "admin/user-id";
    }

    @PostMapping("/user")
    public String performEditUser(@ModelAttribute("user") @Valid User user,
                              BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "/admin/user-id";

        userService.update(user);
        return "redirect:/admin/users";
    }

    @GetMapping("/book/{book}")
    public String editBook(Model model,
                           @PathVariable("book") Book book) {

        model.addAttribute("book", book);
        model.addAttribute("genres", genreService.findAll());
        model.addAttribute("authors", authorService.findAll());
        return "/admin/book-edit";
    }

    @PostMapping("/book")
    public String performEditBook(@ModelAttribute("book") @Valid Book book,
                                  BindingResult bindingResult,
                                  @RequestParam("image") MultipartFile image,
                                  Principal principal) {

        if (principal == null)
            throw new RuntimeException("You are not authorize");

        System.out.println("BEFORE VALIDATE");

        bookValidator.validate(book, bindingResult);

        System.out.println("AFTER VALIDATE");
//      TODO: remake user update function
//        if (bindingResult.hasErrors())
//            return "/admin/book-edit";

        System.out.println("BEFORE UPDATE");

        try {
            bookService.update(book, image);
        } catch (RuntimeException e) {
            return "/admin/book-edit";
        }

        System.out.println("AFTER UPDATE");

        return "redirect:/";
    }

    @GetMapping("/block/{id}")
    public String block(@PathVariable("id") Long id) {
        userService.block(id);

        return "redirect:/admin/users";
    }

    @GetMapping("/unblock/{id}")
    public String unblock(@PathVariable("id") Long id) {
        userService.unblock(id);

        return "redirect:/admin/users";
    }
}
