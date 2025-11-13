/**
 * com.projetos.springpad.repository.PadsRepository
 * Repository da entidade `owner`.
 */

package com.projetos.springpad.repository;

import com.projetos.springpad.model.PadsModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PadsRepository extends JpaRepository<PadsModel, Long> {
}