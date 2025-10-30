package org.example.smartspring.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "livreur")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Livreur {

    @Id
    private String id;

    private String nom;
}
