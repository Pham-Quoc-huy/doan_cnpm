package com.docpet.animalhospital.service.mapper;

import com.docpet.animalhospital.domain.Appointment;
import com.docpet.animalhospital.domain.Owner;
import com.docpet.animalhospital.domain.Pet;
import com.docpet.animalhospital.domain.Vet;
import com.docpet.animalhospital.service.dto.AppointmentDTO;
import com.docpet.animalhospital.service.dto.OwnerDTO;
import com.docpet.animalhospital.service.dto.PetDTO;
import com.docpet.animalhospital.service.dto.VetDTO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-11-20T10:11:37+0700",
    comments = "version: 1.6.3, compiler: javac, environment: Java 17.0.12 (Oracle Corporation)"
)
@Component
public class AppointmentMapperImpl implements AppointmentMapper {

    @Override
    public List<Appointment> toEntity(List<AppointmentDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<Appointment> list = new ArrayList<Appointment>( dtoList.size() );
        for ( AppointmentDTO appointmentDTO : dtoList ) {
            list.add( toEntity( appointmentDTO ) );
        }

        return list;
    }

    @Override
    public List<AppointmentDTO> toDto(List<Appointment> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<AppointmentDTO> list = new ArrayList<AppointmentDTO>( entityList.size() );
        for ( Appointment appointment : entityList ) {
            list.add( toDto( appointment ) );
        }

        return list;
    }

    @Override
    public AppointmentDTO toDto(Appointment s) {
        if ( s == null ) {
            return null;
        }

        AppointmentDTO appointmentDTO = new AppointmentDTO();

        appointmentDTO.setPet( toDtoPetId( s.getPet() ) );
        appointmentDTO.setVet( toDtoVetId( s.getVet() ) );
        appointmentDTO.setOwner( toDtoOwnerId( s.getOwner() ) );
        appointmentDTO.setId( s.getId() );
        appointmentDTO.setTimeStart( s.getTimeStart() );
        appointmentDTO.setTimeEnd( s.getTimeEnd() );
        appointmentDTO.setType( s.getType() );
        appointmentDTO.setStatus( s.getStatus() );
        appointmentDTO.setNotes( s.getNotes() );
        appointmentDTO.setAppointmentType( s.getAppointmentType() );
        appointmentDTO.setLocationType( s.getLocationType() );

        return appointmentDTO;
    }

    @Override
    public PetDTO toDtoPetId(Pet pet) {
        if ( pet == null ) {
            return null;
        }

        PetDTO petDTO = new PetDTO();

        petDTO.setId( pet.getId() );

        return petDTO;
    }

    @Override
    public VetDTO toDtoVetId(Vet vet) {
        if ( vet == null ) {
            return null;
        }

        VetDTO vetDTO = new VetDTO();

        vetDTO.setId( vet.getId() );

        return vetDTO;
    }

    @Override
    public OwnerDTO toDtoOwnerId(Owner owner) {
        if ( owner == null ) {
            return null;
        }

        OwnerDTO ownerDTO = new OwnerDTO();

        ownerDTO.setId( owner.getId() );

        return ownerDTO;
    }

    @Override
    public Appointment toEntity(AppointmentDTO appointmentDTO) {
        if ( appointmentDTO == null ) {
            return null;
        }

        Appointment appointment = new Appointment();

        appointment.setId( appointmentDTO.getId() );
        appointment.setTimeStart( appointmentDTO.getTimeStart() );
        appointment.setTimeEnd( appointmentDTO.getTimeEnd() );
        appointment.setType( appointmentDTO.getType() );
        appointment.setStatus( appointmentDTO.getStatus() );
        appointment.setNotes( appointmentDTO.getNotes() );
        appointment.setAppointmentType( appointmentDTO.getAppointmentType() );
        appointment.setLocationType( appointmentDTO.getLocationType() );

        return appointment;
    }
}
