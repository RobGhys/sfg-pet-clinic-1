package guru.springframework.sfgpetclinic.services.springdatajpa;

import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.repositories.OwnerRepository;
import guru.springframework.sfgpetclinic.repositories.PetRepository;
import guru.springframework.sfgpetclinic.repositories.PetTypeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OwnerSDJpaServiceTest {
    public static final String LAST_NAME = "Ghys";
    @Mock
    OwnerRepository ownerRepository;
    @Mock
    PetRepository petRepository;
    @Mock
    PetTypeRepository petTypeRepository;

    @InjectMocks
    OwnerSDJpaService service;

    Owner returnOwner;

    @BeforeEach
    void setUp() {
        returnOwner = Owner.builder().id(1l).lastName(LAST_NAME).build();
    }

    @Test
    void findAll() {
        Set<Owner> returnOwnersSet = new HashSet<>();
        returnOwnersSet.add(Owner.builder().id(1l).build());
        returnOwnersSet.add(Owner.builder().id(2l).build());

        when(ownerRepository.findAll()).thenReturn(returnOwnersSet);

        // Invoke the @Mock and should return a Set of Owner
        Set<Owner> owners = service.findAll();

        assertNotNull(owners);
        assertEquals(2, owners.size());
    }

    @Test
    void findById() {
        // Return an Owner object or null in findById()
        // Check that we return something
        when(ownerRepository.findById(anyLong())).thenReturn(Optional.of(returnOwner));
        Owner owner = service.findById(1L);

        assertNotNull(owner);
    }

    @Test
    void findByIdNotFound() {
        // Return an Owner object or null in findById()
        // Test that we return a null
        when(ownerRepository.findById(anyLong())).thenReturn(Optional.empty());
        Owner owner = service.findById(1L);

        assertNull(owner);
    }

    @Test
    void save() {
        Owner ownerToSave = Owner.builder().id(1L).build();
        when(ownerRepository.save(any())).thenReturn(returnOwner);
        Owner savedOwner = service.save(ownerToSave);

        assertNotNull(savedOwner);
        verify(ownerRepository).save(any()); // Make sure that a Mock operation occured
    }

    @Test
    void delete() {
        service.delete(returnOwner);

        // Need to use verify(...) as the delete() method does return a void
        // Verify that the method is called one time
        // By default, verify uses times(1)
        verify(ownerRepository, times(1)).delete(any()); // Make sure that a Mock operation occured
    }

    @Test
    void deleteById() {
        service.deleteById(1L);

        // We don't specify that we want the verify to look for only 1 invocation, as it is the default value of the method (see delete() Test for explicit version)
        verify(ownerRepository).deleteById(anyLong()); // Make sure that a Mock operation occured
    }

    @Test
    void findByLastName() {
        // In the findByLastName(...) method, we are using ownerRepository
        // Hence, when this is called, we want to return the new object returnOwner
        when(ownerRepository.findByLastName(any())).thenReturn(returnOwner);

        Owner ghys = service.findByLastName(LAST_NAME);

        // Testing
        assertEquals(LAST_NAME, ghys.getLastName());
        verify(ownerRepository).findByLastName(any()); // Make sure that a Mock operation occured
    }
}