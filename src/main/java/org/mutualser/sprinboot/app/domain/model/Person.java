package org.mutualser.sprinboot.app.domain.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Table(name = "person")
@Entity
@Builder
public class Person extends BaseEntity {
  
  @JsonProperty
  @Id
  @GeneratedValue(strategy=GenerationType.AUTO)
  private Integer id;
  
  @JsonProperty
  @Column(name = "NAME")
  private String name;
  
  @JsonProperty
  @Column(name = "LAST_NAME")
  private String lastName;
  
  @JsonProperty
  @Column(name = "AGE")
  private Integer age;
   
}
