package com.example.KursovaWebSite.service;

import com.example.KursovaWebSite.domain.entity.Bucket;
import com.example.KursovaWebSite.domain.entity.User;
import com.example.KursovaWebSite.dto.BucketDTO;

import java.util.List;

public interface BucketService {
    Bucket createBucket(User user, List<Long> bookIds);

    void addBook(Bucket bucket, List<Long> bookIds);

    BucketDTO getBucketByUser(String username);
}
