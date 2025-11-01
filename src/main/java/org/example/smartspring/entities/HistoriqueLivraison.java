package org.example.smartspring.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.smartspring.enums.StatutColis;

import java.time.LocalDateTime;

@Entity
@Table(name = "historique_livraison", indexes = {
        @Index(name = "idx_historique_colis", columnList = "colis_id")
})
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HistoriqueLivraison {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "colis_id", nullable = false)
    private Colis colis;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private StatutColis statut;

    @Column(name = "date_changement")
    private LocalDateTime dateChangement;

    @Column(columnDefinition = "TEXT")
    private String commentaire;

    @PrePersist
    protected void onCreate() {
        if (dateChangement == null) {
            dateChangement = LocalDateTime.now();
        }
    }
}
