package org.mutualser.sprinboot.app.repository;

import org.mutualser.sprinboot.app.domain.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long>{
}
