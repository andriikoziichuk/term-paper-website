package com.example.KursovaWebSite.service;

import com.example.KursovaWebSite.dao.BookRepository;
import com.example.KursovaWebSite.dao.BucketRepository;
import com.example.KursovaWebSite.domain.book.Book;
import com.example.KursovaWebSite.domain.entity.Bucket;
import com.example.KursovaWebSite.domain.entity.User;
import com.example.KursovaWebSite.dto.BucketDTO;
import com.example.KursovaWebSite.dto.BucketDetailDTO;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class BucketServiceImpl implements BucketService{

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
    public BucketDTO getBucketByUser(String username) {
        User user = userService.findByUsername(username);

        if (user == null || user.getBucket() == null)
            return new BucketDTO();

        BucketDTO bucketDTO = new BucketDTO();
        Map<Long, BucketDetailDTO> mapByBookId = new HashMap<>();

        List<Book> books = user.getBucket().getBooks();
        for (Book book: books) {
            BucketDetailDTO detail = mapByBookId.get(book.getId());

            if (detail == null)
                mapByBookId.put(book.getId(), new BucketDetailDTO(book));
            else {
                detail.setAmount(detail.getAmount().add(new BigDecimal(1.0)));
                detail.setSum(detail.getSum() + Double.valueOf(book.getPrice().toString()));
            }
        }
        bucketDTO.setBucketDetails(new ArrayList<>(mapByBookId.values()));
        bucketDTO.aggregate();

        return bucketDTO;
    }
}
