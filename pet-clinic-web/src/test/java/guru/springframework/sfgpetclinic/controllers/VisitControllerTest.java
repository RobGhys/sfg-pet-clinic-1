package guru.springframework.sfgpetclinic.controllers;

import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.model.Pet;
import guru.springframework.sfgpetclinic.services.PetService;
import guru.springframework.sfgpetclinic.services.VisitService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.util.UriTemplate;

import java.net.URI;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class VisitControllerTest {
    private static final String PETS_CREATE_OR_UPDATE_VISIT_FORM = "pets/createOrUpdateVisitForm";
    private static final String REDIRECT_OWNERS_1 = "redirect:/owners/{ownerId}";
    private static final String YET_ANOTHER_VISIT_DESCRIPTION = "yet another visit";

    @Mock
    PetService petService;
    @Mock
    VisitService visitService;

    @InjectMocks
    VisitController visitController;

    private MockMvc mockMvc;

    // "UriTemplate": Representation of a URI template that can be expanded with URI variables via expand(Map), expand(Object[]),
    // or matched to a URL via match(String).
    private final UriTemplate visitsUriTemplate = new UriTemplate("/owners/{ownerId}/pets/{petId}/visits/new");
    private final Map<String, String> uriVariables = new HashMap<>();
    private URI visitsUri;

    @BeforeEach
    void setUp() {
        Long petId = 1L;
        Long ownerId = 1L;

        // Set up a pet with owner
        when(petService.findById(anyLong())).thenReturn(Pet.builder()
            .id(petId).birthDate(LocalDate.of(2015, 05, 20)).name("Sachouille").visits(new HashSet<>())
            .owner(Owner.builder().id(ownerId).lastName("Dupont").firstName("Albert").build()).build());

        // Map owner's rep elements with test data for owner in Map<String, String> uriVariables
        uriVariables.clear();
        uriVariables.put("ownerId", ownerId.toString());
        uriVariables.put("petId", petId.toString());
        visitsUri = visitsUriTemplate.expand(uriVariables); // Given the Map of variables, expands this template into a URI

        mockMvc = MockMvcBuilders.standaloneSetup(visitController).build();
    }

    // Test for GET method
    @Test
    void initNewVisitForm() throws Exception {
        mockMvc.perform(get(visitsUri))
                .andExpect(status().isOk())
                .andExpect(view().name(PETS_CREATE_OR_UPDATE_VISIT_FORM));
    }

    // Test for POST method
    @Test
    void processNewVisitForm() throws Exception {
        mockMvc.perform(post(visitsUri)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("date", "2015-05-20")
                .param("description", YET_ANOTHER_VISIT_DESCRIPTION))
            .andExpect(status().is3xxRedirection())
            .andExpect(view().name(REDIRECT_OWNERS_1))
            .andExpect(model().attributeExists("visit"));
    }
}