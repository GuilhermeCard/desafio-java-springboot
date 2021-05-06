package com.produtos.api.controller;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.produtos.api.controller.util.URL;
import com.produtos.api.model.Product;
import com.produtos.api.services.ProductService;

import io.swagger.annotations.ApiOperation;

@RestController
public class ProductController {

	@Autowired
	ProductService productService;

	@ApiOperation(value = "Criação de um produto")
	@PostMapping("/products")
	public ResponseEntity<Object> createProduct(@RequestBody Product product) {
		productService.create(product);
		return new ResponseEntity<>(product, HttpStatus.CREATED);

	}

	@ApiOperation(value = "Atualização de um produto")
	@PutMapping("/products/{id}")
	public ResponseEntity<Object> updateProduct(@PathVariable("id") Long id, @RequestBody Product product) {
		product.setId(id);
		productService.update(product);
		return new ResponseEntity<>(product, HttpStatus.OK);
	}

	@ApiOperation(value = "Busca de um produto por ID")
	@GetMapping("/products/{id}")
	public ResponseEntity<Product> getProductById(@PathVariable("id") Long id) {
		Product obj = productService.findById(id).get();
		return ResponseEntity.ok().body(obj);
	}

	@ApiOperation(value = "Lista de produtos")
	@GetMapping("/products")
	public ResponseEntity<List<Product>> getAll() {
		List<Product> products = productService.findAll();
		return ResponseEntity.ok().body(products);
	}

	@ApiOperation(value = "Lista de produtos filtrados")
	@GetMapping("/products/search")
	public ResponseEntity<List<Product>> getProductByFilter(
			@RequestParam(value = "nameOrDescription", defaultValue = "") String q,
			@RequestParam(value = "minPrice", required = false) BigDecimal minPrice,
			@RequestParam(value = "maxPrice", required = false) BigDecimal maxPrice) {

		q = URL.decodeParam(q);

		List<Product> products = productService.getSearch(q, minPrice, maxPrice);
		return ResponseEntity.ok().body(products);
	}

	@ApiOperation(value = "Deleção de um produto")
	@DeleteMapping("/products/{id}")
	public ResponseEntity<Object> deleteProduct(@PathVariable("id") Long id) {
		productService.delete(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
