package hu.katka.webshop.catalog.controllers;

import com.querydsl.core.types.Predicate;
import hu.katka.webshop.catalog.api.CatalogControllerApi;
import hu.katka.webshop.catalog.api.model.CategoryDto;
import hu.katka.webshop.catalog.api.model.HistoryDataProductDto;
import hu.katka.webshop.catalog.api.model.ProductDto;
import hu.katka.webshop.catalog.mapper.CatergoryMapper;
import hu.katka.webshop.catalog.mapper.ProductMapper;
import hu.katka.webshop.catalog.models.Category;
import hu.katka.webshop.catalog.models.HistoryData;
import hu.katka.webshop.catalog.models.Product;
import hu.katka.webshop.catalog.services.CatalogService;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.core.MethodParameter;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.data.web.SortDefault;
import org.springframework.data.web.querydsl.QuerydslPredicateArgumentResolver;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.ModelAndViewContainer;

@RestController
@RequiredArgsConstructor
@FeignClient(name = "catalog", url = "${feign.catalog.url}")
public class CatalogController implements CatalogControllerApi {

  private final CatalogService service;
  private final CatergoryMapper catergoryMapper;
  private final ProductMapper productMapper;
  private final NativeWebRequest nativeWebRequest;
  private final PageableHandlerMethodArgumentResolver pageableResolver;
  private final QuerydslPredicateArgumentResolver predicateResolver;

  @Override
  public ResponseEntity<List<CategoryDto>> getCategories() {
    List<CategoryDto> categries = catergoryMapper.categriesToDtos(service.findAllCategories());
    return ResponseEntity.ok(categries);
  }



  @Override
  public ResponseEntity<CategoryDto> addCategory(CategoryDto categoryDto) {
    Category category = service.createCategory(catergoryMapper.categoryDtoToEntity(categoryDto));
    return ResponseEntity.ok(catergoryMapper.entityToCategoryDto(category));
  }

  @Override
  public ResponseEntity<List<ProductDto>> getAllProducts() {
    List<ProductDto> products = productMapper.productsToDtos(service.findAllProducts());
    return ResponseEntity.ok(products);
  }

  @Override
  public ResponseEntity<ProductDto> createProduct(ProductDto productDto) {
    Product product = service.createProduct(productMapper.productDtoToEntity(productDto));
    return ResponseEntity.ok(productMapper.entityToProductDto(product));
  }

  @Override
  public ResponseEntity<ProductDto> modifyProduct(Integer id, ProductDto productDto) {
    productDto.setId(id);
    Product modifiedProduct = service.modifyProduct(productMapper.productDtoToEntity(productDto));
    return ResponseEntity.ok(productMapper.entityToProductDto(modifiedProduct));
  }

  @Override
  public ResponseEntity<Void> deleteProduct(Integer id) {
    service.deleteProduct(id);
    return ResponseEntity.ok().build();
  }

  @Override
  public ResponseEntity<ProductDto> addCategoryToProduct(Integer id, CategoryDto categoryDto) {
    Product product =
        service.addCategoryToProduct(id, catergoryMapper.categoryDtoToEntity(categoryDto));
    return ResponseEntity.ok(productMapper.entityToProductDto(product));
  }

  @Override
  public ResponseEntity<List<ProductDto>> searchProducts(String name, String categoryName,
                                                         Double price, Integer page, Integer size,
                                                         List<String> sort) {
    Pageable pageable = createPageable("configPageable");
    Predicate predicate = createPredicate("configPredicate");
    List<Product> products = service.searchWithRelationships(predicate, pageable);
    return ResponseEntity.ok(productMapper.productsToDtos(products));
  }

  @Override
  public ResponseEntity<List<HistoryDataProductDto>> getProductHistoryById(Integer id) {
    List<HistoryData<Product>> products = service.getHistoryById(id);
    List<HistoryDataProductDto> productDtosWithHistory = new ArrayList<>();
    for (HistoryData<Product> product : products) {
      productDtosWithHistory.add(productMapper.productHistoryToHistoryDataProductDto(product));
    }
    return ResponseEntity.ok(productDtosWithHistory);
  }

  private Predicate createPredicate(String configMethodName) {
    Method method;
    try {
      ModelAndViewContainer mavContainer = null;
      WebDataBinderFactory binderFactory = null;
      method = this.getClass().getMethod(configMethodName, Predicate.class);
      MethodParameter methodParameter = new MethodParameter(method, 0);
      return (Predicate) predicateResolver.resolveArgument(methodParameter, mavContainer,
          nativeWebRequest, binderFactory);
    } catch (Exception e) {
      e.printStackTrace();
      throw new RuntimeException(e);
    }
  }

  private Pageable createPageable(String pageableConfigurerMethodName) {
    ModelAndViewContainer mavContainer = null;
    WebDataBinderFactory binderFactory = null;
    Method method;
    try {
      method = this.getClass().getMethod(pageableConfigurerMethodName, Pageable.class);
    } catch (NoSuchMethodException e) {
      e.printStackTrace();
      throw new RuntimeException(e);
    }
    MethodParameter methodParameter = new MethodParameter(method, 0);
    return pageableResolver.resolveArgument(methodParameter, mavContainer, nativeWebRequest,
        binderFactory);
  }

  public void configPageable(@SortDefault("id") Pageable pageable) {
  }

  public void configPredicate(@QuerydslPredicate(root = Product.class) Predicate predicate) {
  }
}
