package br.com.juciano.victor.lcwebservices.dto;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
public class ResponseError {

	@NotNull(message="Details cannot be null")
    private String details;
	
}
