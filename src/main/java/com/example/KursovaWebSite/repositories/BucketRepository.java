package com.example.KursovaWebSite.repositories;

import com.example.KursovaWebSite.models.user.Bucket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BucketRepository extends JpaRepository<Bucket, Long> {

}
