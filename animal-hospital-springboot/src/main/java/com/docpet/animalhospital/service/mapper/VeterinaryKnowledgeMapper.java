package com.docpet.animalhospital.service.mapper;

import com.docpet.animalhospital.domain.VeterinaryKnowledge;
import com.docpet.animalhospital.service.dto.VeterinaryKnowledgeDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface VeterinaryKnowledgeMapper extends EntityMapper<VeterinaryKnowledgeDTO, VeterinaryKnowledge> {

    VeterinaryKnowledgeDTO toDto(VeterinaryKnowledge veterinaryKnowledge);

    VeterinaryKnowledge toEntity(VeterinaryKnowledgeDTO veterinaryKnowledgeDTO);

    default VeterinaryKnowledge fromId(Long id) {
        if (id == null) {
            return null;
        }
        VeterinaryKnowledge veterinaryKnowledge = new VeterinaryKnowledge();
        veterinaryKnowledge.setId(id);
        return veterinaryKnowledge;
    }
}





