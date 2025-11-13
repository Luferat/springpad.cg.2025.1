/**
 * com.projetos.springpad.controller.owner.LoginController
 * Executa a persistência quando o usuário faz login.
 */

package com.projetos.springpad.controller.owner;

import lombok.RequiredArgsConstructor;
import com.projetos.springpad.model.OwnerModel;
import com.projetos.springpad.repository.OwnerRepository;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/owner")
@RequiredArgsConstructor
public class LoginController {

    private final OwnerRepository ownerRepository;

    @PostMapping("/login")
    public ResponseEntity<String> login(
            @RequestBody Map<String, Object> data,
            HttpServletResponse response
    ) {

        // Sanitização básica
        String uid = data.get("uid").toString().trim();
        String email = data.get("email").toString().trim().toLowerCase();
        String displayName = data.get("displayName").toString().trim();
        String photoURL = data.get("photoURL") != null ? data.get("photoURL").toString().trim() : "";
        String createdAt = data.get("createdAt").toString();
        String lastLoginAt = data.get("lastLoginAt").toString();

        // Buscar ou criar owner
        Optional<OwnerModel> existingOwner = ownerRepository.findByUid(uid);

        String message;
        if (existingOwner.isPresent()) {
            // Atualizar existente
            OwnerModel owner = existingOwner.get();
            owner.setDisplayName(displayName);
            owner.setEmail(email);
            owner.setPhotoURL(photoURL);
            owner.setLastLoginAt(lastLoginAt);
            ownerRepository.save(owner);
            message = "OK - Usuário atualizado";
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
            message = "OK - Usuário criado";
        }

        // Criar cookie seguro com o UID
        ResponseCookie cookie = ResponseCookie.from("owner_uid", uid)
                .httpOnly(true)       // Protege contra acesso via JavaScript
                .secure(true)         // Envia apenas via HTTPS (ajuste para false em dev local se necessário)
                .path("/")            // Disponível em todo o app
                .maxAge(3600 * 24)    // Expira em 1 dia (ajuste conforme necessário)
                .sameSite("Strict")   // Protege contra CSRF
                .build();

        // Retornar resposta com cookie no header
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.SET_COOKIE, cookie.toString());

        return ResponseEntity.ok()
                .headers(headers)
                .body(message);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletResponse response) {
        // Criar cookie para expirar o existente (maxAge=0)
        ResponseCookie cookie = ResponseCookie.from("owner_uid", "")
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(0)            // Expira imediatamente
                .sameSite("Strict")
                .build();

        // Retornar resposta com cookie expirado no header
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.SET_COOKIE, cookie.toString());

        return ResponseEntity.ok()
                .headers(headers)
                .body("OK - Logout realizado");
    }

}