package guru.springframework.sfgpetclinic.repositories;

import guru.springframework.sfgpetclinic.model.Visit;
import org.springframework.data.repository.CrudRepository;

// Will be automatically available in the Spring context as it is a JPA entity
public interface VisitRepository extends CrudRepository<Visit, Long> {
}
