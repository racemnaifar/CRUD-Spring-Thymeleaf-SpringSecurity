package tn.iit.control;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import tn.iit.entity.Client;
import tn.iit.entity.Compte;
import tn.iit.entity.User;
import tn.iit.service.ClientService;
import tn.iit.service.CompteService;
import tn.iit.service.UserService;

@RestController
public class CompteController {
	
	@Autowired
    private CompteService compteService;
    @Autowired
    private UserService userService;
    @Autowired
    private ClientService clientService;
    

    @GetMapping(value = "/add-compte-form")
    public ModelAndView showAddCompteForm() {
        ModelAndView modelAndView = new ModelAndView();
        HashMap compteTypeList = compteService.compteTypes();
        Compte compte = new Compte();
        modelAndView.addObject("compte", compte);
        modelAndView.addObject("comptesType", compteTypeList);
        modelAndView.setViewName("add-compte");
        return modelAndView;
    }

    
    @PostMapping(value = "/save-compte")
    public ModelAndView saveCompte(Compte compte, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView(new RedirectView()); 
      
        Compte compteExists = compteService.findCompteRib(compte.getRib());
        if (compteExists != null) {
            bindingResult
                    .rejectValue("rib", "error.compte",
                            "There is already a Account registered with the cin provided");
        }
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("add-compte");
        } else {   
        compteService.saveCompte(compte);
        modelAndView.addObject("successMessage", "Account has been added successfully");
        modelAndView.setViewName("redirect:list-comptes");}
        return modelAndView;
    }

    @GetMapping(value = "/list-comptes")
    public ModelAndView getAll(String searchValue) {
        ModelAndView modelAndView = new ModelAndView();
        List<Compte> comptes;
        if (searchValue != null && !searchValue.isEmpty()) {
            comptes = compteService.findByRib(Long.parseLong(searchValue.trim()));
        } else {
            comptes = compteService.findAll();
        }
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByUserName(auth.getName());
        modelAndView.addObject("searchValue", searchValue);
        modelAndView.addObject("userName", "Welcome  " + user.getName() + " " + user.getLastName());
        modelAndView.addObject("comptes", comptes);
        modelAndView.setViewName("list-comptes");
        return modelAndView;
    }

    @GetMapping("/delete-compte/{id}")
    public ModelAndView deleteCompte(@PathVariable("id") long id) throws Exception {
        compteService.deleteCompte(id);
        List<Compte> comptes = compteService.findAll();
        ModelAndView model = new ModelAndView(new RedirectView("/list-comptes"));
        model.addObject("comptes", comptes);
        return model;
    }

    @PostMapping(value = "/update-compte")
    public ModelAndView updateCompte(Compte compte, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView(new RedirectView());
        compteService.saveCompte(compte);
        modelAndView.addObject("successMessage", "Account has been registered successfully");
        modelAndView.setViewName("redirect:list-comptes");
        return modelAndView;
    }

    @GetMapping(value = "/edit-compte-form/{id}")
    public ModelAndView showUpdateForm(@PathVariable("id") long id, ModelAndView modelAndView) throws Exception {
    	List<Client> clients;
        HashMap conventionTypeList = compteService.compteTypes();
        clients = clientService.findAll();
    	Compte compte = compteService.findCompById(id);
    	modelAndView.addObject("clients", clients);
        modelAndView.addObject("comptesType", conventionTypeList);
        modelAndView.addObject("compte", compte);
        modelAndView.setViewName("edit-compte");
        return modelAndView;
    }
    
    
   @RequestMapping(value="/ajaxclientSearchGeneral", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, List<Client>> getClients()
    {
	 List<Client> clients = clientService.findAll();	  
	 Map<String, List<Client>> client = new HashMap<String, List<Client>>();
	 client.put("clients", clients);
	 return client;
    }
    
   @GetMapping(value = "/list-client-comptes")
   public ModelAndView getAllClientCompte(String searchValue) {
       ModelAndView modelAndView = new ModelAndView();
       Authentication auth = SecurityContextHolder.getContext().getAuthentication();
       User user = userService.findUserByUserName(auth.getName());
       List<Compte> comptes;
       if (searchValue != null && !searchValue.isEmpty()) {
           comptes = compteService.findByRib(Long.parseLong(searchValue.trim()));
       } else {
           comptes = compteService.findAllByClientById(user.getId());
       }
       	System.out.println(user.getId());
       modelAndView.addObject("searchValue", searchValue);
       modelAndView.addObject("userName", "Welcome  " + user.getName() + " " + user.getLastName());
       modelAndView.addObject("comptes", comptes);
       modelAndView.setViewName("list-client-comptes");
       return modelAndView;
   }
	
}