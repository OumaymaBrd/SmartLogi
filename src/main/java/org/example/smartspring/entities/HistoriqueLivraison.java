package org.example.smartspring.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.smartspring.enums.StatutColis;

import java.time.LocalDateTime;

@Entity
@Table(name = "historique_livraison")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HistoriqueLivraison {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "colis_id", nullable = false)
    private Colis colis;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private StatutColis statut;

    @Column(nullable = false)
    private LocalDateTime dateChangement;

    @Column(length = 500)
    private String commentaire;
}
