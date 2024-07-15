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
     * Retrieves and displays a paginated list of all products.
     *
     * @param page the page number to retrieve
     * @param size the number of products per page
     * @param sortField the field to sort by
     * @param sortDirection the direction of sorting
     * @param keyword the keyword to search for
     * @param model the model to hold products data
     * @return the products list view
     */
    @GetMapping
    public String getAllProducts(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "5") int size,
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

    /**
     * Displays the form for adding a new product.
     *
     * @param model the model to hold product and categories data
     * @return the add product form view
     */
    @GetMapping("/add")
    public String showAddProductForm(Model model) {
        model.addAttribute("product", new Product());
        model.addAttribute("categories", categoryService.findAll());
        return "product/add";
    }

    /**
     * Retrieves and displays products by category.
     *
     * @param id the id of the category
     * @param model the model to hold category and products data
     * @return the products list view for the category
     */
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

    /**
     * Displays the form for editing a product.
     *
     * @param id the id of the product to edit
     * @param model the model to hold product and categories data
     * @return the edit product form view
     */
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

    /**
     * Handles adding a new product.
     *
     * @param product the product to add
     * @param categoryId the id of the category for the product
     * @return redirect to products list view
     */
    @PostMapping
    public String addProduct(@ModelAttribute Product product, @RequestParam Long categoryId) {
        Category category = categoryService.findById(categoryId);
        if (category != null) {
            product.setCategory(category);
            productService.save(product);
        }
        return "redirect:/products";
    }

    /**
     * Handles editing an existing product.
     *
     * @param id the id of the product to edit
     * @param product the updated product details
     * @param categoryId the id of the category for the product
     * @return redirect to products list view
     */
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

    /**
     * Handles deleting a product.
     *
     * @param id the id of the product to delete
     * @return redirect to products list view
     */
    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable Long id) {
        productService.deleteById(id);
        return "redirect:/products";
    }

    /**
     * Handles searching for products by keyword.
     *
     * @param keyword the keyword to search for
     * @param model the model to hold products data
     * @return redirect to products list view with search results
     */
    @GetMapping("/search")
    public String searchProducts(@RequestParam String keyword, Model model) {
        return "redirect:/products?keyword=" + keyword;
    }
}
