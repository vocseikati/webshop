package hu.katka.webshop.catalog.services;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import hu.katka.webshop.catalog.models.Category;
import hu.katka.webshop.catalog.models.HistoryData;
import hu.katka.webshop.catalog.models.Product;
import hu.katka.webshop.catalog.models.QProduct;
import hu.katka.webshop.catalog.repositories.CategoryRepository;
import hu.katka.webshop.catalog.repositories.ProductRepository;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.hibernate.envers.DefaultRevisionEntity;
import org.hibernate.envers.RevisionType;
import org.hibernate.envers.query.AuditEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.webjars.NotFoundException;

@Service
@RequiredArgsConstructor
public class CatalogService {

  private final CategoryRepository categoryRepository;
  private final ProductRepository productRepository;

  @PersistenceContext
  private EntityManager em;

  @Transactional
  public Category createCategory(Category category) {
    if (category.getId() != null) {
      throw new IllegalArgumentException("Category id must be null");
    }
    if (categoryRepository.existsByNameIgnoreCase(category.getName())) {
      throw new IllegalArgumentException("Category name is already exists. Name must be unique.");
    }
    return categoryRepository.save(category);
  }

  public List<Category> findAllCategories() {
    return categoryRepository.findAll();
  }

  @Transactional
  public Product createProduct(Product product) {
    return productRepository.save(product);
  }

  @Transactional
  public Product addCategoryToProduct(int productId, Category category) {
    Product product = getProductOrThrow(productId);

    if (!categoryRepository.existsByNameIgnoreCase(category.getName())) {
      throw new IllegalArgumentException(
          "Category does not exist. Please create a category first.");
    }

    Category categoryByName = categoryRepository.findByName(category.getName());
    categoryByName.addProduct(product);
    return productRepository.save(product);
  }

  public List<Product> findAllProducts() {
    return productRepository.findAll();
  }

  public Product findProductById(Integer id){
    return getProductOrThrow(id);
  }

  @Transactional
  public Product modifyProduct(Product newProduct) {
    Product originalProduct = getProductOrThrow(newProduct.getId());
    if (newProduct.getCategory() == null) {
      newProduct.setCategory(originalProduct.getCategory());
    }
    return productRepository.save(newProduct);
  }

  @Transactional
  public void deleteProduct(int id) {
    productRepository.delete(getProductOrThrow(id));
  }

  private Product getProductOrThrow(int id) {
    Optional<Product> product = productRepository.findById(id);
    if (product.isEmpty()) {
      throw new NotFoundException("There is no product with the provided id.");
    }
    return product.get();
  }

  @Transactional
  public List<Product> searchWithRelationships(Predicate predicate, Pageable pageable) {
    List<Product> products = productRepository.findAll(predicate, pageable).getContent();
    BooleanExpression inByProductId = QProduct.product.in(products);
    products = productRepository.findAll(inByProductId, "Product.category", Pageable.unpaged()
        .getSort());
    return products;
  }

  @Transactional
  public List<HistoryData<Product>> getHistoryById(Integer id) {
    List resultList = AuditReaderFactory.get(em)
        .createQuery()
        .forRevisionsOfEntity(Product.class, false, true)
        .add(AuditEntity.property("id").eq(id))
        .getResultList()
        .stream()
        .map(o -> {
          Object[] objArray = (Object[]) o;
          DefaultRevisionEntity defaultRevisionEntity = (DefaultRevisionEntity) objArray[1];
          RevisionType revType = (RevisionType) objArray[2];

          Product product = (Product) objArray[0];
          HistoryData<Product> historyData =
              new HistoryData<>(
                  product, revType,
                  defaultRevisionEntity.getId(), defaultRevisionEntity.getRevisionDate());
          return historyData;
        }).toList();
    return resultList;
  }
}
