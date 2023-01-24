package com.example.KursovaWebSite.services;

import com.example.KursovaWebSite.models.user.Bucket;
import com.example.KursovaWebSite.models.user.User;
import com.example.KursovaWebSite.dtos.BucketDTO;

import java.util.List;

public interface BucketService {
    Bucket createBucket(User user, List<Long> bookIds);

    void addBook(Bucket bucket, List<Long> bookIds);

    BucketDTO getBucketByUser(String email);

    void removeBook(Bucket bucket, List<Long> bookIds);
}
