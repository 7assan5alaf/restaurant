package com.hsn.restaurant.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.hsn.restaurant.entity.Categorey;
import com.hsn.restaurant.entity.OrderItem;
import com.hsn.restaurant.entity.Product;
import com.hsn.restaurant.excpetion.EntityNotFound;
import com.hsn.restaurant.mapper.ProductMapper;
import com.hsn.restaurant.repository.OrderItemRepository;
import com.hsn.restaurant.repository.ProductRepository;
import com.hsn.restaurant.request.ProductRequest;
import com.hsn.restaurant.response.PagebleResponse;
import com.hsn.restaurant.response.ProductResponse;
import com.hsn.restaurant.specific.ProductSpecific;

import io.micrometer.common.util.StringUtils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {

	private final ProductRepository productRepository;
	private final CategoryService categoreyService;
	private final ProductMapper productMapper;
	private final OrderItemRepository itemRepository;
	@Value("${application.file.uplaod-file-path}")
	private String fileUploadPath;

	// manage product

	public String addProduct(MultipartFile image, String categoryName, String productName, String synopsis,
			double price) {
		Categorey categorey = categoreyService.findByName(categoryName);
		var product = Product.builder().categorey(categorey).name(productName).price(price).availabilty(true)
				.synopsis(synopsis).build();
		productRepository.save(product);
		uploadFileToProduct(image, product.getId());
		return "Add product successfully";
	}

	public void uploadFileToProduct(MultipartFile image, Long id) {
		var product = productRepository.findById(id).orElseThrow(() -> new EntityNotFound("ProductNotFound"));
		var cover = uploadFile(image, id);
		product.setCover(cover);
		productRepository.save(product);
	}

	public PagebleResponse<ProductResponse> findAll(int page, int size) {
		Pageable pageable = PageRequest.of(page, size, Sort.by("createdBy").descending());
		Page<Product> products = productRepository.findAll(pageable);
		List<ProductResponse> responses = products.stream().map(productMapper::toProductResponse).toList();
		return new PagebleResponse<>(responses, products.getTotalElements(), products.getTotalPages(),
				products.getNumber(), products.isFirst(), products.isLast());
	}

	public PagebleResponse<ProductResponse> findAllByCategorey(int page, int size, Long categoreyId) {
		Pageable pageable = PageRequest.of(page, size, Sort.by("createdBy").descending());
		Page<Product> products = productRepository.findAll(ProductSpecific.withCategorey(categoreyId), pageable);
		List<ProductResponse> responses = products.stream().map(productMapper::toProductResponse).toList();
		return new PagebleResponse<>(responses, products.getTotalElements(), products.getTotalPages(),
				products.getNumber(), products.isFirst(), products.isLast());
	}

	public String updateStatus(Long id) {
		var product = productRepository.findById(id).orElseThrow(() -> new EntityNotFound("Product Not Found"));
		product.setAvailabilty(!product.isAvailabilty());
		productRepository.save(product);
		return "update status successfully";
	}

	public ProductResponse update(Long id, ProductRequest request) {
		var product = productRepository.findById(id).orElseThrow(() -> new EntityNotFound("ProductNotFound"));
		Categorey categorey = categoreyService.findByName(request.getCategoreyName());
		product.setCategorey(categorey);
		product.setName(request.getProductName());
		product.setPrice(request.getPrice());
		product.setSynopsis(request.getSynopsis());
		return productMapper.toUpdateProduct(request);
	}

	public ProductResponse findById(Long id) {
		var p = productRepository.findById(id).orElseThrow(() -> new EntityNotFound("ProductNotFound"));
		return productMapper.toProductResponse(p);
	}
	
	
	public String delete(Long id) {
		List<OrderItem>items=itemRepository.findByProductId(id);
		for(OrderItem item:items) {
			itemRepository.deleteById(item.getId());
		}
	    productRepository.deleteById(id);
		return "Delete successfully";
	}

	// upload file

	public String uploadFile(MultipartFile file, Long productId) {
		Product p = productRepository.findById(productId).orElseThrow(() -> new EntityNotFound("Product Not Found"));
		final String finalPath = fileUploadPath + File.separator + "products" + File.separator
				+ p.getCategorey().getName();
		File targetFolder = new File(finalPath);
		if (!targetFolder.exists()) {
			boolean createFile = targetFolder.mkdirs();
			if (!createFile) {
				log.warn("File not create the target file");
				return null;
			}
		}

		final String fileExtention = getFileExtention(file.getOriginalFilename());
		final String targetFilePath = targetFolder + File.separator + System.currentTimeMillis() + fileExtention;
		Path path = Paths.get(targetFilePath);

		try {
			Files.write(path, file.getBytes());
			log.info("Save file in " + targetFilePath);
			return targetFilePath;
		} catch (IOException e) {
			log.error("file not save" + e.getMessage());
		}

		return null;
	}

	private String getFileExtention(String filename) {
		if (filename.isEmpty() || filename == null) {
			return "";
		}
		int dotIndex = filename.indexOf(".");
		if (dotIndex == -1)
			return "";
		return "." + filename.substring(dotIndex + 1);
	}

	public static byte[] readFileFromLocatin(String fileUrl) {
		if (StringUtils.isBlank(fileUrl))
			return null;

		try {
			Path path = new File(fileUrl).toPath();
			return Files.readAllBytes(path);
		} catch (IOException e) {
			log.error("No file found in the path{}" + e.getMessage());
		}
		return null;

	}

}
