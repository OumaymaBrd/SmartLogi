package org.example.smartspring.mapper;

import org.example.smartspring.dto.produit.AddProduitDTO;
import org.example.smartspring.dto.produit.ProduitDTO;
import org.example.smartspring.dto.produit.UpdateProduitDTO;
import org.example.smartspring.entities.Produit;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ProduitMapper {
    Produit toEntity(AddProduitDTO dto);
    ProduitDTO toDto(Produit entity);
    void updateEntityFromDto(UpdateProduitDTO dto, @MappingTarget Produit entity);
}
