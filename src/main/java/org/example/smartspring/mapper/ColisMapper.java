package org.example.smartspring.mapper;

import org.example.smartspring.dto.clientexpediteur.ClientExpediteurDTO;
import org.example.smartspring.dto.colis.ColisDTO;
import org.example.smartspring.dto.destinataire.DestinataireDTO;
import org.example.smartspring.dto.livreur.ConsulterColisAffecterDTO;
import org.example.smartspring.dto.produit.ProduitDTO;
import org.example.smartspring.dto.zone.ZoneDTO;
import org.example.smartspring.entities.*;
import org.example.smartspring.enums.StatutColis;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

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
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "numeroSuivi", ignore = true)
    @Mapping(target = "livreur", ignore = true)
    @Mapping(target = "dateCreation", ignore = true)
    @Mapping(target = "dateLivraisonReelle", ignore = true)
    @Mapping(target = "villeDestination", ignore = true)
    Colis toEntity(ColisDTO dto);

    @Mapping(target = "expediteur", ignore = true)
    @Mapping(target = "produits", ignore = true)
    @Mapping(target = "description", ignore = true)
    @Mapping(target = "poids", ignore = true)
    ColisDTO toDTO(Colis colis);

    @Mapping(target = "id", ignore = true)
    ClientExpediteur toClientExpediteur(ClientExpediteurDTO dto);

    @Mapping(target = "id", ignore = true)
    Destinataire toDestinataire(DestinataireDTO dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "codePostal", source = "codePostale")
    Zone toZone(ZoneDTO dto);

    @Mapping(target = "id", ignore = true)
    Produit toProduit(ProduitDTO dto);

    default List<Produit> toProduitList(List<ProduitDTO> dtos) {
        if (dtos == null) return null;
        return dtos.stream()
                .map(this::toProduit)
                .collect(Collectors.toList());
    }

    @Named("mapStatut")
    default StatutColis mapStatut(String statut) {
        if (statut == null) return StatutColis.CREE;
        return StatutColis.valueOf(statut);
    }

    @Mapping(target = "numero_colis", source = "numeroColis")
    @Mapping(target = "priorite", source = "priorite")
    @Mapping(target = "client_expediteur_id", source = "clientExpediteur.id")
    @Mapping(target = "zone", source = "zone.nom")
    ConsulterColisAffecterDTO toDto(Colis colis);
}
