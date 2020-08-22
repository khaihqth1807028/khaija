package heleyquin.service;

import heleyquin.entity.Product;

import java.util.List;

public interface ProductService {
    Iterable<Product> getAll(int page, int limit);
    Product getProductById(int id);
    Product save(Product product);
    boolean update(Product product, int id);
    boolean delete(int id);
}
