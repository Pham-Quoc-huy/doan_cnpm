package com.docpet.animalhospital.web.rest;

import com.docpet.animalhospital.repository.VetRepository;
import com.docpet.animalhospital.service.VetService;
import com.docpet.animalhospital.service.dto.VetDTO;
import com.docpet.animalhospital.web.rest.errors.BadRequestAlertException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/vets")
public class VetResource {

    private static final Logger LOG = LoggerFactory.getLogger(VetResource.class);
    private static final String ENTITY_NAME = "vet";

    private final VetService vetService;
    private final VetRepository vetRepository;

    public VetResource(VetService vetService, VetRepository vetRepository) {
        this.vetService = vetService;
        this.vetRepository = vetRepository;
    }

    @PostMapping("")
    public ResponseEntity<VetDTO> createVet(@Valid @RequestBody VetDTO vetDTO) throws URISyntaxException {
        LOG.debug("REST request to save Vet : {}", vetDTO);
        if (vetDTO.getId() != null) {
            throw new BadRequestAlertException("A new vet cannot already have an ID", ENTITY_NAME, "idexists");
        }
        vetDTO = vetService.save(vetDTO);
        return ResponseEntity.created(new URI("/api/vets/" + vetDTO.getId())).body(vetDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<VetDTO> updateVet(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody VetDTO vetDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update Vet : {}, {}", id, vetDTO);
        if (vetDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, vetDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!vetRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        vetDTO = vetService.update(vetDTO);
        return ResponseEntity.ok().body(vetDTO);
    }

    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<VetDTO> partialUpdateVet(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody VetDTO vetDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update Vet partially : {}, {}", id, vetDTO);
        if (vetDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, vetDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!vetRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<VetDTO> result = vetService.partialUpdate(vetDTO);
        return result.map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("")
    public ResponseEntity<List<VetDTO>> getAllVets(Pageable pageable) {
        LOG.debug("REST request to get a page of Vets");
        Page<VetDTO> page = vetService.findAll(pageable);
        return ResponseEntity.ok().body(page.getContent());
    }

    @GetMapping("/{id}")
    public ResponseEntity<VetDTO> getVet(@PathVariable("id") Long id) {
        LOG.debug("REST request to get Vet : {}", id);
        Optional<VetDTO> vetDTO = vetService.findOne(id);
        return vetDTO.map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVet(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete Vet : {}", id);
        vetService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

