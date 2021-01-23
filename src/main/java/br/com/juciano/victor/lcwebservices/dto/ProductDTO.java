package br.com.juciano.victor.lcwebservices.dto;


import java.math.BigDecimal;
import java.util.Set;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.modelmapper.ModelMapper;
import org.springframework.hateoas.RepresentationModel;

import com.fasterxml.jackson.annotation.JsonInclude;

import br.com.juciano.victor.lcwebservices.model.product.Product;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductDTO extends RepresentationModel<ProductDTO> {

	private Long id;
	
	@NotNull(message="Product name cannot be null")
	private String name;

	@NotNull(message="Product price cannot be null")
	private BigDecimal price;

	@NotNull(message="Genre cannot be null")
	@Pattern(regexp="^(FEMALE|MALE)$", 
		message="For the genre only the values MALE and FEMALE are accepted.")
	private String genre;
	
	@NotNull(message="Category cannot be null")
	@Pattern(regexp="^(SNEAKERS|T_SHIRT)$", 
		message="For the category only the values SNEAKERS and T_SHIRT are accepted.")
	private String category;
	
	@NotNull(message="Colors cannot be null")
	private Set<ColorDTO> colors;

	@NotNull(message="Product imageUrl cannot be null")
	private String imageUrl;
	
	public Product convertDTOToEntity() {
		return new ModelMapper().map(this, Product.class);
	}
}
