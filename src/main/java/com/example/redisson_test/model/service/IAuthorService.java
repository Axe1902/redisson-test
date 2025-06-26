package com.example.redisson_test.model.service;

import com.example.redisson_test.model.dto.AuthorDto;
import com.example.redisson_test.model.entity.Author;

import java.util.Optional;

public interface IAuthorService
{
    AuthorDto findById(Integer id);
    void save(AuthorDto author);
    void deleteById(Integer id);
}