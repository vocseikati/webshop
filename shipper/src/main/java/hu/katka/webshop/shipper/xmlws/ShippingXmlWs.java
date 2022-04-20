package hu.katka.webshop.shipper.xmlws;

import javax.jws.WebService;

@WebService
public interface ShippingXmlWs {

  String getShippingStatus(Integer orderId);
}
