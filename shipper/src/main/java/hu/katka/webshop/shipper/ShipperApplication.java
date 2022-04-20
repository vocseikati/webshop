package hu.katka.webshop.shipper;

import hu.katka.webshop.order.controllers.OrderApi;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackageClasses = {OrderApi.class})
public class ShipperApplication {

  public static void main(String[] args) {
    SpringApplication.run(ShipperApplication.class, args);
  }

}
