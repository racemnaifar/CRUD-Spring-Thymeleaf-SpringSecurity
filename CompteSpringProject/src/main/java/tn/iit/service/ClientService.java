package tn.iit.service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.iit.dao.ClientRepository;
import tn.iit.entity.Client;

@Service
public class ClientService {
	 private ClientRepository clientRepository;

	    @Autowired
	    public ClientService(ClientRepository clientRepository) {
	        this.clientRepository = clientRepository;
	    }

	    public Client findClientByCin(String cin) {
	        return clientRepository.findByCin(cin);
	    }
	    
	    public Optional<Client> findClientById(Long id) throws Exception {
	        return clientRepository.findById(id);
	    }

	    public Client findclienById(Long id) throws Exception {
	        return clientRepository.findById(id).orElseThrow(() -> new Exception("Client not found"));
	    }

	    public Client saveClient(Client client) {
	        return clientRepository.saveAndFlush(client);
	    }
	    
	    public List<Client> findAll(){
	        return clientRepository.findAll();
	    }
	
	    public Collection<Client> findAlla(){
	        return clientRepository.findAll();
	    }

	    
	    public List<Client> findByCin(String searchValue) {
	        return clientRepository.findByCinContainingIgnoreCase(searchValue);
	    }
	    

	    public void deleteClient(Long id) throws Exception {
	        Client client = clientRepository.findById(id).orElseThrow(() -> new Exception("Client not found"));
	        clientRepository.delete(client);
	    }
	}