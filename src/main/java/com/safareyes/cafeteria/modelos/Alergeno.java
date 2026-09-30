package com.safareyes.cafeteria.modelos;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "alergeno")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Builder
@ToString

public class Alergeno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nombre;
}
