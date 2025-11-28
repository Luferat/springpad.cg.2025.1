/**
 * com.projetos.springpad.dto.PadSummaryDTO
 * Data Transfer Objects para os "pads"
 */

package com.projetos.springpad.dto;

import lombok.Value;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Value // Lombok para gerar construtor, getters, etc.
public class PadSummaryDTO {
    Long id;
    String title;
    LocalDateTime createdAt;
    String contentSummary; // Resumo de 30 caracteres
    Long ownerId;
    String ownerDisplayName;
    String ownerPhotoURL;

    public String getCreatedAtFormatted() {
        return createdAt.format(DateTimeFormatter.ofPattern("dd/MM/yyyy 'às' HH:mm"));
    }
}