package tn.iit.control;

import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import tn.iit.entity.Client;
import tn.iit.entity.User;
import tn.iit.service.ClientService;
import tn.iit.service.UserService;

@RestController
public class ClientController {

    @Autowired
    private ClientService clientService;
    @Autowired
    private UserService userService;

    @GetMapping(value = "/add-client-form")
    public ModelAndView showAddConventionForm() {
        ModelAndView modelAndView = new ModelAndView();
        Client client = new Client();
        modelAndView.addObject("client", client);
        modelAndView.setViewName("add-client");
        return modelAndView;
    }
   
    @PostMapping(value = "/save-client")
    public ModelAndView saveClient(@Valid Client client, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView(new RedirectView());
        Client clientExists = clientService.findClientByCin(client.getCin());
        if (clientExists != null) {
            bindingResult
                    .rejectValue("cin", "error.client",
                            "There is already a Customer registered with the cin provided");
        }
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("add-client");
        } else {       
        User user = new User();
        user.setActive(false);
        user.setEmail(client.getEmail());
        user.setName(client.getPrenom());
        user.setLastName(client.getNom());
        user.setUserName(client.getCin());
        user.setPassword(client.getCin());
        userService.saveUserClient(user);
        client.setUser(user);
        clientService.saveClient(client);
        modelAndView.addObject("successMessage", "Customer has been added successfully");
        modelAndView.setViewName("redirect:list-clients");
        }
        return modelAndView;

    }

    @GetMapping(value = "/list-clients")
    public ModelAndView getAll(String searchValue) {
        ModelAndView modelAndView = new ModelAndView();
        List<Client> clients;
        if (searchValue != null && !searchValue.isEmpty()) {
            clients = clientService.findByCin(searchValue.trim());
        } else {
            clients = clientService.findAll();
        }
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByUserName(auth.getName());
        modelAndView.addObject("searchValue", searchValue);
        modelAndView.addObject("userName", "Welcome  " + user.getName() + " " + user.getLastName());
        modelAndView.addObject("clients", clients);
        modelAndView.setViewName("list-clients");
        return modelAndView;
    }

    @GetMapping("/delete-client/{id}")
    public ModelAndView deleteClient(@PathVariable("id") long id) throws Exception {
        clientService.deleteClient(id);
        List<Client> clients = clientService.findAll();
        ModelAndView model = new ModelAndView(new RedirectView("/list-clients"));
        model.addObject("clients", clients);
        return model;
    }

    @PostMapping(value = "/update-client")
    public ModelAndView updateClient(Client client, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView(new RedirectView());
        Client clientExists = clientService.findClientByCin(client.getCin());
        if (clientExists != null) {
            bindingResult
                    .rejectValue("cin", "error.client",
                            "There is already a Customer registered with the cin provided");
        }
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("edit-client");
        } else { 
        clientService.saveClient(client);
        modelAndView.addObject("successMessage", "Customer has been registered successfully");
        modelAndView.setViewName("redirect:list-clients");
        }
        return modelAndView;
    }

    @GetMapping(value = "/edit-client-form/{id}")
    public ModelAndView showUpdateForm(@PathVariable("id") long id, ModelAndView modelAndView) throws Exception {
        Client client = clientService.findclienById(id);
        modelAndView.addObject("client", client);
        modelAndView.setViewName("edit-client");
        return modelAndView;
    }

}
