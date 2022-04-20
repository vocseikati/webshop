package hu.katka.webshop.catalog.models;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.envers.Audited;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "categories")
@Audited
public class Category {

  @Id
  @GeneratedValue
  @ToString.Include
  @EqualsAndHashCode.Include
  private Integer id;

  @Column(unique = true)
  @NotEmpty
  @ToString.Include
  @EqualsAndHashCode.Include
  private String name;

  @OneToMany(mappedBy = "category")
  private List<Product> products;

  public void addProduct(Product product) {
    if (products == null){
      this.products = new ArrayList<>();
    }
    this.products.add(product);
    product.setCategory(this);
  }
}
