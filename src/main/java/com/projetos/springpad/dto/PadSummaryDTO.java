/**
 * com.projetos.springpad.dto.PadSummaryDTO
 * Data Transfer Objects para os "pads"
 */

package com.projetos.springpad.dto;

import lombok.Value;

@Value // Lombok para gerar construtor, getters, etc.
public class PadSummaryDTO {
    Long id;
    String title;
    String contentSummary; // Resumo de 30 caracteres
    Long ownerId;
    String ownerDisplayName;
    String ownerPhotoURL;
}