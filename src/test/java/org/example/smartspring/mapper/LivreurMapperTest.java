package org.example.smartspring.mapper;

import org.example.smartspring.dto.livreur.AddLivreurDTO;
import org.example.smartspring.dto.livreur.LivreurDTO;
import org.example.smartspring.dto.livreur.UpdateLivreurDTO;
import org.example.smartspring.dto.zone.ZoneDTO;
import org.example.smartspring.entities.Livreur;
import org.example.smartspring.entities.Zone;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LivreurMapperTest {

    @InjectMocks
    private LivreurMapperImpl mapper;

    @Mock
    private ZoneMapper zoneMapper;

    private final String PHONE = "0601020304";
    private final String ZONE_ID = "Z1-IDF";
    private final String EMAIL = "livreur@smart.com";
    private final String VEHICULE = "Renault Kangoo";


    @Test
    void toEntity_FromAddLivreurDTO_ShouldMapFieldsAndIgnoreZone() {
        // ARRANGE: Utilisation du Builder pour créer un AddLivreurDTO complet
        AddLivreurDTO dto = AddLivreurDTO.builder()
                .nom("Dupont")
                .prenom("Jean")
                .telephone(PHONE)
                .email(EMAIL)
                .zoneId(ZONE_ID)
                .vehicule(VEHICULE)
                .build();

        // ACT
        Livreur entity = mapper.toEntity(dto);

        assertEquals("Dupont", entity.getNom());
        assertEquals(EMAIL, entity.getEmail());
        assertEquals(VEHICULE, entity.getVehicule());

        assertNull(entity.getZone(), "La Zone doit être ignorée et rester null.");


        verifyNoInteractions(zoneMapper);
    }


    @Test
    void updateEntityFromDto_ShouldUpdateSimpleFieldsAndIgnoreZone() {
        // ARRANGE
        UpdateLivreurDTO dto = UpdateLivreurDTO.builder()
                .nom("NomModifie")
                .disponible(false)
                .telephone("0999888777")
                .zoneId("Z2-NORD")
                .build();

        Zone zoneInitiale = new Zone();
        zoneInitiale.setNom("Zone Initiale");

        Livreur entity = new Livreur();
        entity.setNom("NomAncien");
        entity.setTelephone(PHONE);
        entity.setZone(zoneInitiale);

        // ACT
        mapper.updateEntityFromDto(dto, entity);

        // ASSERT Vérifie les champs mis à jour
        assertEquals("NomModifie", entity.getNom());
        assertEquals("0999888777", entity.getTelephone());

        // ASSERT Vérifie que le champ ignoré n'a pas été modifié
        assertNotNull(entity.getZone(), "La zone initiale ne doit pas être null.");
        assertEquals("Zone Initiale", entity.getZone().getNom(), "La zone doit rester l'ancienne.");

        // Vérifie que l'ID de zone du DTO n'a pas été traité par le ZoneMapper
        verifyNoInteractions(zoneMapper);
    }
}