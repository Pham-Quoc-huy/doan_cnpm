package com.docpet.animalhospital.repository;

import com.docpet.animalhospital.domain.AppointmentAssistant;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;

@Repository
public interface AppointmentAssistantRepository extends JpaRepository<AppointmentAssistant, Long> {

    @EntityGraph(attributePaths = {"appointment", "assistant", "assistant.user"})
    List<AppointmentAssistant> findAll();

    @Query("select aa from AppointmentAssistant aa where aa.appointment.id = :appointmentId")
    @EntityGraph(attributePaths = {"appointment", "assistant", "assistant.user"})
    List<AppointmentAssistant> findByAppointmentId(@Param("appointmentId") Long appointmentId);

    @Query("select aa from AppointmentAssistant aa where aa.assistant.id = :assistantId")
    @EntityGraph(attributePaths = {"appointment", "assistant", "assistant.user"})
    List<AppointmentAssistant> findByAssistantId(@Param("assistantId") Long assistantId);

    @Query("select aa from AppointmentAssistant aa where aa.appointment.id = :appointmentId and aa.assistant.id = :assistantId")
    @EntityGraph(attributePaths = {"appointment", "assistant", "assistant.user"})
    Optional<AppointmentAssistant> findByAppointmentIdAndAssistantId(
        @Param("appointmentId") Long appointmentId,
        @Param("assistantId") Long assistantId
    );

    @Query("select aa from AppointmentAssistant aa " +
           "join fetch aa.appointment a " +
           "join fetch aa.assistant ass " +
           "join fetch ass.user " +
           "where aa.assistant.id = :assistantId")
    List<AppointmentAssistant> findByAssistantIdWithRelations(@Param("assistantId") Long assistantId);
}

