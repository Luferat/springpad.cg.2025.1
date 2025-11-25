/**
 * com.projetos.springpad.repository.PadsRepository
 * Repository da entidade `owner`.
 */

package com.projetos.springpad.repository;

import com.projetos.springpad.dto.PadSummaryDTO;
import com.projetos.springpad.model.PadsModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PadsRepository extends JpaRepository<PadsModel, Long> {

    // Query JPQL com SUBSTRING para resumo de content (1 a 30 caracteres)
    // LEFT JOIN para ownerModel, projeção para DTO
    @Query("SELECT new com.projetos.springpad.dto.PadSummaryDTO(" +
            "p.id, p.title, p.createdAt, SUBSTRING(p.content, 1, 40), " +
            "o.id, o.displayName, o.photoURL) " +
            "FROM PadsModel p LEFT JOIN p.ownerModel o " +
            "WHERE p.status = :status " +
            "ORDER BY p.createdAt DESC")
    List<PadSummaryDTO> findSummariesByStatusOrderByCreatedAtDesc(PadsModel.Status status);

}