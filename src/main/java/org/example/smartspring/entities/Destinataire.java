package org.example.smartspring.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "destinataire")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Destinataire {

    @Id
    private String id;

    private String nom;
}
