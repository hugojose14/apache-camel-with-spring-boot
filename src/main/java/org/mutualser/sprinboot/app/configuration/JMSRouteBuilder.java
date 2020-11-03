package org.mutualser.sprinboot.app.configuration;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class JMSRouteBuilder extends RouteBuilder {
  
  @Override
  public void configure() throws Exception {
    
 // Envia un mensaje cada 1 segundo
//    from("timer:mytimer?period=1000")
//            .to("direct:firstRoute");
    
 // Recibe un mensaje en la cola
    from("direct:firstRoute")
    .setBody(constant("Hello"))
    .log("Queue -> ${body}")
    .to("jms:queue:hugoPrueba");
  
  }
  
}
