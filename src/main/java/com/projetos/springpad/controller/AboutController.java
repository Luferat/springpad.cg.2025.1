/**
 * com.projetos.springpad.controller.AboutController
 * Rotas para as páginas da seção "Sobre".
 */

package com.projetos.springpad.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/sobre") // Endereço base das rotas de "Sobre"
public class AboutController {

    @GetMapping("/site")
    public String aboutSite(Model model) {
        return "about/site";
    }

    @GetMapping("/contatos")
    public String aboutContacts(Model model) {
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