/**
 * net.luferat.springpad.controller.UserAuthentication
 * Executa a persistência quando o usuário faz login.
 */

package com.projetos.springpad.controller.owner;

import lombok.RequiredArgsConstructor;
import com.projetos.springpad.model.OwnerModel;
import com.projetos.springpad.repository.OwnerRepository;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/owner")
@RequiredArgsConstructor
public class LoginController {

    private final OwnerRepository ownerRepository;

    @PostMapping("/login")
    public String login(@RequestBody Map<String, Object> data) {

        // Sanitização básica
        String uid = data.get("uid").toString().trim();
        String email = data.get("email").toString().trim().toLowerCase();
        String displayName = data.get("displayName").toString().trim();
        String photoURL = data.get("photoURL") != null ? data.get("photoURL").toString().trim() : "";
        String createdAt = data.get("createdAt").toString();
        String lastLoginAt = data.get("lastLoginAt").toString();

        // Buscar ou criar owner
        Optional<OwnerModel> existingOwner = ownerRepository.findByUid(uid);

        if (existingOwner.isPresent()) {
            // Atualizar existente
            OwnerModel owner = existingOwner.get();
            owner.setDisplayName(displayName);
            owner.setEmail(email);
            owner.setPhotoURL(photoURL);
            owner.setLastLoginAt(lastLoginAt);
            ownerRepository.save(owner);
            return "OK - Usuário atualizado";
        } else {
            // Criar novo
            OwnerModel newOwner = new OwnerModel();
            newOwner.setUid(uid);
            newOwner.setDisplayName(displayName);
            newOwner.setEmail(email);
            newOwner.setPhotoURL(photoURL);
            newOwner.setCreatedAt(createdAt);
            newOwner.setLastLoginAt(lastLoginAt);
            ownerRepository.save(newOwner);
            return "OK - Usuário criado";
        }
    }

}