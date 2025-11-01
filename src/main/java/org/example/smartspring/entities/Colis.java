package org.example.smartspring.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.smartspring.enums.PrioriteColis;
import org.example.smartspring.enums.StatutColis;

import java.time.LocalDateTime;

@Entity
@Table(name = "colis")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Colis {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "numero_suivi", unique = true, nullable = false, length = 50)
    private String numeroSuivi;

    @Column(nullable = false, length = 500)
    private String description;

    @Column(nullable = false)
    private Double poids;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private StatutColis statut;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private PrioriteColis priorite;

    @Column(length = 100)
    private String villeDestination;

    @Column(name = "date_livraison_reelle")
    private LocalDateTime dateLivraisonReelle;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "livreur_id")
    private Livreur livreur;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_expediteur_id", nullable = false)
    private ClientExpediteur clientExpediteur;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "destinataire_id", nullable = false)
    private Destinataire destinataire;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "zone_id")
    private Zone zone;
}