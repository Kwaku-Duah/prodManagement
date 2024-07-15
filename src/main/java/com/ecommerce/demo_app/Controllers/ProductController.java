package com.ecommerce.demo_app.Controllers;

import com.ecommerce.demo_app.Models.Category;
import com.ecommerce.demo_app.Models.Product;
import com.ecommerce.demo_app.Services.CategoryService;
import com.ecommerce.demo_app.Services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    
    /** 
     * @param model
     * @return String
     */
    @GetMapping
    public String getAllProducts(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size,
        @RequestParam(defaultValue = "id") String sortField,
        @RequestParam(defaultValue = "asc") String sortDirection,
        @RequestParam(required = false) String keyword,
        Model model) {

        Page<Product> productPage;
        if (keyword != null && !keyword.isEmpty()) {
            productPage = productService.searchProducts(keyword, page, size, sortField, sortDirection);
            model.addAttribute("keyword", keyword);
        } else {
            productPage = productService.findAll(page, size, sortField, sortDirection);
        }

        model.addAttribute("products", productPage.getContent());
        model.addAttribute("currentPage", productPage.getNumber());
        model.addAttribute("totalPages", productPage.getTotalPages());
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDirection", sortDirection);

        return "product/list";
    }

    @GetMapping("/add")
    public String showAddProductForm(Model model) {
        model.addAttribute("product", new Product());
        model.addAttribute("categories", categoryService.findAll());
        return "product/add";
    }

    @GetMapping("/category/{id}")
    public String getProductsByCategory(@PathVariable Long id, Model model) {
        Category category = categoryService.findById(id);
        if (category != null) {
            List<Product> products = category.getProducts();
            model.addAttribute("products", products);
            model.addAttribute("category", category);
            return "product/list";
        }
        return "redirect:/categories";
    }

    @GetMapping("/edit/{id}")
    public String showEditProductForm(@PathVariable Long id, Model model) {
        Optional<Product> product = productService.findById(id);
        if (product.isPresent()) {
            model.addAttribute("product", product.get());
            model.addAttribute("categories", categoryService.findAll());
            return "product/edit";
        }
        return "redirect:/products";
    }

    @PostMapping
    public String addProduct(@ModelAttribute Product product, @RequestParam Long categoryId) {
        Category category = categoryService.findById(categoryId);
        if (category != null) {
            product.setCategory(category);
            productService.save(product);
        }
        return "redirect:/products";
    }

    @PostMapping("/edit/{id}")
    public String editProduct(@PathVariable Long id, @ModelAttribute Product product, @RequestParam Long categoryId) {
        Category category = categoryService.findById(categoryId);
        if (category != null) {
            product.setId(id);
            product.setCategory(category);
            productService.save(product);
        }
        return "redirect:/products";
    }

    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable Long id) {
        productService.deleteById(id);
        return "redirect:/products";
    }

    @GetMapping("/search")
    public String searchProducts(@RequestParam String keyword, Model model) {
        return "redirect:/products?keyword=" + keyword;
    }
}
