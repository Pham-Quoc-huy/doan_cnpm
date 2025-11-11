package com.docpet.animalhospital.service;

import com.docpet.animalhospital.domain.Owner;
import com.docpet.animalhospital.repository.OwnerRepository;
import com.docpet.animalhospital.service.dto.OwnerDTO;
import com.docpet.animalhospital.service.mapper.OwnerMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class OwnerService {

    private static final Logger LOG = LoggerFactory.getLogger(OwnerService.class);

    private final OwnerRepository ownerRepository;
    private final OwnerMapper ownerMapper;

    public OwnerService(OwnerRepository ownerRepository, OwnerMapper ownerMapper) {
        this.ownerRepository = ownerRepository;
        this.ownerMapper = ownerMapper;
    }

    public OwnerDTO save(OwnerDTO ownerDTO) {
        LOG.debug("Request to save Owner : {}", ownerDTO);
        Owner owner = ownerMapper.toEntity(ownerDTO);
        owner = ownerRepository.save(owner);
        return ownerMapper.toDto(owner);
    }

    public OwnerDTO update(OwnerDTO ownerDTO) {
        LOG.debug("Request to update Owner : {}", ownerDTO);
        Owner owner = ownerMapper.toEntity(ownerDTO);
        owner = ownerRepository.save(owner);
        return ownerMapper.toDto(owner);
    }

    public Optional<OwnerDTO> partialUpdate(OwnerDTO ownerDTO) {
        LOG.debug("Request to partially update Owner : {}", ownerDTO);
        return ownerRepository
            .findById(ownerDTO.getId())
            .map(existingOwner -> {
                if (ownerDTO.getName() != null) {
                    existingOwner.setName(ownerDTO.getName());
                }
                if (ownerDTO.getPhone() != null) {
                    existingOwner.setPhone(ownerDTO.getPhone());
                }
                if (ownerDTO.getAddress() != null) {
                    existingOwner.setAddress(ownerDTO.getAddress());
                }
                return existingOwner;
            })
            .map(ownerRepository::save)
            .map(ownerMapper::toDto);
    }

    @Transactional(readOnly = true)
    public Page<OwnerDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all Owners");
        return ownerRepository.findAll(pageable).map(ownerMapper::toDto);
    }

    @Transactional(readOnly = true)
    public Optional<OwnerDTO> findOne(Long id) {
        LOG.debug("Request to get Owner : {}", id);
        return ownerRepository.findById(id).map(ownerMapper::toDto);
    }

    public void delete(Long id) {
        LOG.debug("Request to delete Owner : {}", id);
        ownerRepository.deleteById(id);
    }
}

