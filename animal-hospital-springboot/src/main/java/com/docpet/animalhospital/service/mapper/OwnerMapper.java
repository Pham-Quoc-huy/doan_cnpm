package com.docpet.animalhospital.service.mapper;

import com.docpet.animalhospital.domain.Owner;
import com.docpet.animalhospital.domain.User;
import com.docpet.animalhospital.service.dto.OwnerDTO;
import com.docpet.animalhospital.service.dto.UserDTO;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface OwnerMapper extends EntityMapper<OwnerDTO, Owner> {
    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "firstName", expression = "java(splitFirstName(s))")
    @Mapping(target = "lastName", expression = "java(splitLastName(s))")
    OwnerDTO toDto(Owner s);

    @Mapping(target = "user", ignore = true)
    @Mapping(target = "name", expression = "java(combineName(ownerDTO))")
    Owner toEntity(OwnerDTO ownerDTO);

    default String combineName(OwnerDTO ownerDTO) {
        if (ownerDTO.getName() != null && !ownerDTO.getName().trim().isEmpty()) {
            return ownerDTO.getName();
        }
        // Combine firstName and lastName if name is not provided
        String firstName = ownerDTO.getFirstName() != null ? ownerDTO.getFirstName().trim() : "";
        String lastName = ownerDTO.getLastName() != null ? ownerDTO.getLastName().trim() : "";
        if (firstName.isEmpty() && lastName.isEmpty()) {
            return null;
        }
        return (firstName + " " + lastName).trim();
    }

    default String splitFirstName(Owner owner) {
        if (owner.getName() == null || owner.getName().trim().isEmpty()) {
            return null;
        }
        String[] parts = owner.getName().trim().split("\\s+", 2);
        return parts.length > 0 ? parts[0] : null;
    }

    default String splitLastName(Owner owner) {
        if (owner.getName() == null || owner.getName().trim().isEmpty()) {
            return null;
        }
        String[] parts = owner.getName().trim().split("\\s+", 2);
        // Nếu có nhiều hơn 1 phần, phần còn lại là lastName
        if (parts.length > 1) {
            return parts[1];
        }
        // Nếu chỉ có 1 phần, coi như đó là lastName (hoặc có thể là firstName)
        // Để đơn giản, trả về null nếu chỉ có 1 từ
        return null;
    }
}
