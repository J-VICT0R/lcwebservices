package br.com.juciano.victor.lcwebservices.dto;

import javax.validation.constraints.NotNull;

import org.modelmapper.ModelMapper;

import com.fasterxml.jackson.annotation.JsonInclude;

import br.com.juciano.victor.lcwebservices.model.generic.Color;
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
public class ColorDTO {
	@NotNull(message="Color name cannot be null")
	private String name;

	@NotNull(message="Color value cannot be null")
	private String value;
	
	public Color convertDTOToEntity() {
		return new ModelMapper().map(this, Color.class);
	}
}
