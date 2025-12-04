/**
 * com.projetos.springpad.repository.PadsRepository
 * Repository da entidade pads.
 */

package com.projetos.springpad.repository;

import com.projetos.springpad.dto.PadSummaryDTO;
import com.projetos.springpad.model.PadsModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PadsRepository extends JpaRepository<PadsModel, Long> {

    // Query JPQL com SUBSTRING para resumo de content (1 a 30 caracteres)
    // LEFT JOIN para ownerModel, projeção para DTO
    @Query("SELECT new com.projetos.springpad.dto.PadSummaryDTO(" +
            "p.id, p.title, p.createdAt, SUBSTRING(p.content, 1, 30), " +
            "o.id, o.displayName, o.photoURL) " +
            "FROM PadsModel p LEFT JOIN p.ownerModel o " +
            "WHERE p.status = :status " +
            "ORDER BY p.createdAt DESC")
    List<PadSummaryDTO> findSummariesByStatusOrderByCreatedAtDesc(PadsModel.Status status);

    // Adicione ao interface existente
    @Query("SELECT p FROM PadsModel p LEFT JOIN FETCH p.ownerModel WHERE p.id = :id")
    Optional<PadsModel> findByIdWithOwner(@Param("id") Long id);

    // Query para pesquisa
    @Query("""
    SELECT new com.projetos.springpad.dto.PadSummaryDTO(
        p.id, p.title, p.createdAt, SUBSTRING(p.content, 1, 30),
        o.id, o.displayName, o.photoURL
    )
    FROM PadsModel p
    LEFT JOIN p.ownerModel o
    WHERE p.status = :status
      AND (
            LOWER(p.title)   LIKE LOWER(CONCAT('%', :term, '%')) OR
            LOWER(p.content) LIKE LOWER(CONCAT('%', :term, '%'))
          )
    ORDER BY p.createdAt DESC
    """)
    List<PadSummaryDTO> searchSummariesByStatusAndTerm(
            @Param("status") PadsModel.Status status,
            @Param("term") String term);

}