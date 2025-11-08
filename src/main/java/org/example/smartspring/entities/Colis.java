package org.example.smartspring.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.smartspring.enums.PrioriteColis;
import org.example.smartspring.enums.StatutColis;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@EntityListeners(org.example.smartspring.entities.listeners.ColisListener.class)
@Table(name = "colis")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Colis {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false, unique = true)
    private String numeroColis;

    @Column(nullable = false)
    private String numeroSuivi;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatutColis statut;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private PrioriteColis priorite = PrioriteColis.NORMALE;

    @ManyToOne
    @JoinColumn(name = "client_expediteur_id", nullable = false)
    private ClientExpediteur clientExpediteur;

    @ManyToOne
    @JoinColumn(name = "destinataire_id", nullable = false)
    private Destinataire destinataire;

    @ManyToOne
    @JoinColumn(name = "zone_id", nullable = false)
    private Zone zone;

    @OneToMany(mappedBy = "colis", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<ColisProduit> colisProduits = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "livreur_id")
    private Livreur livreur;

    @Column(nullable = false)
    private LocalDateTime dateCreation;

    @Column(name = "date_livraison_reelle")
    private LocalDateTime dateLivraisonReelle;

    @Column(name = "ville_destination", length = 100)
    private String villeDestination;

    @ManyToOne
    @JoinColumn(name = "livreur_id_livree")
    private Livreur livreurLivree;


}
