package guru.springframework.sfgpetclinic.repositories;

import guru.springframework.sfgpetclinic.model.Pet;
import org.springframework.data.repository.CrudRepository;

// Will be automatically available in the Spring context as it is a JPA entity
public interface PetRepository extends CrudRepository<Pet, Long> {
}
