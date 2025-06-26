package com.example.redisson_test.controllers;

import com.example.redisson_test.model.dto.AuthorDto;
import com.example.redisson_test.model.service.IAuthorService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/authors")
public class AuthorController
{
    private final IAuthorService authorService;

    public AuthorController(IAuthorService authorService)
    {
        this.authorService = authorService;
    }

    @GetMapping("/{id}")
    public AuthorDto getAuthorById(@PathVariable Integer id)
    {
        return authorService.findById(id);
    }

    @PostMapping
    public void addAuthor(@RequestBody AuthorDto author)
    {
        authorService.save(author);
    }

    @DeleteMapping("/{id}")
    public void deleteAuthor(@PathVariable Integer id)
    {
        authorService.deleteById(id);
    }
}
