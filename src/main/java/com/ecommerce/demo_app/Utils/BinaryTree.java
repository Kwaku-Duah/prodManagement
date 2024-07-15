package com.ecommerce.demo_app.Utils;

import com.ecommerce.demo_app.Models.Product;

import java.util.ArrayList;
import java.util.List;

public class BinaryTree {

    private Node root;

    /**
     * Constructs a binary tree and adds products to it.
     *
     * @param products the list of products to add to the tree
     */
    public BinaryTree(List<Product> products) {
        for (Product product : products) {
            add(product);
        }
    }

    private class Node {
        Product product;
        Node left, right;

        Node(Product product) {
            this.product = product;
            left = right = null;
        }
    }

    /**
     * Adds a product to the binary tree.
     *
     * @param product the product to add
     */
    private void add(Product product) {
        root = addRecursive(root, product);
    }

    private Node addRecursive(Node current, Product product) {
        if (current == null) {
            return new Node(product);
        }

        if (product.getName().compareToIgnoreCase(current.product.getName()) < 0) {
            current.left = addRecursive(current.left, product);
        } else if (product.getName().compareToIgnoreCase(current.product.getName()) > 0) {
            current.right = addRecursive(current.right, product);
        } else {
            return current;
        }

        return current;
    }

    /**
     * Searches for products in the binary tree that contain the specified keyword.
     *
     * @param keyword the keyword to search for
     * @return a list of products that match the keyword
     */
    public List<Product> search(String keyword) {
        List<Product> result = new ArrayList<>();
        searchRecursive(root, keyword.toLowerCase(), result);
        return result;
    }

    private void searchRecursive(Node current, String keyword, List<Product> result) {
        if (current != null) {
            if (current.product.getName().toLowerCase().contains(keyword) || current.product.getDescription().toLowerCase().contains(keyword)) {
                result.add(current.product);
            }
            searchRecursive(current.left, keyword, result);
            searchRecursive(current.right, keyword, result);
        }
    }
}
