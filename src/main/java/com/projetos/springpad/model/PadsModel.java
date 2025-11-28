/**
 * com.projetos.springpad.model.PadsModel
 * Modela oa entidade "pads"
 */

package com.projetos.springpad.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "pads")
public class PadsModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Builder.Default
    @Column(name = "createdAt", nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(length = 127, nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    @Column(nullable = false)
    private Long owner;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "ENUM('ON', 'OFF', 'DEL')", nullable = false)
    private Status status = Status.ON;

    @Column(columnDefinition = "TEXT")
    private String metadata;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner", insertable = false, updatable = false)
    private OwnerModel ownerModel;

    public enum Status {
        ON, OFF, DEL
    }

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
