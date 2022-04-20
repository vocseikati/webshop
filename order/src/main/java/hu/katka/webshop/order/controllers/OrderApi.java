package hu.katka.webshop.order.controllers;

import hu.katka.webshop.order.dtos.OrderRequest;
import hu.katka.webshop.order.dtos.OrderResponse;
import hu.katka.webshop.order.models.OrderItem;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "order", url = "${feign.order.url}")
public interface OrderApi {

  @PostMapping("/api/orders")
  OrderResponse createOrder(@RequestBody OrderRequest orderRequest);

  @GetMapping("/api/orders")
  List<OrderItem> getAllOrders();

  @GetMapping("/api/orders/{userName}")
  List<OrderItem> findOrderByUserName(@PathVariable String userName);

  @PutMapping("/api/orders/{id}")
  OrderItem modifyOrderStatus(@PathVariable Integer id, @RequestParam String status);
}
