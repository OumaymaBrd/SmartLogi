package org.example.smartspring.service;

import org.example.smartspring.dto.colis.ColisDetails.*;
import org.example.smartspring.entities.*;
import org.example.smartspring.enums.PrioriteColis;
import org.example.smartspring.enums.StatutColis;
import org.example.smartspring.mapper.ColisDeatilsMapper.ColisDetailsMapper;
import org.example.smartspring.mapper.ColisMapper;
import org.example.smartspring.mapper.ColisDeatilsMapper.LivreurCollecteMapper;
import org.example.smartspring.mapper.ColisDeatilsMapper.LivreurLivreeMapper;
import org.example.smartspring.mapper.ColisDeatilsMapper.ZoneDeatailsMapper;
import org.example.smartspring.mapper.ColisDeatilsMapper.DestinataireDetailsMapper;
import org.example.smartspring.repository.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import org.springframework.data.domain.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ColisDetailsServiceTest {

    @Mock
    private ColisDeatilsRepository colisDeatilsRepository;

    @Mock
    private LivreurLivreeMapper livreurLivreeMapper;

    @Mock
    private LivreurCollecteMapper livreurCollecteMapper;

    @Mock
    private ColisDetailsMapper colisDetailsMapper;

    @Mock
    private LivreurRepository livreurRepository;

    @Mock
    private ColisProduitRepository colisProduitRepository;

    @Mock
    private DestinataireRepository destinataireRepository;

    @Mock
    private ZoneRepository zoneRepository;

    @Mock
    private ZoneDeatailsMapper zoneDeatailsMapper;

    @Mock
    private DestinataireDetailsMapper destinataireDetailsMapper;

    @Mock
    private ColisDetailsMapper colismapper;

    @Mock
    private ColisMapper mapper;

    @InjectMocks
    private ColisDetailsService colisDetailsService;


    private Colis colis;
    private Livreur livreurCollecte;
    private Livreur livreurLivree;
    private Destinataire destinataire;
    private Zone zone;
    private Produit produit;
    private ColisProduit colisProduit;


    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);

        // Livreur Collecte
        livreurCollecte = new Livreur();
        livreurCollecte.setId("L1");
        livreurCollecte.setNom("Ahmed");
        livreurCollecte.setPrenom("Ali");

        // Livreur Livrée
        livreurLivree = new Livreur();
        livreurLivree.setId("L2");
        livreurLivree.setNom("Sara");
        livreurLivree.setPrenom("Nour");

        // Destinataire
        destinataire = new Destinataire();
        destinataire.setNom("Yassine");
        destinataire.setPrenom("Omar");
        destinataire.setEmail("dest@test.com");

        // Zone
        zone = new Zone();
        zone.setNom("Zone 1");
        zone.setDescription("Zone principale");

        // Produit
        produit = new Produit();
        produit.setNom("Produit A");
        produit.setPrixUnitaire(BigDecimal.valueOf(99.99));

        colisProduit = new ColisProduit();
        colisProduit.setProduit(produit);

        // Colis
        colis = new Colis();
        colis.setId("C1");
        colis.setNumeroColis("NUM123");
        colis.setPriorite(PrioriteColis.URGENTE);
        colis.setStatut(StatutColis.CREE);
        colis.setVilleDestination("Rabat");
        colis.setDateCreation(LocalDateTime.now());
        colis.setLivreur(livreurCollecte);
        colis.setLivreurLivree(livreurLivree);
        colis.setDestinataire(destinataire);
        colis.setZone(zone);
    }

    @Test
    void testGetAll_FullMapping() {

        Pageable pageable = PageRequest.of(0, 10);
        List<Colis> colisList = Collections.singletonList(colis);

        when(colisDeatilsRepository.findAll()).thenReturn(colisList);
        when(colisProduitRepository.findByColis_Id("C1"))
                .thenReturn(Collections.singletonList(colisProduit));

        // Mock destinataire
        when(destinataireDetailsMapper.mapToDto(destinataire))
                .thenReturn(new DestinataireDetailsDTO("Yassine Omar", "dest@test.com"));

        // Mock zone
        when(zoneDeatailsMapper.mapToDto(zone))
                .thenReturn(new ZoneDeatailsDTO("Zone 1", "Zone principale"));

        // CALL SERVICE
        Page<ColisDetailsDTO> page =
                colisDetailsService.getAll(null, null, null, null, null, pageable);


        assertEquals(1, page.getTotalElements());

        ColisDetailsDTO dto = page.getContent().get(0);
        assertEquals("C1", dto.getId());
        assertEquals("NUM123", dto.getNumeroColis());
        assertEquals(PrioriteColis.URGENTE, dto.getPriorite());
        assertEquals(StatutColis.CREE, dto.getStatut());
        assertEquals("Rabat", dto.getVille_destination());

        // LIVREUR COLLECTE
        assertNotNull(dto.getLivreurCollecte());
        assertEquals("L1", dto.getLivreurCollecte().getId());
        assertEquals("Ahmed Ali", dto.getLivreurCollecte().getNom_complet());

        // LIVREUR LIVREE
        assertNotNull(dto.getLivreurLivree());
        assertEquals("L2", dto.getLivreurLivree().getId());
        assertEquals("Sara Nour", dto.getLivreurLivree().getNom_complet());

        // PRODUITS
        assertNotNull(dto.getProduits());
        assertEquals(1, dto.getProduits().size());
        assertEquals("Produit A", dto.getProduits().get(0).getNom());
        assertEquals(BigDecimal.valueOf(99.99), dto.getProduits().get(0).getPrixUnitaire());

        // DESTINATAIRE
        assertNotNull(dto.getDestinataire());
        assertEquals("dest@test.com", dto.getDestinataire().getEmail());

        // ZONE
        assertNotNull(dto.getZone());
        assertEquals("Zone 1", dto.getZone().getNom());
    }


    @Test
    void testUpdateStatutColisLivreur() {

        String livreurId = "L1";

        UpdateStatutLivreurColis updateDto =
                new UpdateStatutLivreurColis(StatutColis.EN_STOCK);

        List<Colis> colisList = Collections.singletonList(colis);

        when(colisDeatilsRepository.findByLivreur_Id(livreurId))
                .thenReturn(colisList);

        ColisDetailsDTO dto =
                ColisDetailsDTO.builder().id("C1").statut(StatutColis.EN_STOCK).build();

        when(mapper.toDTOList(colisList))
                .thenReturn(Collections.singletonList(dto));

        List<ColisDetailsDTO> result =
                colisDetailsService.updateStatutColisLivreur(livreurId, updateDto);

        assertEquals(StatutColis.EN_STOCK, colis.getStatut());
        verify(colisDeatilsRepository).saveAll(colisList);
        assertEquals(1, result.size());
    }
}
