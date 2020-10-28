package org.mutualser.sprinboot.app.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.mutualser.sprinboot.app.domain.model.Post;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MyProcessor implements Processor{
  
  @SuppressWarnings("deprecation")
  @Override
  public void process(Exchange exchange) throws Exception {

    Post[] post = exchange.getIn().getBody(Post[].class);
    

    if (post != null) {
      log.info("Post no es nulll");
      
      exchange.getOut().setBody(post);
      
      for (Post x : post) {
        
        StringBuilder postInfo = new StringBuilder();
        
        postInfo.append("{id: ").append(x.getId().toString()).append(",").append("\n")
        .append("userId: ").append(x.getUserId().toString()).append(",").append("\n")
        .append("title: ").append(x.getTitle()).append(",").append("\n")
        .append("body: ").append(x.getBody()).append("}").toString();
        
        log.info(postInfo.toString());
        
      }

    } else {
      exchange.getOut().setBody(null);
      log.info("Es Null Post");
    }

  }

}
