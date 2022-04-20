package hu.katka.webshop.catalog.repositories;

import com.querydsl.core.types.Predicate;
import java.util.List;
import org.springframework.data.domain.Sort;

public interface QuerydslWithEntityGraphRepository<T> {

  List<T> findAll(Predicate predicate, String entityGraphName, Sort sort);
}
