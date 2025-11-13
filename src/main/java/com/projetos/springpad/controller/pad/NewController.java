/**
 * com.projetos.springpad.controller.NewController
 * Rota da página de cadastro de novo pad.
 */

package com.projetos.springpad.controller.pad;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/pad")
public class NewController {

    // Rota da página inicial
    @GetMapping("/novo")
    public String newPad(
            @CookieValue(value = "owner_uid", required = false) String ownerUid,
            Model model
    ) {

        System.out.println("\n\n\n");
        System.out.println(ownerUid);
        System.out.println("\n\n\n");

        // Redireciona para a raiz se o cookie não existir ou estiver vazio
        if (ownerUid == null || ownerUid.isEmpty()) {
            return "redirect:/";
        }

        model.addAttribute("title", "Título da página");

        return "pad/new"; // home.html
    }
}