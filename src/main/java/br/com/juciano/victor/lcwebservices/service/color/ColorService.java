package br.com.juciano.victor.lcwebservices.service.color;

import java.util.List;

import org.springframework.stereotype.Service;

import br.com.juciano.victor.lcwebservices.model.generic.Color;

@Service
public interface ColorService {
	List<Color> findAll();
	List<Color> findColorsByName(List<String> colorNames);
	List<Color> saveOnesNotExists(List<Color> color);
}
