package guru.springframework.sfgpetclinic.repositories;

import org.springframework.data.repository.CrudRepository;

// Will be automatically available in the Spring context as it is a JPA entity
public interface Specialty extends CrudRepository<Specialty, Long> {
}
