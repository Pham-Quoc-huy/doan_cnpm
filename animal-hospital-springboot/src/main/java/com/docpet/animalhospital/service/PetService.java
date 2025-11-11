package com.docpet.animalhospital.service;

import com.docpet.animalhospital.domain.Pet;
import com.docpet.animalhospital.repository.PetRepository;
import com.docpet.animalhospital.service.dto.PetDTO;
import com.docpet.animalhospital.service.mapper.PetMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PetService {

    private static final Logger LOG = LoggerFactory.getLogger(PetService.class);

    private final PetRepository petRepository;
    private final PetMapper petMapper;

    public PetService(PetRepository petRepository, PetMapper petMapper) {
        this.petRepository = petRepository;
        this.petMapper = petMapper;
    }

    public PetDTO save(PetDTO petDTO) {
        LOG.debug("Request to save Pet : {}", petDTO);
        Pet pet = petMapper.toEntity(petDTO);
        pet = petRepository.save(pet);
        return petMapper.toDto(pet);
    }

    public PetDTO update(PetDTO petDTO) {
        LOG.debug("Request to update Pet : {}", petDTO);
        Pet pet = petMapper.toEntity(petDTO);
        pet = petRepository.save(pet);
        return petMapper.toDto(pet);
    }

    public Optional<PetDTO> partialUpdate(PetDTO petDTO) {
        LOG.debug("Request to partially update Pet : {}", petDTO);
        return petRepository
            .findById(petDTO.getId())
            .map(existingPet -> {
                if (petDTO.getName() != null) {
                    existingPet.setName(petDTO.getName());
                }
                if (petDTO.getSpecies() != null) {
                    existingPet.setSpecies(petDTO.getSpecies());
                }
                if (petDTO.getBreed() != null) {
                    existingPet.setBreed(petDTO.getBreed());
                }
                if (petDTO.getSex() != null) {
                    existingPet.setSex(petDTO.getSex());
                }
                if (petDTO.getDateOfBirth() != null) {
                    existingPet.setDateOfBirth(petDTO.getDateOfBirth());
                }
                if (petDTO.getWeight() != null) {
                    existingPet.setWeight(petDTO.getWeight());
                }
                if (petDTO.getAllergies() != null) {
                    existingPet.setAllergies(petDTO.getAllergies());
                }
                if (petDTO.getNotes() != null) {
                    existingPet.setNotes(petDTO.getNotes());
                }
                if (petDTO.getImageUrl() != null) {
                    existingPet.setImageUrl(petDTO.getImageUrl());
                }
                return existingPet;
            })
            .map(petRepository::save)
            .map(petMapper::toDto);
    }

    @Transactional(readOnly = true)
    public Page<PetDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all Pets");
        return petRepository.findAll(pageable).map(petMapper::toDto);
    }

    @Transactional(readOnly = true)
    public Optional<PetDTO> findOne(Long id) {
        LOG.debug("Request to get Pet : {}", id);
        return petRepository.findById(id).map(petMapper::toDto);
    }

    public void delete(Long id) {
        LOG.debug("Request to delete Pet : {}", id);
        petRepository.deleteById(id);
    }
}

