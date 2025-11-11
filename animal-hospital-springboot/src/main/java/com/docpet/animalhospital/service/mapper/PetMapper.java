package com.docpet.animalhospital.service.mapper;

import com.docpet.animalhospital.domain.Owner;
import com.docpet.animalhospital.domain.Pet;
import com.docpet.animalhospital.service.dto.OwnerDTO;
import com.docpet.animalhospital.service.dto.PetDTO;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface PetMapper extends EntityMapper<PetDTO, Pet> {
    
    @Mapping(target = "owner", source = "owner", qualifiedByName = "ownerId")
    PetDTO toDto(Pet s);

    @Named("ownerId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    OwnerDTO toDtoOwnerId(Owner owner);

    @Mapping(target = "owner", ignore = true)
    Pet toEntity(PetDTO petDTO);
}
