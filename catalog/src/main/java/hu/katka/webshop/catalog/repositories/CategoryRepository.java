package hu.katka.webshop.catalog.repositories;

import hu.katka.webshop.catalog.models.Category;
import java.util.List;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

  @EntityGraph(attributePaths = "products")
  List<Category> findAll();

  Category findByName(String name);

  boolean existsByNameIgnoreCase(String name);

}
