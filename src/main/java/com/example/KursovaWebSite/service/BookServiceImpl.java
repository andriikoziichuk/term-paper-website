package com.example.KursovaWebSite.service;

import com.example.KursovaWebSite.dao.BookRepository;
import com.example.KursovaWebSite.domain.book.Book;
import com.example.KursovaWebSite.domain.entity.Bucket;
import com.example.KursovaWebSite.domain.entity.User;
import com.example.KursovaWebSite.dto.BookDTO;
import com.example.KursovaWebSite.mapper.BookMapper;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class BookServiceImpl implements BookService{

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
    public void addToUserBucket(Long productId, String username) {

        User user = userService.findByUsername(username);

        if (user == null)
            throw new RuntimeException("User not found: " + username);

        Bucket bucket = user.getBucket();

        if (bucket == null) {
            Bucket bucket1 = bucketService.createBucket(user, Collections.singletonList(productId));
            user.setBucket(bucket1);
            userService.save(user);
        }
        else {
            bucketService.addBook(bucket, Collections.singletonList(productId));
        }
    }
}
