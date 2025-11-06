package org.example.smartspring.mapper;

import org.example.smartspring.dto.colis.ColisDTO;
import org.example.smartspring.dto.destinataire.DestinataireDTO;
import org.example.smartspring.dto.produit.ProduitDTO;
import org.example.smartspring.dto.zone.ZoneDTO;
import org.example.smartspring.entities.*;
import org.example.smartspring.enums.StatutColis;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.Context;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface ColisMapper {

    @Mapping(target = "numeroColis", expression = "java(\"COLIS_\" + (int)(Math.random()*90000 + 10000))")
    @Mapping(target = "statut", source = "statut", qualifiedByName = "mapStatut")
    @Mapping(target = "clientExpediteur", ignore = true)
    @Mapping(target = "colisProduits", ignore = true)
    @Mapping(target = "zone", ignore = true)
    @Mapping(target = "destinataire", ignore = true)
    Colis toEntity(ColisDTO dto, @Context ClientExpediteur client);

    ColisDTO toDTO(Colis colis);

    Produit toProduitEntity(ProduitDTO dto);

    Zone toZoneEntity(ZoneDTO dto);

    Destinataire toDestinataireEntity(DestinataireDTO dto);

    default List<Produit> toProduitList(List<ProduitDTO> dtos) {
        if (dtos == null) return null;
        return dtos.stream()
                .map(this::toProduitEntity)
                .collect(Collectors.toList());
    }

    @Named("mapStatut")
    default StatutColis mapStatut(String statut) {
        if (statut == null) return StatutColis.CREE;
        return StatutColis.valueOf(statut);
    }
}
