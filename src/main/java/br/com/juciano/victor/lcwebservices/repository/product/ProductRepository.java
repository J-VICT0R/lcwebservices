package br.com.juciano.victor.lcwebservices.repository.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.juciano.victor.lcwebservices.model.product.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}
