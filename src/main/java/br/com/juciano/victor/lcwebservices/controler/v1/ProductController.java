package br.com.juciano.victor.lcwebservices.controler.v1;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.juciano.victor.lcwebservices.dto.ProductDTO;
import br.com.juciano.victor.lcwebservices.dto.Response;
import br.com.juciano.victor.lcwebservices.model.product.Product;
import br.com.juciano.victor.lcwebservices.service.product.ProductService;
import br.com.juciano.victor.lcwebservices.util.ApiUtil;
import br.com.juciano.victor.lcwebservices.util.Indirection;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/lcwebservices/v1/products")
public class ProductController {

	// https://mari-azevedo.medium.com/construindo-uma-api-restful-com-java-e-spring-framework-46b74371d107
	ProductService productService;

	@Autowired
	public ProductController(ProductService productService) {
	  this.productService = productService;
	}
	
	@CrossOrigin
	@GetMapping
	@ApiOperation(value = "Endpoint to findAll products in page mode")
	public ResponseEntity<Response<Page<ProductDTO>>> findAll(
		@RequestHeader(value=ApiUtil.HEADER_API_VERSION, defaultValue="${api.version}") String apiVersion, 
		@RequestHeader(value=ApiUtil.HEADER_API_KEY, defaultValue="${api.key}") String apiKey,
		@PageableDefault(page = 1, size = 10, sort = {"name"}) Pageable pageable
	) {
		Response<Page<ProductDTO>> response = new Response<>();

		Page<ProductDTO> products = this.productService.findAllByPages(pageable).map(this::hateoasResponse);

		response.setData(products);

		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.add(ApiUtil.HEADER_API_VERSION, apiVersion);
		headers.add(ApiUtil.HEADER_API_KEY, apiKey);

		return new ResponseEntity<>(response, headers, HttpStatus.OK);
	}
	
	@CrossOrigin
	@GetMapping(value = "/{id}")
	@ApiOperation(value = "Endpoint to find products by id")
	public ResponseEntity<Response<ProductDTO>> findById(
		@RequestHeader(value=ApiUtil.HEADER_API_VERSION, defaultValue="${api.version}") String apiVersion, 
		@RequestHeader(value=ApiUtil.HEADER_API_KEY, defaultValue="${api.key}") String apiKey,
		@PathVariable("id") Long id
	) {
		Response<ProductDTO> response = new Response<>();
		
		 Indirection<HttpStatus> status = new Indirection<HttpStatus>(HttpStatus.OK);

		this.productService.findById(id).ifPresentOrElse(p -> {
			response.setData(hateoasResponse(p));
		}, () ->
			status.setIndirection(HttpStatus.NOT_FOUND)
		);
		
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.add(ApiUtil.HEADER_API_VERSION, apiVersion);
		headers.add(ApiUtil.HEADER_API_KEY, apiKey);

		return new ResponseEntity<>(response, headers, status.getIndirection());
	}
	
	@PostMapping
	@ApiOperation(value = "Endpoint to create a new product")
	public ResponseEntity<Response<ProductDTO>> create(
		@RequestHeader(value=ApiUtil.HEADER_API_VERSION, defaultValue="${api.version}") String apiVersion, 
		@RequestHeader(value=ApiUtil.HEADER_API_KEY, defaultValue="${api.key}") String apiKey,
		@Valid @RequestBody ProductDTO newProductDTO,
		BindingResult validationResult 
	) {
		Response<ProductDTO> response = new Response<>();

		if (validationResult.hasErrors()) {
			validationResult.getAllErrors().forEach(error -> response.addErrorMsgToResponse(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}

		Product createdProduct = this.productService.save(newProductDTO.convertDTOToEntity());

		response.setData(hateoasResponse(createdProduct));

		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.add(ApiUtil.HEADER_API_VERSION, apiVersion);
		headers.add(ApiUtil.HEADER_API_KEY, apiKey);
		
		return new ResponseEntity<>(response, headers, HttpStatus.CREATED);
	}
	
	@CrossOrigin
	@DeleteMapping("/{id}")
	@ApiOperation(value = "Endpoint to delete product")
	public ResponseEntity<Response<String>> delete(
		@RequestHeader(value=ApiUtil.HEADER_API_VERSION, defaultValue="${api.version}") String apiVersion,
		@RequestHeader(value=ApiUtil.HEADER_API_KEY, defaultValue="${api.key}") String apiKey,
		@PathVariable("id") Long id
	) {
		Response<String> response = new Response<>();
		
		Indirection<HttpStatus> status = new Indirection<HttpStatus>(HttpStatus.NO_CONTENT);
		
		this.productService.findById(id)
			.ifPresentOrElse(
				p -> this.productService.deleteById(id), 
				() -> status.setIndirection(HttpStatus.NOT_FOUND)
			);


		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.add(ApiUtil.HEADER_API_VERSION, apiVersion);
		headers.add(ApiUtil.HEADER_API_KEY, apiKey);

		return new ResponseEntity<>(response, headers, status.getIndirection());
	}
	
	private ProductDTO hateoasResponse(Product product) {
		Link selfLink = WebMvcLinkBuilder.linkTo(ProductController.class).slash(product.getId()).withSelfRel();
		return product.convertEntityToDTO().add(selfLink);
	}
}
