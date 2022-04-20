package hu.katka.webshop.catalog.repositories;

import com.querydsl.core.types.dsl.StringExpression;
import hu.katka.webshop.catalog.models.Product;
import hu.katka.webshop.catalog.models.QProduct;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;

public interface ProductRepository extends JpaRepository<Product, Integer>,
    QuerydslPredicateExecutor<Product>, QuerydslBinderCustomizer<QProduct>,
    QuerydslWithEntityGraphRepository<Product> {

  @EntityGraph(attributePaths = "category")
  List<Product> findAll();

  @Override
  default void customize(QuerydslBindings bindings, QProduct product) {
    bindings.bind(product.name).first((StringExpression::containsIgnoreCase));
    bindings.bind(product.category.name)
        .first((StringExpression::startsWithIgnoreCase));
    bindings.bind(product.price).all(((path, values) -> {
      if (values.size() < 2) {
        return Optional.empty();
      }
      Iterator<? extends Double> iterator = values.iterator();
      Double from = iterator.next();
      Double to = iterator.next();
      return Optional.of(path.between(from,to));
    }));
  }
}
