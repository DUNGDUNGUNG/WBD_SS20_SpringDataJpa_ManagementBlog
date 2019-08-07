package com.codegym.controller;

import com.codegym.model.Blog;
import com.codegym.model.Category;
import com.codegym.service.BlogService;
import com.codegym.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private BlogService blogService;


    @GetMapping("/categories")
    public ModelAndView listCategory() {
        Iterable<Category> categories = categoryService.findAll();
        return new ModelAndView("category/list", "categories", categories);
    }

    @GetMapping("/create-category")
    public ModelAndView showCreateForm() {

        return new ModelAndView("category/create", "category", new Category());
    }

    @PostMapping("/create-category")
    public ModelAndView saveCategory(@ModelAttribute Category category) {
        categoryService.save(category);
        ModelAndView modelAndView = new ModelAndView("category/create", "category", new Category());
        modelAndView.addObject("message", "New category created successfully");
        return modelAndView;
    }

    @GetMapping("/edit-category/{id}")
    public ModelAndView showEditForm(@PathVariable Long id) {
        return new ModelAndView("category/edit", "category", categoryService.findById(id));
    }

    @PostMapping("/edit-category")
    public ModelAndView updateCategory(@ModelAttribute Category category) {
        categoryService.save(category);
        ModelAndView modelAndView = new ModelAndView("redirect:/categories", "category", category);
        modelAndView.addObject("message", "Category Update successfully");
        return modelAndView;
    }

    @GetMapping("/delete-category/{id}")
    public ModelAndView showDeleteForm(@PathVariable Long id) {
        return new ModelAndView("category/delete", "category", categoryService.findById(id));
    }

    @PostMapping("/delete-category")
    public ModelAndView removeCategory(@ModelAttribute Category category) {
        categoryService.remove(category.getId());
        return new ModelAndView("redirect:/categories");
    }

    @GetMapping("/view-category/{id}")
    public ModelAndView viewCategory(@PathVariable("id") Long id){
        Category category = categoryService.findById(id);
        Iterable<Blog> blogs = blogService.findAllByCategory(category);

        ModelAndView modelAndView = new ModelAndView("category/view");
        modelAndView.addObject("category", category);
        modelAndView.addObject("blogs", blogs);
        return modelAndView;
    }

}
