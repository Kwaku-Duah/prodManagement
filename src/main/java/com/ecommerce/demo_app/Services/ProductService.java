package com.ecommerce.demo_app.Services;

import com.ecommerce.demo_app.Models.Product;
import com.ecommerce.demo_app.Repository.ProductRepository;
import com.ecommerce.demo_app.Utils.BinaryTree;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public Page<Product> findAll(int page, int size, String sortField, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() : Sort.by(sortField).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        return productRepository.findAll(pageable);
    }

    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    public Product save(Product product) {
        return productRepository.save(product);
    }

    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }

    public Page<Product> searchProducts(String keyword, int page, int size, String sortField, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() : Sort.by(sortField).descending();
        Pageable pageable = PageRequest.of(page, size, sort);

        List<Product> allProducts = productRepository.findAll();
        BinaryTree binaryTree = new BinaryTree(allProducts);
        List<Product> filteredProducts = binaryTree.search(keyword);

        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), filteredProducts.size());
        List<Product> paginatedList = filteredProducts.subList(start, end);

        return new PageImpl<>(paginatedList, pageable, filteredProducts.size());
    }

    public List<Product> findProductsByPriceGreaterThan(double price) {
        return productRepository.findProductsByPriceGreaterThan(price);
    }
}
