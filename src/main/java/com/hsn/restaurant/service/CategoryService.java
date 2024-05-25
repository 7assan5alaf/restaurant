package com.hsn.restaurant.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.hsn.restaurant.entity.Categorey;
import com.hsn.restaurant.excpetion.EntityNotFound;
import com.hsn.restaurant.repository.CategoryRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryService {
	

	private final CategoryRepository categoreyRepository;
	
	public String addCategorey(Categorey categorey) {
		categoreyRepository.save(categorey);
		return "Add categorey successfully";
	}
	
	public Categorey findByName(String name) {
		return categoreyRepository.findByName(name)
				.orElseThrow(()->new EntityNotFound("categorey not found"));
	}
	
	public Categorey update(Long id,String name) {
		Categorey categorey=categoreyRepository.findById(id)
				.orElseThrow(()->new EntityNotFound("categorey not found"));
		categorey.setName(name);
		categoreyRepository.save(categorey);
		return categorey;
	}
	
	public String delete(Long id) {
		Categorey categorey=categoreyRepository.findById(id)
				.orElseThrow(()->new EntityNotFound("categorey not found"));
		categoreyRepository.deleteById(categorey.getId());
		return "delete successfully";
	}

	public List<?> findAll() {
		return categoreyRepository.findAll();
	}
	
}
