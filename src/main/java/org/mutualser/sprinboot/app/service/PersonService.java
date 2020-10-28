package org.mutualser.sprinboot.app.service;

import java.util.List;
import org.mutualser.sprinboot.app.domain.model.Person;

public interface PersonService {
  public List<Person> findAllPerson();
  public void savePerson(Person person);
}
