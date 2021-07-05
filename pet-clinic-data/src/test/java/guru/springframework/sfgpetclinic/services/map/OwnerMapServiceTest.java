package guru.springframework.sfgpetclinic.services.map;

import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.model.PetType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class OwnerMapServiceTest {
    OwnerMapService ownerMapService;
    public final Long ownerId = 1L;
    public final String lastName = "Dupont";

    @BeforeEach
    void setUp() {
        ownerMapService = new OwnerMapService(new PetTypeMapService(), new PetMapService());
        ownerMapService.save(Owner.builder().id(ownerId).lastName(lastName).build());
    }

    @Test
    void findAll() {
        Set<Owner> owners = ownerMapService.findAll();
        assertEquals(1, owners.size());
    }

    @Test
    void findById() {
        Owner owner = ownerMapService.findById(ownerId);
        assertEquals(ownerId, owner.getId());
    }

    @Test
    void saveWithExistingId() {
        Long id = 2L;
        Owner unsavedOwner = Owner.builder().id(id).build();
        Owner savedOwnerWithId = ownerMapService.save(unsavedOwner);
        assertEquals(id, savedOwnerWithId.getId());
    }

    @Test
    void saveWithoutId() {
        Owner unsavedOwner = Owner.builder().build();
        Owner savedOwnerWithoutId = ownerMapService.save(unsavedOwner);
        assertNotNull(savedOwnerWithoutId);
        assertNotNull(savedOwnerWithoutId.getId());
    }

    @Test
    void delete() {
        // Delete Owner with Id # 1 --> By the object
        ownerMapService.delete(ownerMapService.findById(ownerId));

        // We expect a size of 0, given the @BeforeEach setUp() method will create only 1 user, with ownerId as id
        assertEquals(0, ownerMapService.findAll().size());
    }

    @Test
    void deleteById() {
        // Delete Owner with Id # 1 --> By the Id
        ownerMapService.deleteById(ownerId);
        assertEquals(0, ownerMapService.findAll().size());

    }

    @Test
    void findByLastName() {
        Owner dupont = ownerMapService.findByLastName(lastName);
        assertNotNull(dupont);
        assertEquals(ownerId, dupont.getId());
    }

    @Test
    void findByLastNameNotFound() {
        Owner coucou = ownerMapService.findByLastName("coucou");
        assertNull(coucou);
    }
}