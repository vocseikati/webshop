package hu.katka.webshop.order.dtos;

import hu.katka.webshop.order.models.OrderStatus;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderResponse {

  private String userName;
  private String address;
  private Double totalPrice;
  private OrderStatus status;
}
