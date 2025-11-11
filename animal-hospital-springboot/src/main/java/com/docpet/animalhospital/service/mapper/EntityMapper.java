package com.docpet.animalhospital.service.mapper;

import java.util.List;

public interface EntityMapper<D, E> {
    E toEntity(D dto);

    D toDto(E entity);

    List<E> toEntity(List<D> dtoList);

    List<D> toDto(List<E> entityList);

    default E partialUpdate(E entity, D dto) {
        return entity;
    }
}

