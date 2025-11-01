package org.example.smartspring.mapper;

import org.example.smartspring.dto.HistoriqueLivraison.AddHistoriqueLivraisonDTO;
import org.example.smartspring.dto.HistoriqueLivraison.HistoriqueLivraisonDTO;
import org.example.smartspring.entities.HistoriqueLivraison;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface HistoriqueLivraisonMapper {

    @Mapping(target = "colis.id", source = "colisId")
    HistoriqueLivraison toEntity(AddHistoriqueLivraisonDTO dto);

    @Mapping(target = "colisId", source = "colis.id")
    HistoriqueLivraisonDTO toDto(HistoriqueLivraison entity);
}
