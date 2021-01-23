package br.com.juciano.victor.lcwebservices.model.product;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.modelmapper.ModelMapper;

import br.com.juciano.victor.lcwebservices.dto.ProductDTO;
import br.com.juciano.victor.lcwebservices.enumeration.Genre;
import br.com.juciano.victor.lcwebservices.enumeration.ProductCategory;
import br.com.juciano.victor.lcwebservices.model.generic.Color;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "product")
public class Product implements Serializable {

	private static final long serialVersionUID = -1002282625802058416L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="product_id")
    private Long id;
    private String name;
    
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "product_color",
    		joinColumns = @JoinColumn(
                    name = "product_id",
                    referencedColumnName = "product_id"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "color_id",
                    referencedColumnName = "color_id"
            )
	)
    private Set<Color> colors;

    @Enumerated(EnumType.STRING)
    private ProductCategory category;

    @Enumerated(EnumType.STRING)
    private Genre genre;

    private BigDecimal price;
    private String imageUrl;

	public ProductDTO convertEntityToDTO() {
		return new ModelMapper().map(this, ProductDTO.class);
	}
}
