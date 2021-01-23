package br.com.juciano.victor.lcwebservices.dto;

import javax.validation.constraints.NotNull;

import org.modelmapper.ModelMapper;
import org.springframework.hateoas.RepresentationModel;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductColorDTO extends RepresentationModel<ProductColorDTO> {
	Long id;
	
	@NotNull(message="Color name cannot be null")
	String name;
	
	@NotNull(message="Color value cannot be null")
	String value;
	
	Long productId;
	
	public ProductColorDTO convertDTOToEntity() {
		return new ModelMapper().map(this, ProductColorDTO.class);
	}
}
