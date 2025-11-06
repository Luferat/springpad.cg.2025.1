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

    private LocalDateTime createdAt;
    private LocalDateTime lastLoginAt;
    private String displayName;

    @Column(unique = true)
    private String email;
    private String photoURL;

    @Enumerated(EnumType.STRING)
    @Column(
            // Garante que o H2 inclua o DEFAULT 'ON' na instrução CREATE TABLE
            columnDefinition = "VARCHAR(3) DEFAULT 'ON'"
    )
    private Status status = Status.ON;

    @Column(columnDefinition = "TEXT")
    private String metadata;

    public enum Status {
        ON, OFF, DEL
    }
}
 