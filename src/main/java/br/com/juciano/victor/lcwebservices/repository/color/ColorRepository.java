package br.com.juciano.victor.lcwebservices.repository.color;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.juciano.victor.lcwebservices.model.generic.Color;

@Repository
public interface ColorRepository extends JpaRepository<Color, Long> {
	@Query(value = "FROM Color c WHERE c.name IN(?1)")
	List<Color> findColorsByNames(List<String> colorNames);
}
