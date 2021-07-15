package guru.springframework.sfgpetclinic.controllers;

import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.services.OwnerService;
import guru.springframework.sfgpetclinic.services.VetService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;

@RequestMapping("/owners")
@Controller
public class OwnerController {
    private static final String VIEWS_OWNER_CREATE_OR_UPDATE_FORM = "owners/createOrUpdateOwnerForm";
    private final OwnerService ownerService;

    // The OwnerController will inject the OwnerService
    // No need to call @Autowired as it is done automatically by Spring
    public OwnerController(OwnerService ownerService) {
        this.ownerService = ownerService;
    }

    // Spring binds http to a Java object
    // We don't want web frames to address the id property
    // Web data binder is injected into the controller
    @InitBinder
    public void setAllowedFields(WebDataBinder dataBinder) {
        dataBinder.setDisallowedFields("id");
    }

    @RequestMapping("/find")
    public String findOwners(Model model) {
        model.addAttribute("owner", Owner.builder().build());
        return "owners/findOwners";
    }

    @RequestMapping
    public String processFindForm(Owner owner, BindingResult result, Model model) {
        // GET request made without parameters for /owners will return all records
        if (owner.getLastName() == null) {
            owner.setLastName(""); // Will give the broadest possible search for lastName
        }

        // Find owners by lastName
        // We append "%" before and after last name, so that users who search for a name don't have to type it
        List<Owner> results = ownerService.findAllByLastNameLike("%" + owner.getLastName() + "%");

        // No owners found, then reject the value
        if (results.isEmpty()) {
            result.rejectValue("lastName", "notFound", "not found");

            return "owners/findOwners";
        }
        // Found 1 owner, then redirect to the owners
        else if (results.size() == 1) {
            owner = results.get(0);

            return "redirect:/owners/" + owner.getId();
        }
        // Found more than 1 owner, then redirect to owners/ownersList
        else {
            model.addAttribute("selections", results);

            return "owners/ownersList";
        }
    }

    @GetMapping("/{ownerId}")
    public ModelAndView showOwner(@PathVariable("ownerId") Long ownerId) {
        ModelAndView mav = new ModelAndView("owners/ownerDetails");
        mav.addObject(ownerService.findById(ownerId));

        return mav;
    }

    @GetMapping("/new")
    public String initCreationForm(Model model) {
        model.addAttribute("owner", Owner.builder().build());

        return VIEWS_OWNER_CREATE_OR_UPDATE_FORM;
    }

    @PostMapping("/new")
    public String processCreationForm(@Valid Owner owner, BindingResult result) {
        if (result.hasErrors()) {
            return VIEWS_OWNER_CREATE_OR_UPDATE_FORM;
        }
        else {
            Owner savedOwner = ownerService.save(owner);

            return "redirect:/owners/" + savedOwner.getId();
        }
    }

    @GetMapping("/{ownerId}/edit")
    public String initUpdateOwnerForm(@PathVariable Long ownerId, Model model) {
        // Take the model attribute and populate it from the ownerService
        model.addAttribute(ownerService.findById(ownerId));

        return VIEWS_OWNER_CREATE_OR_UPDATE_FORM;
    }

    @PostMapping("/{ownerId}/edit")
    public String processUpdateOwnerForm(@Valid Owner owner,
                                         BindingResult result,
                                         @PathVariable Long ownerId) {
        if (result.hasErrors()) {
            return VIEWS_OWNER_CREATE_OR_UPDATE_FORM;
        }
        else {
            owner.setId(ownerId);
            Owner savedOwner = ownerService.save(owner);

            return "redirect:/owners/" + savedOwner.getId();
        }
    }
}