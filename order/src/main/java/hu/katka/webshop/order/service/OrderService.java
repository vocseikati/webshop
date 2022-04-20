package hu.katka.webshop.order.service;

import hu.katka.webshop.order.dtos.OrderRequest;
import hu.katka.webshop.order.dtos.OrderResponse;
import hu.katka.webshop.order.models.OrderItem;
import hu.katka.webshop.order.models.OrderStatus;
import hu.katka.webshop.order.repositories.OrderRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.EnumUtils;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {

  private final OrderRepository orderRepository;

  public OrderResponse addOrder(OrderRequest orderRequest) {
    List<OrderItem> items = orderRequest.getItems();
    for (OrderItem item : items) {
      item.setStatus(OrderStatus.PENDING);
    }
    orderRepository.saveAll(items);
    OrderResponse response = OrderResponse.builder()
        .userName(orderRequest.getUserName())
        .address(orderRequest.getAddress())
        .totalPrice(countTotalPrice(orderRequest.getItems()))
        .status(OrderStatus.PENDING)
        .build();
    return response;
  }

  private double countTotalPrice(List<OrderItem> items) {
    double totalPrice = 0;
    for (OrderItem item : items) {
      totalPrice += item.getUnitPrice() * item.getQuantity();
    }
    return totalPrice;
  }

  public List<OrderItem> findAllOrders() {
    return orderRepository.findAll();
  }

  public List<OrderItem> findByUserName(String userName) {
    return orderRepository.findByUserNameIgnoreCase(userName);
  }

  public OrderItem changeOrderStatus(Integer id, String status) {
    OrderItem orderItem = orderRepository.findById(id).orElseThrow(IllegalArgumentException::new);
    if (!EnumUtils.isValidEnum(OrderStatus.class, status.toUpperCase())){
      throw new IllegalArgumentException("invalid status");
    }
    orderItem.setStatus(OrderStatus.valueOf(status.toUpperCase()));
    return orderRepository.save(orderItem);
  }
}
