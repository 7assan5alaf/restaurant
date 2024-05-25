package com.hsn.restaurant.controller;



import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.hsn.restaurant.entity.Categorey;
import com.hsn.restaurant.request.ProductRequest;
import com.hsn.restaurant.service.CategoryService;
import com.hsn.restaurant.service.OrderService;
import com.hsn.restaurant.service.ProductService;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/admin")
public class AdminController {

	private final ProductService productService;
	private final CategoryService categoreyService;
	private final OrderService orderService;

	// manage category (add-update-delete-view)
	@GetMapping("/categories")
	public ResponseEntity<?> findAll(Authentication authentication) {
		return ResponseEntity.ok(categoreyService.findAll());
	}

	@PostMapping("/categories/add")
	public ResponseEntity<?> add(@RequestBody Categorey categorey) {
		return ResponseEntity.ok(categoreyService.addCategorey(categorey));
	}

	@PatchMapping("/categories/update/{id}")
	public ResponseEntity<?> update(@PathVariable Long id, @RequestParam String name) {
		return ResponseEntity.ok(categoreyService.update(id, name));
	}

	@DeleteMapping("/categories/delete/{id}")
	public ResponseEntity<?> deleteCategory(@PathVariable Long id) {
		return ResponseEntity.ok(categoreyService.delete(id));
	}

	// manage product (add-update-delete-view)

	@GetMapping("/products/{id}")
	public ResponseEntity<?> findById(@PathVariable Long id) {
		return ResponseEntity.ok(productService.findById(id));
	}

	@GetMapping("/products")
	public ResponseEntity<?> findAll(@RequestParam(defaultValue = "0", required = false) int page,
			@RequestParam(defaultValue = "10", required = false) int size) {
		return ResponseEntity.ok(productService.findAll(page, size));
	}

	@PatchMapping("/products/update-image/{id}")
	public ResponseEntity<?> addImage(@RequestParam MultipartFile image, @PathVariable Long id) {
		productService.uploadFileToProduct(image, id);
		return ResponseEntity.accepted().build();
	}

	@GetMapping("/products-category/{categoryId}")
	public ResponseEntity<?> findAllByCategory(@PathVariable Long categoryId,
			@RequestParam(defaultValue = "0", required = false) int page,
			@RequestParam(defaultValue = "10", required = false) int size) {
		return ResponseEntity.ok(productService.findAllByCategorey(page, size, categoryId));
	}

	@PostMapping("/products/add")
	public ResponseEntity<?> add(@RequestParam MultipartFile image, @RequestParam String categoryName,
			@RequestParam String productName, @RequestParam String synopsis, @RequestParam double price) {
		return ResponseEntity.ok(productService.addProduct(image, categoryName, productName, synopsis, price));
	}

	@PutMapping("/products/update/{id}")
	public ResponseEntity<?> update(@PathVariable Long id, @RequestBody ProductRequest request) {
		return ResponseEntity.ok(productService.update(id, request));
	}

	@PatchMapping("/products/update-status/{id}")
	public ResponseEntity<?> updateStatus(@PathVariable Long id) {
		return ResponseEntity.ok(productService.updateStatus(id));
	}

	@DeleteMapping("/products/delete/{id}")
	public ResponseEntity<?> deleteProduct(@PathVariable Long id) {
		return ResponseEntity.ok(productService.delete(id));
	}

	// manage order
	@PatchMapping("/order/completed/{orderId}")
	public ResponseEntity<?> orderIsCompleted(@PathVariable Long orderId) throws MessagingException {
		return ResponseEntity.ok(orderService.updateOrderStatus(orderId));
	}

}
