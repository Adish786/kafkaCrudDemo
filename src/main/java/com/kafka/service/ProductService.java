package com.kafka.service;

import com.kafka.entity.Product;
import com.kafka.exceptionhandler.ProductNotFound;
import com.kafka.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {
    
    private final ProductRepository productRepository;
    private final KafkaProducerService kafkaProducerService;
    
    @Transactional
    public Product createProduct(Product product) {
        Product savedProduct = productRepository.save(product);
        log.info("Product created with ID: {}", savedProduct.getId());
        
        // Send Kafka event for CREATE operation
        kafkaProducerService.sendProductEvent(savedProduct, "CREATE");
        
        return savedProduct;
    }
    
    public Product getProductById(Long id) throws ProductNotFound {
        return productRepository.findById(id)
            .orElseThrow(() -> new ProductNotFound("Product not found with ID: " + id));
    }
    
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }
    
    @Transactional
    public Product updateProduct(Long id, Product productDetails) throws ProductNotFound {
        Product existingProduct = getProductById(id);
        
        existingProduct.setName(productDetails.getName());
        existingProduct.setDescription(productDetails.getDescription());
        existingProduct.setPrice(productDetails.getPrice());
        existingProduct.setQuantity(productDetails.getQuantity());
        
        Product updatedProduct = productRepository.save(existingProduct);
        log.info("Product updated with ID: {}", updatedProduct.getId());
        
        // Send Kafka event for UPDATE operation
        kafkaProducerService.sendProductEvent(updatedProduct, "UPDATE");
        
        return updatedProduct;
    }
    
    @Transactional
    public void deleteProduct(Long id) throws ProductNotFound {
        Product product = getProductById(id);
        productRepository.delete(product);
        log.info("Product deleted with ID: {}", id);
        
        // Send Kafka event for DELETE operation
        kafkaProducerService.sendProductEvent(product, "DELETE");
    }
    
    public List<Product> searchProductsByName(String name) {
        return productRepository.findByNameContainingIgnoreCase(name);
    }
    
    public List<Product> getProductsByPriceRange(Double minPrice, Double maxPrice) {
        return productRepository.findByPriceBetween(minPrice, maxPrice);
    }
}