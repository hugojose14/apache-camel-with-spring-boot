package org.mutualser.sprinboot.app.configuration;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.mutualser.sprinboot.app.commons.AssemblerPost;
import org.mutualser.sprinboot.app.domain.dto.PersonDto;
import org.mutualser.sprinboot.app.domain.dto.Staff;
import org.mutualser.sprinboot.app.domain.model.Person;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

@Component("jmsConfig")
public class JMSRouteBuilder extends RouteBuilder {
 
  private Person person;
  
  private Staff staff;
  
  private Gson gson; 
  
  ObjectMapper mapper;
  
  public JMSRouteBuilder(Gson gson,ObjectMapper mapper) {
    this.gson = gson;
    this.mapper = mapper;
  }
  
  @Override
  public void configure() throws Exception {
    
 // Envia un mensaje cada x segundos
    from("timer:mytimer?period=30000")
            .to("direct:firstRoute");
    
    from("timer:mytimer?period=3000")
    .to("direct:secondRoute");
    
    
 // Recibe un mensaje en la cola
    from("direct:firstRoute")
    //.setBody(constant("Hello"))
    .process(this::createEvent)
    .log("Queue -> ${body}")
    .to("jms:queue:hugoPrueba");
   
 // Recibe un mensaje en la cola
    from("direct:secondRoute")
    //.setBody(constant("Hello"))
    .process(this::createEventTwo)
    .log("Queue -> ${body}")
    .to("jms:queue:hugoPrueba");
    
  }
  
  public void createEvent(Exchange exchange) {
    
    person = Person.builder().id(1).name("Hugo").lastName("Pérez").age(21).build();
    
    PersonDto personDto = AssemblerPost.convertToDto(person); 
    
    String personString = gson.toJson(personDto);

    exchange.getIn().setBody(personString);
    
  }
  
  public void createEventTwo(Exchange exchange) throws JsonMappingException, JsonProcessingException {
    
    mapper = new ObjectMapper();

    String json = "[{ \"name\" : \"Hugo\", \"lastName\" : \"Pérez\" }]";    

    Object objectJson = mapper.readValue(json, Object.class); 
    
    staff = Staff.builder().id(1).content(objectJson).build();
    
    String staffJson = gson.toJson(staff);
    
    exchange.getIn().setBody(staffJson);
    
  }
  
}
