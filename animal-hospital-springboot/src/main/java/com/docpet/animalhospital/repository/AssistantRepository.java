package com.docpet.animalhospital.repository;

import com.docpet.animalhospital.domain.Assistant;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AssistantRepository extends JpaRepository<Assistant, Long> {
    @Query("select assistant from Assistant assistant left join fetch assistant.user where assistant.user.login = ?#{authentication.name}")
    List<Assistant> findByUserIsCurrentUser();
    
    @Query("select assistant from Assistant assistant left join fetch assistant.user where assistant.user.login = ?1")
    List<Assistant> findAllByUser_Login(String login);
    
    @Query("select assistant from Assistant assistant left join fetch assistant.user where assistant.user.login = ?1 order by assistant.id asc")
    java.util.List<Assistant> findAssistantsByUser_Login(String login);
    
    @Query("select assistant from Assistant assistant left join fetch assistant.user where assistant.id = ?1")
    Optional<Assistant> findByIdWithUser(Long id);
    
    @Query("select assistant from Assistant assistant left join fetch assistant.user")
    List<Assistant> findAllWithUser();
    
    default Optional<Assistant> findFirstByUser_Login(String login) {
        java.util.List<Assistant> assistants = findAssistantsByUser_Login(login);
        return assistants.isEmpty() ? Optional.empty() : Optional.of(assistants.get(0));
    }
    
    // Alias method để tương thích với code hiện tại
    default Optional<Assistant> findByUser_Login(String login) {
        return findFirstByUser_Login(login);
    }
}

