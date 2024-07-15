package com.ecommerce.demo_app.Controllers;

import com.ecommerce.demo_app.Models.Category;
import com.ecommerce.demo_app.Services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    
    /**
     * Retrieves and displays all categories.
     *
     * @param model the model to hold categories data
     * @return the categories list view
     */
    @GetMapping
    public String getAllCategories(Model model) {
        model.addAttribute("categories", categoryService.findAll());
        return "category/list";
    }


     /**
     * Retrieves and displays products for a specific category.
     *
     * @param id the id of the category
     * @param model the model to hold category and products data
     * @return the category products view or redirect to categories list if category not found
     */

    @GetMapping("/{id}")
    public String getCategoryProducts(@PathVariable Long id, Model model) {
        Category category = categoryService.findById(id);
        if (category != null) {
            model.addAttribute("products", category.getProducts());
            model.addAttribute("category", category);
            return "category/products";
        }
        return "redirect:/categories";
    }
}
