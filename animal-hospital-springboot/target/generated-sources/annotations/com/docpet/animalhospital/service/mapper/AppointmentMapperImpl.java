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
    date = "2025-11-10T23:44:01+0700",
    comments = "version: 1.6.3, compiler: Eclipse JDT (IDE) 3.44.0.v20251023-0518, environment: Java 21.0.8 (Eclipse Adoptium)"
)
@Component
public class AppointmentMapperImpl implements AppointmentMapper {

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
    public AppointmentDTO toDto(Appointment s) {
        if ( s == null ) {
            return null;
        }

        AppointmentDTO appointmentDTO = new AppointmentDTO();

        appointmentDTO.setPet( toDtoPetId( s.getPet() ) );
        appointmentDTO.setVet( toDtoVetId( s.getVet() ) );
        appointmentDTO.setOwner( toDtoOwnerId( s.getOwner() ) );
        appointmentDTO.setAppointmentType( s.getAppointmentType() );
        appointmentDTO.setId( s.getId() );
        appointmentDTO.setLocationType( s.getLocationType() );
        appointmentDTO.setNotes( s.getNotes() );
        appointmentDTO.setStatus( s.getStatus() );
        appointmentDTO.setTimeEnd( s.getTimeEnd() );
        appointmentDTO.setTimeStart( s.getTimeStart() );
        appointmentDTO.setType( s.getType() );

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

        appointment.setAppointmentType( appointmentDTO.getAppointmentType() );
        appointment.setId( appointmentDTO.getId() );
        appointment.setLocationType( appointmentDTO.getLocationType() );
        appointment.setNotes( appointmentDTO.getNotes() );
        appointment.status( appointmentDTO.getStatus() );
        appointment.timeEnd( appointmentDTO.getTimeEnd() );
        appointment.timeStart( appointmentDTO.getTimeStart() );
        appointment.type( appointmentDTO.getType() );

        return appointment;
    }
}
