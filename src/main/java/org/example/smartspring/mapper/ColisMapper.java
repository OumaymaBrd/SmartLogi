package org.example.smartspring.mapper;

import org.example.smartspring.dto.Colis.AddColisDTO;
import org.example.smartspring.dto.Colis.ColisDTO;
import org.example.smartspring.dto.Colis.UpdateColisDTO;
import org.example.smartspring.entities.Colis;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ColisMapper {

    @Mapping(target = "clientExpediteur.id", source = "clientExpediteurId")
    @Mapping(target = "destinataire.id", source = "destinataireId")
    @Mapping(target = "livreur.id", source = "livreurId")
    @Mapping(target = "zone.id", source = "zoneId")
    Colis toEntity(AddColisDTO dto);

    @Mapping(target = "clientExpediteurId", source = "clientExpediteur.id")
    @Mapping(target = "clientExpediteurNom", expression = "java(entity.getClientExpediteur().getNom() + \" \" + entity.getClientExpediteur().getPrenom())")
    @Mapping(target = "destinataireId", source = "destinataire.id")
    @Mapping(target = "destinataireNom", expression = "java(entity.getDestinataire().getNom() + \" \" + entity.getDestinataire().getPrenom())")
    @Mapping(target = "livreurId", source = "livreur.id")
    @Mapping(target = "livreurNom", expression = "java(entity.getLivreur() != null ? entity.getLivreur().getNom() + \" \" + entity.getLivreur().getPrenom() : null)")
    @Mapping(target = "zoneId", source = "zone.id")
    @Mapping(target = "zoneNom", source = "zone.nom")
    ColisDTO toDto(Colis entity);

    @Mapping(target = "livreur.id", source = "livreurId")
    @Mapping(target = "zone.id", source = "zoneId")
    void updateEntityFromDto(UpdateColisDTO dto, @MappingTarget Colis entity);
}
