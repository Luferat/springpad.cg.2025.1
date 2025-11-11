/**
 * net.luferat.springpad.model.Owner
 * Model da entidade `owner`
 */

package com.projetos.springpad.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "owners")
public class OwnerModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String uid;

    private String createdAt;
    private String lastLoginAt;
    private String displayName;
    private String email;
    private String photoURL;

    @Enumerated(EnumType.STRING)
    private Status status = Status.ON;

    // Reservado para uso futuro
    @Column(columnDefinition = "TEXT")
    private String metadata;

    public enum Status {
        ON, OFF, DEL
    }
}
 