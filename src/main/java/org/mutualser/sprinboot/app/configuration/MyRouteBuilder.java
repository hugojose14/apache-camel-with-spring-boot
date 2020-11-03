package org.mutualser.sprinboot.app.configuration;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.apache.camel.component.jackson.ListJacksonDataFormat;
import org.mutualser.sprinboot.app.domain.model.Post;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

//RouteBuilder -> Es donde definimos o construimos nuestras rutas

@Component
public class MyRouteBuilder extends RouteBuilder{
    
  private JacksonDataFormat format;
    
  private static final String URL_ENDPOINT = "https://jsonplaceholder.typicode.com/posts";
  
  private static final String BODY_IS_NULL = "El Body es null, no se hace un SPLIT";

  private static final String BODY_IS_NOT_NULL = "El Body no es null";
  
  public MyRouteBuilder() {
    format = new  ListJacksonDataFormat(Post.class);
  }
  
  @Override
  public void configure() throws Exception {
    try {
      
      from("timer:simple?period=3000").to("direct:consumirApiRest").end();
      
      from("direct:consumirApiRest").to(URL_ENDPOINT)
          .setHeader(Exchange.HTTP_METHOD, constant(HttpMethod.GET)).unmarshal(format)
          //.process(new MyProcessor())
          .setBody(simple("${body}")) //Establecer un body vía simple
          .choice() // <- Permite abrir sentencias de validación (If)
            .when(body().isNull())
              .log(BODY_IS_NULL)
              //Flujo de procesamiento
            .otherwise()
              .split(body()) // <- Cada elemento de la lista va a disparar un flujo de procesamiento (hacer un split del body)
                .log("split del body")
                .process(this::createEvent)
          .end();
          
    } catch (Exception e) {
      log.info(e.getMessage());
    }
     
  }
  
  private void createEvent(Exchange exchange) {
    Post[] post = exchange.getIn().getBody(Post[].class);
    if(post != null) {
      log.info(BODY_IS_NOT_NULL);
      
      for (Post x : post) {

        StringBuilder postInfo = new StringBuilder();

        postInfo.append("{id: ").append(x.getId().toString()).append(",").append("\n")
            .append("userId: ").append(x.getUserId().toString()).append(",").append("\n")
            .append("title: ").append(x.getTitle()).append(",").append("\n").append("body: ")
            .append(x.getBody()).append("}").toString();

        log.info(postInfo.toString());

      }
      
    }
  }
  
}
