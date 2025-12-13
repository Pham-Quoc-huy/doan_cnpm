package com.docpet.animalhospital.service.mapper;

import com.docpet.animalhospital.domain.User;
import com.docpet.animalhospital.domain.Vet;
import com.docpet.animalhospital.service.dto.UserDTO;
import com.docpet.animalhospital.service.dto.VetDTO;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface VetMapper extends EntityMapper<VetDTO, Vet> {
    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "firstName", source = "user.firstName")
    @Mapping(target = "lastName", source = "user.lastName")
    @Mapping(target = "fullName", expression = "java(buildFullName(s))")
    VetDTO toDto(Vet s);

    @Mapping(target = "user", ignore = true)
    Vet toEntity(VetDTO vetDTO);

    default String buildFullName(Vet vet) {
        if (vet == null || vet.getUser() == null) {
            return null;
        }
        String firstName = vet.getUser().getFirstName();
        String lastName = vet.getUser().getLastName();
        
        if (firstName == null && lastName == null) {
            return null;
        }
        
        String first = firstName != null ? firstName.trim() : "";
        String last = lastName != null ? lastName.trim() : "";
        String fullName = (first + " " + last).trim();
        
        return fullName.isEmpty() ? null : fullName;
    }
}
