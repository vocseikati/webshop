package hu.katka.webshop.catalog.mapper;

import hu.katka.webshop.catalog.api.model.CategoryDto;
import hu.katka.webshop.catalog.models.Category;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CatergoryMapper {

  List<CategoryDto> categriesToDtos(Iterable<Category> categories);

  Category categoryDtoToEntity(CategoryDto categoryDto);

  @Mapping(target = "products", ignore = true)
  CategoryDto entityToCategoryDto(Category category);
}
