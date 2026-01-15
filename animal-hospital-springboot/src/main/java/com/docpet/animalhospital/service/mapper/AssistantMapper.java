package com.docpet.animalhospital.service.mapper;

import com.docpet.animalhospital.domain.Assistant;
import com.docpet.animalhospital.service.dto.AssistantDTO;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface AssistantMapper extends EntityMapper<AssistantDTO, Assistant> {
    @Mapping(target = "userId", source = "user.id")
    AssistantDTO toDto(Assistant s);

    @Mapping(target = "user", ignore = true)
    Assistant toEntity(AssistantDTO assistantDTO);
}



