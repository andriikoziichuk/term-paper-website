package com.example.KursovaWebSite.utils;

import com.example.KursovaWebSite.models.book.Book;
import com.example.KursovaWebSite.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Optional;

@Component
public class BookValidator implements Validator {

    private final BookService bookService;

    @Autowired
    public BookValidator(BookService bookService) {
        this.bookService = bookService;
    }

    @Override
    public boolean supports(Class<?> clas) {
        return Book.class.equals(clas);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Book book = (Book) target;

        Optional<Book> bookOptional = bookService.findById(book.getId());

        if (bookOptional.isPresent() && bookOptional.get().getPrice() < 0)
            errors.rejectValue("price", "", "Price cannot be lower than 0");
    }
}
