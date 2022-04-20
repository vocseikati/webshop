package hu.katka.webshop.shipper.controllers;

import hu.katka.webshop.order.controllers.OrderApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ShippingController {

  @Autowired
  OrderApi orderApi;


}
