package org.example.smartspring.service;

import jakarta.transaction.Transactional;
import org.example.smartspring.dto.gestionnairelogistique.AddGestionnaireLogistqueDTO;
import org.example.smartspring.dto.gestionnairelogistique.GestionnaireLogistiqueDTO;
import org.example.smartspring.entities.ClientExpediteur;
import org.example.smartspring.entities.GestionnaireLogistique;
import org.example.smartspring.repository.ClientExpediteurRepository;
import org.example.smartspring.repository.GestionnaireLogistiqueRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class GestionnaireLogistiqueServiceTest {

    @Autowired
    private GestionnaireLogistiqueRepository repository;

    @Autowired
    private ClientExpediteurRepository clientRepository;

    @Autowired
    private GestionnaireLogistiqueService service;

    @Test
    void testCreateAndFindGestionnaire() {
        // Création DTO
        AddGestionnaireLogistqueDTO dto = new AddGestionnaireLogistqueDTO();
        dto.setNom("Bramid");
        dto.setPrenom("Oumayma");
        dto.setEmail("oumayma@gmail.com");
        dto.setTelephone("0701237397");

        // Sauvegarde via le service
        GestionnaireLogistiqueDTO saved = service.create(dto);
        assertNotNull(saved.getId(), "L'id ne doit pas être null");

        // Vérification en base
        List<GestionnaireLogistique> all = repository.findAll();
        assertEquals(1, all.size(), "Il doit y avoir exactement 1 gestionnaire");
    }

    @Test
    void testInsertAndFindClient() {
        ClientExpediteur client = new ClientExpediteur();
        client.setNom("Alami");
        client.setPrenom("Hassan");
        client.setEmail("alami.hassan@gmail.com");
        client.setTelephone("0606060606");
        client.setAdresse("123 Rue des Tests");
        client.setVille("Casablanca");

        clientRepository.save(client);

        List<ClientExpediteur> clients = clientRepository.findAll();
        assertEquals(1, clients.size());
        assertEquals("Alami", clients.get(0).getNom());
    }


}
