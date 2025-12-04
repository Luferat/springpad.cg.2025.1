/**
 * com.projetos.springpad.model.ContactsModel
 * Model da entidade `contacts`
 */

package com.projetos.springpad.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "contacts")
public class ContactsModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;
    private String subject;
    private String message;

    @Enumerated(EnumType.STRING)
    private Status status = Status.RECEBIDO;

    // Reservado para uso futuro
    @Column(columnDefinition = "TEXT")
    private String metadata;

    public enum Status {
        RECEBIDO, LIDO, RESPONDIDO, APAGADO
    }
}
 