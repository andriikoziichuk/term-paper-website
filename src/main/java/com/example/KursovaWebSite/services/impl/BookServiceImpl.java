package com.example.KursovaWebSite.services.impl;

import com.example.KursovaWebSite.models.user.Bucket;
import com.example.KursovaWebSite.repositories.BookRepository;
import com.example.KursovaWebSite.models.book.Book;
import com.example.KursovaWebSite.models.user.User;
import com.example.KursovaWebSite.dtos.BookDTO;
import com.example.KursovaWebSite.mapper.BookMapper;
import com.example.KursovaWebSite.services.BookService;
import com.example.KursovaWebSite.services.BucketService;
import com.example.KursovaWebSite.services.UserService;
import com.example.KursovaWebSite.utils.ImageUploader;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {

    private final BookMapper mapper = BookMapper.MAPPER;

    private final BookRepository bookRepository;
    private final UserService userService;
    private final BucketService bucketService;
    private final ImageUploader imageUploader;

    public BookServiceImpl(BookRepository bookRepository, UserService userService, BucketService bucketService, ImageUploader imageUploader) {
        this.bookRepository = bookRepository;
        this.userService = userService;
        this.bucketService = bucketService;
        this.imageUploader = imageUploader;
    }

    @Override
    public List<BookDTO> getAll() {
        return mapper.fromBookList(bookRepository.findAll());
    }

    @Override
    public Optional<Book> findById(Long id) {
        return bookRepository.findById(id);
    }

    @Override
    public List<BookDTO> getFavouriteBooks(String email) {

        Optional<User> byEmail = userService.findByEmail(email);

        if (!byEmail.isPresent())
            throw new RuntimeException("User not found");

        return mapper.fromBookList(byEmail.get().getLiked());
    }

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void update(Book book, MultipartFile image) {
        // TODO:
        try {
            System.out.println("BOOK ID: " + book.getId());
            Optional<Book> bookExists = findById(book.getId());

            if (!bookExists.isPresent()) {
                throw new RuntimeException("User not found by email: " + book.getTitle());
            }
//
//            if (image == null)
//                bookExists.get().setImage(book.getImage());
//            else {
//                if (!imageUploader.checkExisted(image)) {
//                    imageUploader.uploadImage(image);
//                }
//                bookExists.get().setImage(Base64.getEncoder().encodeToString(image.getBytes()));
//            }
            try {
                byte [] byteImage = ImageUploader.ImageToByte(new File(image.getOriginalFilename()));
                bookExists.get().setImage(byteImage);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }


            bookExists.get().setTitle(book.getTitle());
            bookExists.get().setDescription(book.getDescription());
            bookExists.get().setAuthor(book.getAuthor());
            bookExists.get().setGenre(book.getGenre());
            bookExists.get().setPrice(book.getPrice());

            bookRepository.save(bookExists.get());

        } catch (Exception e) {
            throw new RuntimeException("Something went wrong");
        }
    }

    @Override
    public List<BookDTO> searchProduct(String keyword) {
        return mapper.fromBookList(bookRepository.searchProduct(keyword));
    }

    @Override
    public void addToUserBucket(Long productId, String email) {

        Optional<User> user = userService.findByEmail(email);

        if (!user.isPresent())
            throw new RuntimeException("User not found: " + email);

        Bucket bucket = user.get().getBucket();

        if (bucket == null) {
            Bucket bucket1 = bucketService.createBucket(user.get(), Collections.singletonList(productId));
            user.get().setBucket(bucket1);
            userService.save(user.get());
        } else {
            bucketService.addBook(bucket, Collections.singletonList(productId));
        }
    }

    @Override
    public void addToUserLiked(Long productId, String username) {
        User user = userService.findByUsername(username);

        if (user == null)
            throw new RuntimeException("User not found: " + username);

    }

    @Override
    public void delFromUserBucket(Long productId, String email) {
        Optional<User> user = userService.findByEmail(email);

        if (!user.isPresent())
            throw new RuntimeException("User not found: " + email);

        Bucket bucket = user.get().getBucket();

        if (bucket != null) {
            bucketService.removeBook(bucket, Collections.singletonList(productId));
        }
    }
}
