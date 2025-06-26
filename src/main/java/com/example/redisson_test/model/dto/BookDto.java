package com.example.redisson_test.model.dto;

public record BookDto (
        Integer id,
        String title,
        String description,
        Integer authorId
){}
