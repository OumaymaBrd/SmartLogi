package org.example.smartspring.mapper;

import org.example.smartspring.dto.gestionnairelogistique.AddGestionnaireLogistqueDTO;
import org.example.smartspring.dto.gestionnairelogistique.GestionnaireLogistiqueDTO;
import org.example.smartspring.entities.ClientExpediteur;
import org.example.smartspring.entities.GestionnaireLogistique;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GestionnaireLogistiqueMapper {

    public GestionnaireLogistiqueDTO toDto(GestionnaireLogistique entity);
    public GestionnaireLogistique toEntity(AddGestionnaireLogistqueDTO dto);




}
