package hu.katka.webshop.catalog.mapper;

import hu.katka.webshop.catalog.api.model.HistoryDataProductDto;
import hu.katka.webshop.catalog.api.model.ProductDto;
import hu.katka.webshop.catalog.models.HistoryData;
import hu.katka.webshop.catalog.models.Product;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductMapper {

  List<ProductDto> productsToDtos(List<Product> products);

  Product productDtoToEntity(ProductDto productDto);

//  @Mapping(target = "category.products", ignore = true)
  @Mapping(target = "category", ignore = true)
  ProductDto entityToProductDto(Product product);

  HistoryDataProductDto productHistoryToHistoryDataProductDto(HistoryData<Product> historyData);

  List<HistoryDataProductDto> productHistoryToHistoryDataProductDtos(List<HistoryData<Product>> history);
}
