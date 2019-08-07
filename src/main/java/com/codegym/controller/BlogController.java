package com.codegym.controller;

import com.codegym.model.Blog;
import com.codegym.model.Category;
import com.codegym.service.BlogService;
import com.codegym.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
public class BlogController {

    @Autowired
    private BlogService blogService;

    @Autowired
    private CategoryService categoryService;

    @ModelAttribute("categories")
    public Iterable<Category> categories() {
        return categoryService.findAll();
    }

    @GetMapping("/blogs")
    public ModelAndView listBlog(@PageableDefault(size = 10, sort = "dateTime") Pageable pageable, @RequestParam("s") Optional<String> s) {
        Page<Blog> blogs;
        if (s.isPresent()) {
            blogs = blogService.findAllByNameStartsWith(s.get(), pageable);
        } else {
            blogs = blogService.findAll(pageable);
        }
        return new ModelAndView("blog/list", "blogs", blogs);
    }


//    @GetMapping("/blogs")
//    public ModelAndView listBlog(Pageable pageable) {
//        Page<Blog> blogs = blogService.findAll(pageable);
//        return new ModelAndView("blog/list", "blogs", blogs);
//    }

    @GetMapping("/create-blog")
    public ModelAndView showCreateForm() {
        return new ModelAndView("blog/create", "blog", new Blog());
    }

    @PostMapping("/create-blog")
    public ModelAndView saveBlog(@ModelAttribute Blog blog) {
        blogService.save(blog);
        ModelAndView modelAndView = new ModelAndView("blog/create", "blog", new Blog());
        modelAndView.addObject("message", "New blog created successfully");
        return modelAndView;
    }

    @GetMapping("/edit-blog/{id}")
    public ModelAndView showEditForm(@PathVariable Long id) {
        return new ModelAndView("blog/edit", "blog", blogService.findById(id));
    }

    @PostMapping("/edit-blog")
    public ModelAndView updateBlog(@ModelAttribute Blog blog) {
        blogService.save(blog);
        ModelAndView modelAndView = new ModelAndView("redirect:/blogs", "blog", blog);
        modelAndView.addObject("message", "Blog Update successfully");
        return modelAndView;
    }

    @GetMapping("/delete-blog/{id}")
    public ModelAndView showDeleteForm(@PathVariable Long id) {
        return new ModelAndView("blog/delete", "blog", blogService.findById(id));
    }

    @PostMapping("/delete-blog")
    public ModelAndView removeBlog(@ModelAttribute Blog blog) {
        blogService.remove(blog.getId());
        return new ModelAndView("redirect:/blogs");
    }
}
