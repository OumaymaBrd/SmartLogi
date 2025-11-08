package org.example.smartspring.entities;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "historique_email")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HistoriqueEmail {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String destinataire;
    private String sujet;

    @Column(columnDefinition = "TEXT")
    private String contenu;

    private boolean success;

    private String erreur;

    private LocalDateTime dateEnvoi;

    private String colisId;
    private String statutColis;
}
