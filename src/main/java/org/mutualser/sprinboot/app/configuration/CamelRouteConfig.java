package org.mutualser.sprinboot.app.configuration;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.mutualser.sprinboot.app.domain.model.Person;
import org.mutualser.sprinboot.app.service.PersonService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

@Component("RouteConfigRest")
public class CamelRouteConfig extends RouteBuilder{
  
  private final PersonService personService;

  @Value("${server.port}")
  private  String serverPort;
  
  @Value("${server.localhost}")
  private  String localHost;
  
  public CamelRouteConfig(PersonService personService) {
    this.personService = personService;
  }
  
  @Override
  public void configure() throws Exception {
    this.configureApiRest();
  }
  
  private void configureApiRest() {
    
    restConfiguration().host(localHost)
    .port(serverPort)
    .component("servlet").enableCORS(true)
    .apiProperty("api.title", "REST API")
    .apiProperty("api.version", "1.0")
    .bindingMode(RestBindingMode.json);

    // Cuando llegue una peticion a esa ruta, se enrute a direct:...
    rest("ws_rest_camel")
    .get("/getAllPerson")
    .route()
    .to("direct:processGetAllPerson")
    .description("return all persons");
    
    rest("ws_rest_camel_post").post("/person").produces(MediaType.APPLICATION_JSON_VALUE).type(Person.class)
    .route().routeId("postPersonRoute").log("--- binded ${body} ---")
    .to("jpa:" + Person.class.getName() + "?usePersist=true")
    .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(201)).setBody(constant(null));
    
    from("direct:processGetAllPerson")
    .setBody(constant("PETICION PROCESADA"))
    .log("cuerpo de la peticion -> ${body}")
    .bean(personService,"findAllPerson")
    .end();
    

  }
  
  
}
