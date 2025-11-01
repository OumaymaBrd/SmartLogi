package org.example.smartspring.mapper;

import org.example.smartspring.dto.ColisProduit.AddColisProduitDTO;
import org.example.smartspring.dto.ColisProduit.ColisProduitDTO;
import org.example.smartspring.entities.ColisProduit;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ColisProduitMapper {

    @Mapping(target = "colis.id", source = "colisId")
    @Mapping(target = "produit.id", source = "produitId")
    ColisProduit toEntity(AddColisProduitDTO dto);

    @Mapping(target = "colisId", source = "colis.id")
    @Mapping(target = "produitId", source = "produit.id")
    @Mapping(target = "produitNom", source = "produit.nom")
    ColisProduitDTO toDto(ColisProduit entity);
}
