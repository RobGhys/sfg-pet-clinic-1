package guru.springframework.sfgpetclinic.repositories;

import guru.springframework.sfgpetclinic.model.Owner;
import org.springframework.data.repository.CrudRepository;

// Will be automatically available in the Spring context as it is a JPA entity
public interface OwnerRepository extends CrudRepository<Owner, Long> {
}
