package hu.katka.webshop.order.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderItem {

  @Id
  @GeneratedValue
  private Integer orderId;
  private Integer productId;
  private Integer quantity;
  private Double unitPrice;
  private String userName;
  private String address;
  @Enumerated(EnumType.STRING)
  private OrderStatus status;


}
