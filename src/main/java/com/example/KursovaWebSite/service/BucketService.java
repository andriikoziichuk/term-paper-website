package com.example.KursovaWebSite.service;

import com.example.KursovaWebSite.models.user.Bucket;
import com.example.KursovaWebSite.models.user.User;
import com.example.KursovaWebSite.dto.BucketDTO;

import java.util.List;

public interface BucketService {
    Bucket createBucket(User user, List<Long> bookIds);

    void addBook(Bucket bucket, List<Long> bookIds);

    BucketDTO getBucketByUser(String username);

    void removeBook(Bucket bucket, List<Long> bookIds);
}
