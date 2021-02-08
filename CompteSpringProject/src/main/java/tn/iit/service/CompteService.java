package tn.iit.service;


import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.iit.dao.CompteRepository;
import tn.iit.entity.Compte;
import tn.iit.entity.CompteType;

@Service
public class CompteService {
	private CompteRepository compteRepository;

    @Autowired
    public CompteService(CompteRepository compteRepository) {
        this.compteRepository = compteRepository;
    }

    public Optional<Compte> findCompteById(Long id) throws Exception {
        return compteRepository.findById(id);
    }
    
    
    public Compte findCompById(Long id) throws Exception {
        return compteRepository.findById(id).orElseThrow(() -> new Exception("Account not found"));
    }

    public Compte saveCompte(Compte compte) {
        return compteRepository.saveAndFlush(compte);
    }
    
    public List<Compte> findAll(){
        return compteRepository.findAll();
    }
    
    public List<Compte> findAllByClientById(Long id){
        return compteRepository.getCompteByClientById(id);
    }

    public List<Compte> findByRib(Long searchValue) {
        return compteRepository.findByRibContaining(searchValue);
    }
    
    public List<Compte> findCompteByRib(Long searchValue) {
        return compteRepository.findByRibContaining(searchValue);
    }
    public Compte findCompteRib(Long searchValue) {
        return compteRepository.findByRib(searchValue);
    }

    public void deleteCompte(Long id) throws Exception {
        Compte compte = compteRepository.findById(id).orElseThrow(() -> new Exception("Account not found"));
        compteRepository.delete(compte);
    }
    
    public HashMap compteTypes() {
        HashMap<CompteType, String> hashMap = new HashMap();
        Arrays.stream(CompteType.values()).forEach(CompteType -> {
            hashMap.put(CompteType, CompteType.toString().toLowerCase());
        });
        return hashMap;
    }
}