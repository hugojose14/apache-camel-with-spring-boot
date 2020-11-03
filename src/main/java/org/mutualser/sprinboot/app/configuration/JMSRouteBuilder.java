package org.mutualser.sprinboot.app.configuration;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.mutualser.sprinboot.app.commons.AssemblerPost;
import org.mutualser.sprinboot.app.domain.dto.PersonDto;
import org.mutualser.sprinboot.app.domain.model.Person;
import org.springframework.stereotype.Component;
import com.google.gson.Gson;

@Component("jmsConfig")
public class JMSRouteBuilder extends RouteBuilder {
 
  private Person person;
  
  private Gson gson; 
  
  public JMSRouteBuilder(Gson gson) {
    this.gson = gson;
  }
  
  
  @Override
  public void configure() throws Exception {
    
 // Envia un mensaje cada 1 segundo
    from("timer:mytimer?period=5000")
            .to("direct:firstRoute");
    
 // Recibe un mensaje en la cola
    from("direct:firstRoute")
    //.setBody(constant("Hello"))
    .process(this::createEvent)
    .log("Queue -> ${body}")
    .to("jms:queue:hugoPrueba");
    
  }
  
  public void createEvent(Exchange exchange) {
    
    person = Person.builder().id(1).name("Hugo").lastName("Pérez").age(21).build();
    
    PersonDto personDto = AssemblerPost.convertToDto(person); 
    
    String personString = gson.toJson(personDto);

    exchange.getIn().setBody(personString);
    
  }
  
}
