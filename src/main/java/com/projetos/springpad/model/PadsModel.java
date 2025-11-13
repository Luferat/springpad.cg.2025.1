/**
 * com.projetos.springpad.model.PadsModel
 * Modela oa entidade "pads"
 */

package com.projetos.springpad.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "pads")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PadsModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "createdAt", nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(length = 127, nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    @Column(nullable = false)
    private Long owner;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "ENUM('ON', 'OFF', 'DEL')", nullable = false)
    private Status status = Status.ON;

    @Column(columnDefinition = "TEXT")
    private String metadata;

    // Relação com Owner (opcional: para navegação)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner", insertable = false, updatable = false)
    private OwnerModel ownerModel;

    // Enum para status
    public enum Status {
        ON, OFF, DEL
    }

    // Sobrescreve toString para evitar stack overflow com relacionamento bidirecional
    @Override
    public String toString() {
        return "PadsModel{" +
                "id=" + id +
                ", createdAt=" + createdAt +
                ", title='" + title + '\'' +
                ", status=" + status +
                ", owner=" + owner +
                '}';
    }
}