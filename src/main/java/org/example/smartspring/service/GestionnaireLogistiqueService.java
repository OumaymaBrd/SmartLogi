package org.example.smartspring.service;

import lombok.RequiredArgsConstructor;
import org.example.smartspring.dto.gestionnairelogistique.AddGestionnaireLogistqueDTO;
import org.example.smartspring.dto.gestionnairelogistique.GestionnaireLogistiqueDTO;
import org.example.smartspring.entities.GestionnaireLogistique;
import org.example.smartspring.mapper.GestionnaireLogistiqueMapper;
import org.example.smartspring.repository.GestionnaireLogistiqueRepository;
import org.springframework.stereotype.Service;

@Service

@RequiredArgsConstructor
public class GestionnaireLogistiqueService {

    private final GestionnaireLogistiqueRepository repository;
    private final GestionnaireLogistiqueMapper mapper;

    public GestionnaireLogistiqueDTO create(AddGestionnaireLogistqueDTO dto){

        GestionnaireLogistique entity= mapper.toEntity(dto);
        GestionnaireLogistique saved= repository.save(entity);
        return mapper.toDto(saved);
    }




}
