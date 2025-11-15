package org.example.smartspring.mapper;

import org.example.smartspring.dto.clientexpediteur.AddClientExpediteurDTO;
import org.example.smartspring.dto.clientexpediteur.ClientExpediteurDTO;
import org.example.smartspring.dto.colis.ColisDTO;
import org.example.smartspring.dto.destinataire.DestinataireDTO;
import org.example.smartspring.dto.livreur.UpdateColisStatutDTO;
import org.example.smartspring.dto.produit.ProduitDTO;
import org.example.smartspring.dto.zone.ZoneDTO;
import org.example.smartspring.entities.ClientExpediteur;
import org.example.smartspring.entities.Colis;
import org.example.smartspring.entities.Produit;
import org.example.smartspring.entities.Zone;
import org.example.smartspring.enums.StatutColis;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

class ColisMapperTest {

    private final ColisMapper mapper = new ColisMapperImpl();


    @Test
    void toClientExpediteur_FromAddDTO_ShouldMapAndIgnoreFields() {
        // ARRANGE
        AddClientExpediteurDTO dto = AddClientExpediteurDTO.builder()
                .nom("NomAdd").prenom("PrenomAdd").email("add@mail.com")
                .adresse("1 Rue Add").telephone("0987654321").build();

        // ACT
        ClientExpediteur entity = mapper.toClientExpediteur(dto);

        // ASSERT
        assertEquals("NomAdd", entity.getNom());
        assertEquals("0987654321", entity.getTelephone());
        assertNull(entity.getId(), "L'ID doit être null car ignoré.");
        assertNull(entity.getVille(), "La ville n'est pas dans le DTO source.");
    }

    @Test
    void toClientExpediteur_FromFullDTO_ShouldMapFieldsCorrectly() {
        // ARRANGE
        ClientExpediteurDTO dto = new ClientExpediteurDTO("NomExp", "PrenomExp", "exp@mail.com", "0123456789", "Adresse A", "Ville A");

        // ACT
        ClientExpediteur entity = mapper.toClientExpediteur(dto);

        // ASSERT
        assertEquals("NomExp", entity.getNom());
        assertEquals("Ville A", entity.getVille());
        assertNull(entity.getId());
    }


    @Test
    void toZone_ShouldMapCodePostaleToCodePostal() {
        // ARRANGE
        ZoneDTO dto = new ZoneDTO("Zone A", "Description A", "75000");

        // ACT
        Zone zone = mapper.toZone(dto);

        // ASSERT
        assertEquals("75000", zone.getCodePostal());
        assertEquals("Zone A", zone.getNom());
    }

    @Test
    void toEntity_ColisDTO_ShouldGenerateNumeroColisAndMapStatut() {
        ClientExpediteurDTO expDTO = new ClientExpediteurDTO("Exp", "PExp", "e@e.com", "0000000000", "Add", "Ville");
        DestinataireDTO destDTO = new DestinataireDTO("Dest", "PDest", "d@d.com", "0000000000", "Add", "Ville");
        ZoneDTO zoneDTO = new ZoneDTO("Zone A", "Description Zone", "75000");
        List<ProduitDTO> produitsList = Collections.emptyList();

        ColisDTO dto = new ColisDTO(
                "EN_TRANSIT", "NORMALE", expDTO, destDTO, zoneDTO, produitsList, "Description", 5.0
        );

        Colis colis = mapper.toEntity(dto);

        assertThat(colis.getNumeroColis()).matches("COLIS_\\d{5}");
        assertEquals(StatutColis.EN_TRANSIT, colis.getStatut());
        assertNull(colis.getId());
    }


    @Test
    void mapStatut_NullInput_ShouldReturnCREE() {
        assertEquals(StatutColis.CREE, mapper.mapStatut(null));
    }

    @Test
    void mapStatut_ValidInput_ShouldReturnCorrespondingStatut() {
        assertEquals(StatutColis.LIVRE, mapper.mapStatut("LIVRE"));
    }

    @Test
    void mapStatut_InvalidInput_ShouldThrowException() {
        assertThrows(IllegalArgumentException.class, () -> {
            mapper.mapStatut("STATUT_INVALIDE");
        });
    }

    @Test
    void toProduitList_NullInput_ShouldReturnNull() {
        assertNull(mapper.toProduitList(null));
    }

    @Test
    void toProduitList_EmptyListInput_ShouldReturnEmptyList() {
        assertTrue(mapper.toProduitList(Collections.emptyList()).isEmpty());
    }

}