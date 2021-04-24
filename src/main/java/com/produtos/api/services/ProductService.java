package com.produtos.api.services;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.produtos.api.model.Product;
import com.produtos.api.repository.ProductCustomRepository;
import com.produtos.api.repository.ProductRepository;

@Service
public class ProductService {

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	ProductCustomRepository productCustomRepository;

	public List<Product> findAll() {
		return productRepository.findAll();
	}

	public Optional<Product> findById(Long id) {
		return productRepository.findById(id);
	}

	public Product create(Product product) {
		return productRepository.save(product);
	}

	public void delete(Long id) {
		productRepository.deleteById(id);
	}

	public Product update(Product product) {
		Product productObj = productRepository.findById(product.getId()).get();
		updateData(productObj, product);
		return productRepository.save(productObj);
	}

	public List<Product> getSearch(String q, BigDecimal min_price, BigDecimal max_price) {
		return productCustomRepository.findSearch(q, min_price, max_price);
	}

	private void updateData(Product productObj, Product product) {
		productObj.setName(product.getName());
		productObj.setDescription(product.getDescription());
		productObj.setPrice(product.getPrice());

	}
}