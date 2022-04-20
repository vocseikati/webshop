package hu.katka.webshop.catalog.repositories;

import com.querydsl.core.types.EntityPath;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import hu.katka.webshop.catalog.models.Product;
import java.util.List;
import javax.persistence.EntityManager;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.support.Querydsl;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.data.querydsl.SimpleEntityPathResolver;

public class QuerydslWithEntityGraphRepositoryImpl extends SimpleJpaRepository<Product, Integer>
    implements QuerydslWithEntityGraphRepository<Product> {

  private final EntityManager em;
  private final EntityPath<Product> path;
  private final PathBuilder<Product> builder;
  private final Querydsl querydsl;

  public QuerydslWithEntityGraphRepositoryImpl(EntityManager em) {
    super(Product.class, em);
    this.em = em;
    this.path = SimpleEntityPathResolver.INSTANCE.createPath(Product.class);
    this.builder = new PathBuilder<>(path.getType(), path.getMetadata());
    this.querydsl = new Querydsl(em, builder);
  }

  @Override
  public List<Product> findAll(Predicate predicate, String entityGraphName, Sort sort) {
    JPAQuery query = (JPAQuery)querydsl.applySorting(sort, createQuery(predicate).select(path));
    query.setHint(EntityGraph.EntityGraphType.LOAD.getKey(), em.getEntityGraph(entityGraphName));
    return query.fetch();
  }

  private JPAQuery createQuery(Predicate predicate) {
    return querydsl.createQuery(path).where(predicate);
  }
}
