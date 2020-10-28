package org.mutualser.sprinboot.app.service.internal;

import java.util.List;
import org.mutualser.sprinboot.app.domain.model.Person;
import org.mutualser.sprinboot.app.repository.PersonRepository;
import org.mutualser.sprinboot.app.service.PersonService;
import org.springframework.stereotype.Service;

@Service
public class DefaultPersonService implements PersonService {

  private final PersonRepository personRepository;
  
  public DefaultPersonService(PersonRepository personRepository) {
    this.personRepository = personRepository;
  }
  
  @Override
  public List<Person> findAllPerson() {
    return personRepository.findAll();
  }

  @Override
  public void savePerson(Person person) {
    personRepository.save(person);
  }

}
