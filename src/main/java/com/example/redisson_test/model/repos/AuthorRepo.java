package com.example.redisson_test.model.repos;

import com.example.redisson_test.model.entity.Author;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AuthorRepo extends JpaRepository<Author, Integer>
{
    Page<Author> findAllByOrderByFirstName(Pageable pageable);
    Page<Author> findAllByOrderByFirstNameDesc(Pageable pageable);

    @Query("SELECT a FROM Author a WHERE a.firstName ILIKE %:substring%")
    List<Author> findByFirstNameILike(@Param("substring") String substring);
}