package com.example.redisson_test.model.service;

import com.example.redisson_test.model.dto.AuthorDto;
import com.example.redisson_test.model.entity.Author;
import com.example.redisson_test.model.repos.AuthorRepo;
import com.example.redisson_test.model.utils.DtoMapper;
import org.springframework.cache.annotation.*;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthorService implements IAuthorService
{
    private final AuthorRepo authorRepo;
    private final DtoMapper dtoMapper;

    public AuthorService(AuthorRepo authorRepo, DtoMapper dtoMapper)
    {
        this.authorRepo = authorRepo;
        this.dtoMapper = dtoMapper;
    }

    @Cacheable(value = "author", key = "#id")
    @Override
    public AuthorDto findById(Integer id)
    {
        Author result = authorRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cannot find author with id=" + id));

        return dtoMapper.toDto(result);
    }

    @CachePut(value = "author", key = "#author.id")
    @Override
    public void save(AuthorDto author)
    {
        authorRepo.save(dtoMapper.toEntity(author));
    }

    @CacheEvict(value = "author", key = "#id")
    @Override
    public void deleteById(Integer id)
    {
        authorRepo.deleteById(id);
    }
}
