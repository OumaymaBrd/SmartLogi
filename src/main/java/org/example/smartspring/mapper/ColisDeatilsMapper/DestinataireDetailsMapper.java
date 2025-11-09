package org.example.smartspring.mapper.ColisDeatilsMapper;

import org.example.smartspring.dto.colis.ColisDetails.DestinataireDetailsDTO;
import org.example.smartspring.entities.Destinataire;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper (componentModel = "spring")
public interface DestinataireDetailsMapper {

    @Mapping(target = "nom_complet" , expression = "java( CombineName(entity.getNom() , entity.getPrenom()) )")
    @Mapping(target = "email",source = "email")
    DestinataireDetailsDTO mapToDto (Destinataire entity);

    default String CombineName(String nom , String prenom){
        return nom+ " "+prenom;
    }
}
