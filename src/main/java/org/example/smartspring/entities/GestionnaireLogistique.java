package org.example.smartspring.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "gestionnaire_logistique")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GestionnaireLogistique {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false)
    private String nom;

    @Column(nullable = false)
    private String prenom;

    @Column(nullable = false, unique = true, length = 191)
    private String email;

    @Column(nullable = false)
    private String telephone;


    @CreationTimestamp
    @Column(name = "date_creation", nullable = false, updatable = false)
    private LocalDateTime dateCreation;

    @Column(nullable = false)
    private Boolean actif = true;

    @OneToMany(mappedBy = "gestionnaire", cascade = CascadeType.ALL)
    @Builder.Default
    private List<HistoriqueLivraison> historiques = new ArrayList<>();
}
