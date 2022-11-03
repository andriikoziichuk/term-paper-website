package com.example.KursovaWebSite.dao;

import com.example.KursovaWebSite.domain.entity.Bucket;
import com.example.KursovaWebSite.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BucketRepository extends JpaRepository<Bucket, Long> {

}
