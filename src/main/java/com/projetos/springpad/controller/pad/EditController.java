/**
 * com.projetos.springpad.controller.pad.EditController
 */

package com.projetos.springpad.controller.pad;

import com.projetos.springpad.model.PadsModel;
import com.projetos.springpad.repository.OwnerRepository;
import com.projetos.springpad.repository.PadsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.HtmlUtils;

import java.util.Optional;

@Controller
public class EditController {

    private final PadsRepository padsRepository;
    private final OwnerRepository ownerRepository;

    @Autowired
    public EditController(PadsRepository padsRepository, OwnerRepository ownerRepository) {
        this.padsRepository = padsRepository;
        this.ownerRepository = ownerRepository;
    }

    // GET - exibe o formulário de edição
    @GetMapping("/edita/{id}")
    public String editPadForm(
            @PathVariable Long id,
            @CookieValue(value = "owner_uid", required = false) String ownerUid,
            Model model
    ) {
        if (ownerUid == null || ownerUid.isEmpty()) {
            return "redirect:/";
        }

        Optional<PadsModel> optionalPad = padsRepository.findById(id);
        if (optionalPad.isEmpty() || optionalPad.get().getStatus() != PadsModel.Status.ON) {
            return "redirect:/";
        }

        PadsModel pad = optionalPad.get();

        // verifica owner
        if (pad.getOwnerModel() == null || !ownerUid.equals(pad.getOwnerModel().getUid())) {
            return "redirect:/ver/" + id;
        }

        model.addAttribute("pad", pad);
        model.addAttribute("title", "Editar: " + pad.getTitle());

        return "pad/edit";
    }

    // POST - processa a edição
    @PostMapping("/edita/{id}")
    public String updatePad(
            @PathVariable Long id,
            @CookieValue(value = "owner_uid", required = false) String ownerUid,
            @RequestParam("padtitle") String title,
            @RequestParam("padcontent") String content,
            RedirectAttributes redirectAttributes
    ) {
        if (ownerUid == null || ownerUid.isEmpty()) {
            return "redirect:/";
        }

        Optional<PadsModel> optionalPad = padsRepository.findById(id);
        if (optionalPad.isEmpty() || optionalPad.get().getStatus() != PadsModel.Status.ON) {
            return "redirect:/";
        }

        PadsModel pad = optionalPad.get();

        // verifica owner
        if (pad.getOwnerModel() == null || !ownerUid.equals(pad.getOwnerModel().getUid())) {
            return "redirect:/ver/" + id;
        }

        // Sanitização simples
        String sanitizedTitle = HtmlUtils.htmlEscape(title.trim());
        String sanitizedContent = HtmlUtils.htmlEscape(content.trim());

        // Se o título está vazio
        if (sanitizedTitle.isEmpty()) {
            // Recarrega o formulário
            return "redirect:/edita/" + id;
        }

        // Aplica alterações
        pad.setTitle(sanitizedTitle);
        pad.setContent(sanitizedContent);
        padsRepository.save(pad);

        /*
        // Cria mensagem de confirmação
        redirectAttributes.addFlashAttribute(
                "successMessage",
                sanitizedTitle + " atualizado com sucesso!"
        );
        */

        redirectAttributes.addFlashAttribute("flashMessage", sanitizedTitle + " atualizado com sucesso!");
        redirectAttributes.addFlashAttribute("flashStyle", "success");  // success, danger, warning, info etc.

        // Redireciona para a visão d pad editado
        return "redirect:/ver/" + id;
    }
}