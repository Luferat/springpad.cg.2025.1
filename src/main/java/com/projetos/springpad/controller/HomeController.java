/**
 * com.projetos.springpad.controller.HomeController
 * Rota da página inicial.
 */

package com.projetos.springpad.controller;

import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    // Rota da página inicial
    @GetMapping("/")
    public String home(Model model) {

        model.addAttribute("title", "Título da página");

        return "home"; // home.html
    }
}
