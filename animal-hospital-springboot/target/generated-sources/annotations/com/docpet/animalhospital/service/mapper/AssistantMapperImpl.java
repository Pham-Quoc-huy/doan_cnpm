package com.docpet.animalhospital.service.mapper;

import com.docpet.animalhospital.domain.Assistant;
import com.docpet.animalhospital.domain.User;
import com.docpet.animalhospital.service.dto.AssistantDTO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-11-20T10:59:39+0700",
    comments = "version: 1.6.3, compiler: javac, environment: Java 23.0.2 (Eclipse Adoptium)"
)
@Component
public class AssistantMapperImpl implements AssistantMapper {

    @Override
    public List<Assistant> toEntity(List<AssistantDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<Assistant> list = new ArrayList<Assistant>( dtoList.size() );
        for ( AssistantDTO assistantDTO : dtoList ) {
            list.add( toEntity( assistantDTO ) );
        }

        return list;
    }

    @Override
    public List<AssistantDTO> toDto(List<Assistant> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<AssistantDTO> list = new ArrayList<AssistantDTO>( entityList.size() );
        for ( Assistant assistant : entityList ) {
            list.add( toDto( assistant ) );
        }

        return list;
    }

    @Override
    public AssistantDTO toDto(Assistant s) {
        if ( s == null ) {
            return null;
        }

        AssistantDTO assistantDTO = new AssistantDTO();

        assistantDTO.setUserId( sUserId( s ) );
        assistantDTO.setId( s.getId() );

        return assistantDTO;
    }

    @Override
    public Assistant toEntity(AssistantDTO assistantDTO) {
        if ( assistantDTO == null ) {
            return null;
        }

        Assistant assistant = new Assistant();

        assistant.setId( assistantDTO.getId() );

        return assistant;
    }

    private Long sUserId(Assistant assistant) {
        User user = assistant.getUser();
        if ( user == null ) {
            return null;
        }
        return user.getId();
    }
}
