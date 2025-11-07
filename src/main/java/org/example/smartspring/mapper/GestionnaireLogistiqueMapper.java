package org.example.smartspring.mapper;

import org.example.smartspring.dto.gestionnairelogistique.AddGestionnaireLogistqueDTO;
import org.example.smartspring.dto.gestionnairelogistique.GestionnaireLogistiqueDTO;
import org.example.smartspring.entities.ClientExpediteur;
import org.example.smartspring.entities.GestionnaireLogistique;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface GestionnaireLogistiqueMapper {

    public GestionnaireLogistiqueDTO toDto(GestionnaireLogistique entity);
    public GestionnaireLogistique toEntity(AddGestionnaireLogistqueDTO dto);

    void updatesColisLivreur(GestionnaireLogistiqueDTO dto , @MappingTarget GestionnaireLogistique entity);





}
