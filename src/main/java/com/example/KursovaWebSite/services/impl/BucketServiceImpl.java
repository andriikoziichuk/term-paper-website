package com.example.KursovaWebSite.services.impl;

import com.example.KursovaWebSite.repositories.BookRepository;
import com.example.KursovaWebSite.repositories.BucketRepository;
import com.example.KursovaWebSite.models.book.Book;
import com.example.KursovaWebSite.models.user.Bucket;
import com.example.KursovaWebSite.models.user.User;
import com.example.KursovaWebSite.dtos.BucketDTO;
import com.example.KursovaWebSite.dtos.BucketDetailDTO;
import com.example.KursovaWebSite.services.BucketService;
import com.example.KursovaWebSite.services.UserService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class BucketServiceImpl implements BucketService {

    private final BucketRepository bucketRepository;
    private final BookRepository bookRepository;
    private final UserService userService;

    public BucketServiceImpl(BucketRepository bucketRepository, BookRepository bookRepository, UserService userService) {
        this.bucketRepository = bucketRepository;
        this.bookRepository = bookRepository;
        this.userService = userService;
    }

    @Override
    @Transactional
    public Bucket createBucket(User user, List<Long> bookIds) {
        Bucket bucket = new Bucket();
        bucket.setUser(user);
        List<Book> bookList = getCollectRefBooksByIds(bookIds);
        bucket.setBooks(bookList);
        return bucketRepository.save(bucket);
    }

    private List<Book> getCollectRefBooksByIds(List<Long> bookIds) {
        return bookIds.stream()
                .map(bookRepository::getReferenceById)
                .collect(Collectors.toList());
    }

    @Override
    public void addBook(Bucket bucket, List<Long> bookIds) {

        List<Book> books = bucket.getBooks();
        List<Book> newBooks = books == null ? new ArrayList<>() : new ArrayList<>(books);
        newBooks.addAll(getCollectRefBooksByIds(bookIds));
        bucket.setBooks(newBooks);
        bucketRepository.save(bucket);
    }

    @Override
    public BucketDTO getBucketByUser(String email) {
        Optional<User> user = userService.findByEmail(email);

        if (!user.isPresent() || user.get().getBucket() == null)
            return new BucketDTO();

        BucketDTO bucketDTO = new BucketDTO();
        Map<Long, BucketDetailDTO> mapByBookId = new HashMap<>();

        List<Book> books = user.get().getBucket().getBooks();
        for (Book book: books) {
            BucketDetailDTO detail = mapByBookId.get(book.getId());

            if (detail == null)
                mapByBookId.put(book.getId(), new BucketDetailDTO(book));
            else {
                detail.setAmount(detail.getAmount() + 1.0);
                detail.setSum(detail.getSum() + 1.0);
            }
        }
        bucketDTO.setBucketDetails(new ArrayList<>(mapByBookId.values()));
        bucketDTO.aggregate();

        return bucketDTO;
    }

    @Override
    public void removeBook(Bucket bucket, List<Long> bookIds) {

        List<Book> books = bucket.getBooks();
        List<Book> newBooks = books == null ? new ArrayList<>() : new ArrayList<>(books);
        newBooks.removeAll(getCollectRefBooksByIds(bookIds));
        bucket.setBooks(newBooks);
        bucketRepository.save(bucket);
    }
}
