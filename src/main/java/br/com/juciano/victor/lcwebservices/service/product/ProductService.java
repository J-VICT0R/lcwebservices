package br.com.juciano.victor.lcwebservices.service.product;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.juciano.victor.lcwebservices.model.product.Product;

public interface ProductService {
	
	List<Product> findAll();
	Page<Product> findAllByPages(Pageable pageable);
	Optional<Product> findById(Long id);
	Product save(Product product);
	void deleteById(Long id);
}
