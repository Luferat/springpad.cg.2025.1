/**
 * com.projetos.springpad.controller.AboutController
 * Rotas para as páginas da seção "Sobre".
 */

package com.projetos.springpad.controller;

import com.projetos.springpad.model.PadsModel;
import com.projetos.springpad.repository.OwnerRepository;
import com.projetos.springpad.repository.PadsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/sobre") // Endereço base das rotas de "Sobre"
public class AboutController {

    private final PadsRepository padsRepository;
    private final OwnerRepository ownerRepository;

    @Autowired
    public AboutController(PadsRepository padsRepository, OwnerRepository ownerRepository) {
        this.padsRepository = padsRepository;
        this.ownerRepository = ownerRepository;
    }

    @GetMapping("/site")
    public String aboutSite(Model model) {
        return "about/site";
    }

    @GetMapping("/contatos")
    public String aboutContacts(
            Model model,
            @CookieValue(value = "owner_uid", required = false) String ownerUid
    ) {

        Map<String, String> form = new HashMap<>();
        form.put("name", "Joca da Silva");
        form.put("email", "joca@email.com");
        form.put("subject", "Assunto do Joca.");
        form.put("message", "Mensagem do Joca.\nMensagem do Joca.\nMensagem do Joca.\nMensagem do Joca.\n");

        model.addAttribute("form", form);

        return "about/contacts";
    }

    @GetMapping("/privacidade")
    public String aboutPrivacy(Model model) {
        return "about/privacy";
    }

    @GetMapping("/quemsomos")
    public String aboutOwner(Model model) {
        return "about/owner";
    }
}