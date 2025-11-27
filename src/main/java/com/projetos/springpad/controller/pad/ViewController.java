/**
 * com.projetos.springpad.controller.pad.ViewController
 * Exibe os detalhes do "pad" selecionado pelo `pad.id`
 */

package com.projetos.springpad.controller.pad;

import com.projetos.springpad.model.PadsModel;
import com.projetos.springpad.repository.PadsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Controller
public class ViewController {

    @Autowired
    private PadsRepository padsRepository;

    @GetMapping("/ver/{id}")
    public String viewPad(
            @PathVariable Long id,
            Model model,
            @CookieValue(value = "owner_uid", required = false) String ownerUid
    ) {
        // Busca o pad pelo ID
        Optional<PadsModel> optionalPad = padsRepository.findById(id);

        // Verifica se existe e status é ON
        if (optionalPad.isEmpty() || optionalPad.get().getStatus() != PadsModel.Status.ON) {
            return "redirect:/"; // Redireciona para raiz se não encontrado ou status inválido
        }

        // Lista o pad obtido
        PadsModel pad = optionalPad.get();

        // Formata a data para o padrão: dd/MM/yyyy às HH:mm
        String formattedCreatedAt = pad.getCreatedAt().format(
                DateTimeFormatter.ofPattern("dd/MM/yyyy 'às' HH:mm")
        );

        // Verifica se o usuário logado é o owner do pad
        boolean isOwner = false;
        if (ownerUid != null && !ownerUid.isEmpty() && pad.getOwnerModel() != null) {
            isOwner = ownerUid.equals(pad.getOwnerModel().getUid());
        }

        // Adiciona atributos ao model
        model.addAttribute("title", pad.getTitle());
        model.addAttribute("pad", pad); // Objeto completo
        model.addAttribute("formattedCreatedAt", formattedCreatedAt); // Data formatada como string
        model.addAttribute("isOwner", isOwner); // Flag para exibir botões no template

        return "pad/view"; // Renderiza pad/view.html
    }
}