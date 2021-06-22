package guru.springframework.sfgpetclinic.bootstrap;

import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.model.Vet;
import guru.springframework.sfgpetclinic.services.OwnerService;
import guru.springframework.sfgpetclinic.services.VetService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component // Becomes a Spring bean
public class DataLoader implements CommandLineRunner {
    private final OwnerService ownerService;
    private final VetService vetService;

    // Variables are injected in the controller
    // Spring will wire-in those components, as we annotated PetServiceMap and OwnerServiceMap with @Service.
    public DataLoader(OwnerService ownerService, VetService vetService) {
        this.ownerService = ownerService;
        this.vetService = vetService;
    }

    @Override
    public void run(String... args) throws Exception {
        // We don't ask explicity to create an ID value. They get generated automatically through the AbstractMapService "save" method.
        Owner owner1 = new Owner();
        owner1.setFirstName("Rob");
        owner1.setLastName("Ghyselinck");
        ownerService.save(owner1);

        Owner owner2 = new Owner();
        owner2.setFirstName("Caroline");
        owner2.setLastName("Hazard");
        ownerService.save(owner2);

        System.out.println("Loaded Owners successfully...");

        Vet vet1 = new Vet();
        vet1.setFirstName("Nico");
        vet1.setLastName("Haha");
        vetService.save(vet1);

        Vet vet2 = new Vet();
        vet2.setFirstName("Gabb");
        vet2.setLastName("Angel");
        vetService.save(vet2);

        System.out.println("Loaded Vets successfully...");
    }
}
