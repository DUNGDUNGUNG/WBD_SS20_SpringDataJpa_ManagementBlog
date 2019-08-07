package com.codegym.service;

import com.codegym.model.Blog;
import com.codegym.model.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BlogService {
    Iterable<Blog>findAllByCategory(Category category);

    Page<Blog>findAllByNameStartsWith(String name, Pageable pageable);

    Page<Blog>findAll(Pageable pageable);

    void save(Blog model);

    void remove(Long id);

    Blog findById(Long id);

}
