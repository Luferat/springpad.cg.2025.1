/**
 * com.projetos.springpad.controller.AboutController
 * Rotas para as páginas da seção "Sobre".
 */

package com.projetos.springpad.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AboutController {

    @GetMapping("/sobre")
    public String about(Model model) {
        return "about";
    }
}