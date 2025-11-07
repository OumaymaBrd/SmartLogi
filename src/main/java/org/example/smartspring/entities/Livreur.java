package org.example.smartspring.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "livreur")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Livreur {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false)
    private String nom;

    @Column(nullable = false)
    private String prenom;

    @Column(nullable = false, unique = true)
    private String telephone;

    private String email;

    @Column(name = "numero_permis")
    private String numeroPermis;

    @Column(name = "date_embauche")
    private LocalDateTime dateEmbauche;

    @Column(nullable = false)
    @Builder.Default
    private Boolean actif = true;

    @JsonIgnore
    @OneToMany(mappedBy = "livreur", cascade = CascadeType.ALL)
    @Builder.Default
    private List<Colis> colis = new ArrayList<>();

    @PrePersist
    protected void onCreate() {
        if (dateEmbauche == null) {
            dateEmbauche = LocalDateTime.now();
        }
    }
}
