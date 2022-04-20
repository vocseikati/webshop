package hu.katka.webshop.order.repositories;

import hu.katka.webshop.order.models.OrderItem;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<OrderItem, Integer> {

  List<OrderItem> findByUserNameIgnoreCase(String userName);

}
