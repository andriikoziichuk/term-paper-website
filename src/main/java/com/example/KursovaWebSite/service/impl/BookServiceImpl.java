package com.example.KursovaWebSite.service.impl;

import com.example.KursovaWebSite.repositories.BookRepository;
import com.example.KursovaWebSite.models.book.Book;
import com.example.KursovaWebSite.models.user.User;
import com.example.KursovaWebSite.dto.BookDTO;
import com.example.KursovaWebSite.mapper.BookMapper;
import com.example.KursovaWebSite.service.BookService;
import com.example.KursovaWebSite.service.BucketService;
import com.example.KursovaWebSite.service.UserService;
import lombok.Getter;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Getter
public class BookServiceImpl implements BookService {

    private final BookMapper mapper = BookMapper.MAPPER;

    private final BookRepository bookRepository;
    private final UserService userService;
    private final BucketService bucketService;

    public BookServiceImpl(BookRepository bookRepository, UserService userService, BucketService bucketService) {
        this.bookRepository = bookRepository;
        this.userService = userService;
        this.bucketService = bucketService;
    }

    @Override
    public List<BookDTO> getAll() {
        return mapper.fromBookList((List<Book>) bookRepository.findAll());
    }

    @Override
    public List<BookDTO> getByName(String title) {
        return mapper.fromBookList(bookRepository.findByTitle(title));
    }

    @Override
    public void addToUserBucket(Long productId, String username) {

        User user = userService.findByUsername(username);

        if (user == null)
            throw new RuntimeException("User not found: " + username);

//        Bucket bucket = user.getBucket();
//
//        if (bucket == null) {
//            Bucket bucket1 = bucketService.createBucket(user, Collections.singletonList(productId));
//            user.setBucket(bucket1);
//            userService.save(user);
//        }
//        else {
//            bucketService.addBook(bucket, Collections.singletonList(productId));
//        }
    }

    @Override
    public void addToUserLiked(Long productId, String username) {
        User user = userService.findByUsername(username);

        if (user == null)
            throw new RuntimeException("User not found: " + username);

    }

    @Override
    public void delFromUserBucket(Long productId, String username) {
        User user = userService.findByUsername(username);

        if (user == null)
            throw new RuntimeException("User not found: " + username);

//        Bucket bucket = user.getBucket();
//
//        if (bucket != null) {
//            bucketService.removeBook(bucket, Collections.singletonList(productId));
//        }
    }
}
