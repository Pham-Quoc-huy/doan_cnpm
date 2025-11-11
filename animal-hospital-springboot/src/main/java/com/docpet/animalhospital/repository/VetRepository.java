package com.docpet.animalhospital.repository;

import com.docpet.animalhospital.domain.Vet;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface VetRepository extends JpaRepository<Vet, Long> {
    @Query("select vet from Vet vet where vet.user.login = ?#{authentication.name}")
    List<Vet> findByUserIsCurrentUser();
    
    @Query("select vet from Vet vet where vet.user.login = ?1")
    Optional<Vet> findByUser_Login(String login);
}

