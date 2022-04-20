package hu.katka.webshop.user;

import hu.katka.webshop.tokenlib.JwtAuthFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.FeignClient;

@SpringBootApplication(scanBasePackageClasses = {
    JwtAuthFilter.class,
    UserApplication.class
})
@FeignClient(name = "user", url = "${feign.user.url}")
public class UserApplication {

  public static void main(String[] args) {
    SpringApplication.run(UserApplication.class, args);
  }

}
