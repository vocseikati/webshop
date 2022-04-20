package hu.katka.webshop.order.controllers;

import hu.katka.webshop.order.dtos.OrderRequest;
import hu.katka.webshop.order.dtos.OrderResponse;
import hu.katka.webshop.order.models.OrderItem;
import hu.katka.webshop.order.service.OrderService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OrderController implements OrderApi {

  private final OrderService orderService;

  @Override
  public OrderResponse createOrder(@RequestBody OrderRequest orderRequest){
    OrderResponse response = orderService.addOrder(orderRequest);
    return response;
  }

  @Override
  public List<OrderItem> getAllOrders(){
    return orderService.findAllOrders();
  }

  @Override
  public List<OrderItem> findOrderByUserName(@PathVariable String userName){
    return orderService.findByUserName(userName);
  }

  @Override
  public OrderItem modifyOrderStatus(@PathVariable Integer id, @RequestParam String status){
    return orderService.changeOrderStatus(id, status);
  }
}
