package com.example.redisson_test.model.utils;

import com.example.redisson_test.model.dto.AuthorDto;
import com.example.redisson_test.model.dto.BookDto;
import com.example.redisson_test.model.entity.Author;
import com.example.redisson_test.model.entity.Book;

public interface IDtoMapper
{
    BookDto toDto(Book book);
    BookDto toDto(Book book, Integer authorId);
    Book toEntity(BookDto bookDto, Author author);
    AuthorDto toDto(Author author);
    Author toEntity(AuthorDto authorDto);
}