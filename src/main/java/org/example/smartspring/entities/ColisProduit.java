package org.example.smartspring.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "colis_produit")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ColisProduit {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "id", columnDefinition = "CHAR(36)")
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "colis_id", nullable = false)
    @JsonIgnore
    private Colis colis;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "produit_id", nullable = false)
    private Produit produit;

    @Column(name = "quantite", nullable = false)
    private Integer quantite;

    @Column(name = "prix", nullable = false, precision = 10, scale = 2)
    private BigDecimal prix; // Prix total = prixUnitaire * quantite

    @Column(name = "date_ajout", nullable = false)
    private LocalDateTime dateAjout;

    @PrePersist
    protected void onCreate() {
        if (dateAjout == null) {
            dateAjout = LocalDateTime.now();
        }
    }
}
