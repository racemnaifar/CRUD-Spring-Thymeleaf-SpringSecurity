package tn.iit.dao;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.iit.entity.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

	Client findByCin(String cin);
   List<Client> findByCinContainingIgnoreCase(String searchValue);
}
