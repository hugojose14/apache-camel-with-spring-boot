package org.mutualser.sprinboot.app.domain.model;

import java.sql.Timestamp;
import javax.persistence.Column;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;
import org.hibernate.annotations.UpdateTimestamp;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class BaseEntity {

  @Column(updatable = false, name = "CREATED_AT")
  @Generated(GenerationTime.INSERT)
  @CreationTimestamp
  private Timestamp  createdAt;
  
  @Column(name = "UPDATED_AT")
  @UpdateTimestamp
  private Timestamp  updatedAt;
}
