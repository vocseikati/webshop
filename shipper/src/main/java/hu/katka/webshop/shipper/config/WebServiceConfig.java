package hu.katka.webshop.shipper.config;

import hu.katka.webshop.shipper.xmlws.ShippingXmlWs;
import javax.xml.ws.Endpoint;
import org.apache.cxf.Bus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebServiceConfig {

  private Bus bus;
  private ShippingXmlWs shippingXmlWs;

  public WebServiceConfig(Bus bus, ShippingXmlWs shippingXmlWs) {
    this.bus = bus;
    this.shippingXmlWs = shippingXmlWs;
  }

  @Bean
  public Endpoint endPoint() {
    EndpointImpl endpointImpl = new EndpointImpl(bus, shippingXmlWs);
    endpointImpl.publish("/shipping");
    return endpointImpl;
  }
}
