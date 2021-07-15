package guru.springframework.sfgpetclinic.bootstrap;

import guru.springframework.sfgpetclinic.model.*;
import guru.springframework.sfgpetclinic.services.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component // Becomes a Spring bean
public class DataLoader implements CommandLineRunner {
    private final OwnerService ownerService;
    private final VetService vetService;
    private final PetTypeService petTypeService;
    private final SpecialtyService specialtyService;
    private final VisitService visitService;

    // Variables are injected in the controller
    // Spring will wire-in those components, as we annotated PetServiceMap and OwnerServiceMap with @Service.
    public DataLoader(OwnerService ownerService, VetService vetService, PetTypeService petTypeService, SpecialtyService specialtyService, VisitService visitService) {
        this.ownerService = ownerService;
        this.vetService = vetService;
        this.petTypeService = petTypeService;
        this.specialtyService = specialtyService;
        this.visitService = visitService;
    }

    @Override
    public void run(String... args) throws Exception {
        // Check whether there are some Pet Types in DB or not
        int countNbrPetType = petTypeService.findAll().size();
        if (countNbrPetType == 0) {
            loadData();
        }
    }

    private void loadData() {
        PetType dog = new PetType();
        dog.setName("Dog");
        PetType savedDogPetType = petTypeService.save(dog);

        PetType cat = new PetType();
        cat.setName("Cat");
        PetType savedCatPetType = petTypeService.save(cat);

        Specialty radiology = new Specialty();
        radiology.setDescription("Radiology");
        Specialty savedRadiology = specialtyService.save(radiology);

        Specialty surgery = new Specialty();
        surgery.setDescription("Surgery");
        Specialty savedSurgery = specialtyService.save(surgery);

        Specialty dentistry = new Specialty();
        dentistry.setDescription("Dentistry");
        Specialty savedDentistry = specialtyService.save(dentistry);

        // We don't ask explicity to create an ID value.
        // They get generated automatically through the AbstractMapService "save" method.
        Owner owner1 = new Owner();
        owner1.setFirstName("Rob");
        owner1.setLastName("Ghyselinck");
        owner1.setAddress("Rue Dr D");
        owner1.setCity("Charleroi");
        owner1.setTelephone("0475757575");

        Pet robsPet = new Pet();
        robsPet.setPetType(savedDogPetType);
        robsPet.setOwner(owner1);
        robsPet.setBirthDate(LocalDate.now());
        robsPet.setName("Le chieur-pisseur");
        owner1.getPets().add(robsPet);

        ownerService.save(owner1);

        Owner owner2 = new Owner();
        owner2.setFirstName("Caroline");
        owner2.setLastName("Hazard");
        owner2.setAddress("Rue Dr D");
        owner2.setCity("Charleroi");
        owner2.setTelephone("0498989898");

        Pet carolinesPet = new Pet();
        carolinesPet.setPetType(savedCatPetType);
        carolinesPet.setOwner(owner2);
        carolinesPet.setBirthDate(LocalDate.now());
        carolinesPet.setName("Poupounsss");
        owner2.getPets().add(carolinesPet);

        ownerService.save(owner2);

        Visit catVisit = new Visit();
        catVisit.setPet(carolinesPet);
        catVisit.setDate(LocalDate.now());
        catVisit.setDescription("Chat fatigué aujourd'hui");

        visitService.save(catVisit);

        System.out.println("Loaded Owners successfully...");

        Vet vet1 = new Vet();
        vet1.setFirstName("Nico");
        vet1.setLastName("Haha");
        vet1.getSpecialties().add(savedRadiology);
        vetService.save(vet1);

        Vet vet2 = new Vet();
        vet2.setFirstName("Gabb");
        vet2.setLastName("Angel");
        vet2.getSpecialties().add(savedDentistry);
        vetService.save(vet2);

        System.out.println("Loaded Vets successfully...");
    }
}
