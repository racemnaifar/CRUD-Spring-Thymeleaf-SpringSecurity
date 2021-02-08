package tn.iit.dao;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import tn.iit.entity.Compte;

@Repository
public interface CompteRepository  extends JpaRepository<Compte, Long> {
    List<Compte> findAllByOrderByRibDesc();
    List<Compte> findByRibContaining(Long searchValue);
    Compte findByRib(Long searchValue);

    @Query("Select c from Compte c where c.client.user.id = ?1")
    List<Compte> getCompteByClientById(Long id);
    List<Compte> findByClientUserId(Long id);
}
