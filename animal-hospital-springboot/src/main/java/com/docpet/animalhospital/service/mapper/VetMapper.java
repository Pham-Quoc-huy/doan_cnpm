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
    VetDTO toDto(Vet s);

    @Mapping(target = "user", ignore = true)
    Vet toEntity(VetDTO vetDTO);
}
