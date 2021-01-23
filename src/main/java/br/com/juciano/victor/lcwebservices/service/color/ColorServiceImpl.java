package br.com.juciano.victor.lcwebservices.service.color;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.juciano.victor.lcwebservices.model.generic.Color;
import br.com.juciano.victor.lcwebservices.repository.color.ColorRepository;

@Service
public class ColorServiceImpl implements ColorService {
	
	@Autowired
	private ColorRepository colorRepository;

	@Override
	public List<Color> findAll() {
		return this.colorRepository.findAll();
	}
	

	@Override
	public List<Color> saveOnesNotExists(List<Color> colors) {
		List<Color> noPersistedcolors = colors
			.stream()
			.filter(c -> 
				this.colorRepository.findAll()
					.stream()
					.noneMatch(pc -> pc.getName().equals(c.getName()))
			)
			.collect(Collectors.toCollection(ArrayList::new));
		return this.colorRepository.saveAll(noPersistedcolors);
	}


	@Override
	public List<Color> findColorsByName(List<String> colorNames) {
		return this.colorRepository.findColorsByNames(colorNames);
	}
	
}
