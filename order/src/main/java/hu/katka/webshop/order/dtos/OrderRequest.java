package hu.katka.webshop.order.dtos;

import hu.katka.webshop.order.models.OrderItem;
import java.util.List;
import lombok.Data;

@Data
public class OrderRequest {

  private String userName;
  private String address;
  private List<OrderItem> items;

}
