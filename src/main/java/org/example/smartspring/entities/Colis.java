package org.example.smartspring.entities;

import jakarta.persistence.*;
import lombok.*;
import org.example.smartspring.enums.PrioriteColis;
import org.example.smartspring.enums.StatutColis;

@Entity
@Table(name = "colis")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Colis {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String description;

    private Double poids;

    @Enumerated(EnumType.STRING)
    private StatutColis statut;

    @Enumerated(EnumType.STRING)
    private PrioriteColis priorite;

    private String villeDestination;

    // Relations ManyToOne
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_livreur")
    private Livreur livreur;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_client_expediteur")
    private ClientExpediteur clientExpediteur;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_destinataire")
    private Destinataire destinataire;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_zone")
    private Zone zone;
}
