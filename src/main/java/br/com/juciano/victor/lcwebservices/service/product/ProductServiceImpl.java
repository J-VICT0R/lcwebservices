package br.com.juciano.victor.lcwebservices.service.product;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.juciano.victor.lcwebservices.model.generic.Color;
import br.com.juciano.victor.lcwebservices.model.product.Product;
import br.com.juciano.victor.lcwebservices.repository.product.ProductRepository;
import br.com.juciano.victor.lcwebservices.service.color.ColorService;

@Service
public class ProductServiceImpl implements ProductService {
	
	@Autowired
	ProductRepository productRepository;
	@Autowired
	ColorService colorService;
	
	public List<Product> findAll() {
		return this.productRepository.findAll();
	}

	@Override
	public void deleteById(Long productId) {
		this.productRepository.deleteById(productId);
	}

	@Override
	@Transactional
	public Product save(Product product) {
		product.setColors(persistColorsIfNotExistsAndGetThem(product.getColors().stream().collect(Collectors.toList())));
		return this.productRepository.save(product);
	}
	
	@Override
	public Optional<Product> findById(Long id) {
		return this.productRepository.findById(id);
	}

	@Override
	public Page<Product> findAllByPages(Pageable pageable) {
		return this.productRepository.findAll(pageable);
	}

	private Set<Color> persistColorsIfNotExistsAndGetThem(List<Color> productColors) {
		this.colorService.saveOnesNotExists(productColors);
		return Set.copyOf(
				colorService.findColorsByName(
						productColors.stream()
							.map(Color::getName)
							.collect(Collectors.toList())
						)
				);
	}
}
