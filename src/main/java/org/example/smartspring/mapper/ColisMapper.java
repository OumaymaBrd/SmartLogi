package org.example.smartspring.mapper;

import org.example.smartspring.dto.request.ColisRequestDTO;
import org.example.smartspring.dto.response.ColisResponseDTO;
import org.example.smartspring.entities.Colis;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ColisMapper {

    ColisMapper INSTANCE = Mappers.getMapper(ColisMapper.class);

    // Mapping du DTO vers l'entité
    @Mapping(source = "idLivreur", target = "livreur.id")
    @Mapping(source = "idClientExpediteur", target = "clientExpediteur.id")
    @Mapping(source = "idDestinataire", target = "destinataire.id")
    @Mapping(source = "idZone", target = "zone.id")

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "statut", ignore = true)
    Colis toEntity(ColisRequestDTO dto);


    @Mapping(target = "livreurNom", expression = "java(entity.getLivreur() != null ? entity.getLivreur().getNom() : null)")
    @Mapping(target = "clientExpediteurNom", expression = "java(entity.getClientExpediteur() != null ? entity.getClientExpediteur().getNom() : null)")
    @Mapping(target = "destinataireNom", expression = "java(entity.getDestinataire() != null ? entity.getDestinataire().getNom() : null)")
    @Mapping(target = "zoneNom", expression = "java(entity.getZone() != null ? entity.getZone().getNom() : null)")
    ColisResponseDTO toResponseDTO(Colis entity);
}