package hu.katka.webshop.shipper.xmlws;

import java.util.Random;
import org.springframework.stereotype.Service;

@Service
public class ShippingXmlWsImpl implements ShippingXmlWs{

  private Random random = new Random();

  public ShippingXmlWsImpl(Random random) {
    this.random = random;
  }

  @Override
  public String getShippingStatus(Integer orderId) {
    int count = random.nextInt(2);
    if (count == 0){
      return "SHIPMENT_FAILED";
    }else {
      return "DELIVERED";
    }
  }
}
