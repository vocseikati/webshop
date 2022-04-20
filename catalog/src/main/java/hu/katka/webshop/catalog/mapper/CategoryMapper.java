package hu.katka.webshop.catalog.mapper;

import hu.katka.webshop.catalog.api.model.CategoryDto;
import hu.katka.webshop.catalog.models.Category;
import java.util.List;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

  List<CategoryDto> categriesToDtos(Iterable<Category> categories);
}
