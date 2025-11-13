/**
 * com.projetos.springpad.repository.OwnerRepository
 * Repository da entidade `owner`.
 */

package com.projetos.springpad.repository;

import com.projetos.springpad.model.OwnerModel;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface OwnerRepository extends JpaRepository<OwnerModel, Long> {
    Optional<OwnerModel> findByUid(String uid);
}