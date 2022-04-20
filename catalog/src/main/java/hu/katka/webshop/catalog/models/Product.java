package hu.katka.webshop.catalog.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
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
@Table(name = "products")
@NamedEntityGraph(name = "Product.category", attributeNodes = @NamedAttributeNode(value = "category"))
@Audited
public class Product {

  @Id
  @GeneratedValue
  @ToString.Include
  @EqualsAndHashCode.Include
  private Integer id;

  @NotEmpty(message = "Name must have a value.")
  @ToString.Include
  @EqualsAndHashCode.Include
  private String name;

  @NotNull(message = "Price must have a value.")
  @ToString.Include
  private double price;

  @ManyToOne
  Category category;

}
