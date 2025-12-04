/**
 * com.projetos.springpad.controller.pad.NewController
 * Rota da página de cadastro de novo pad.
 */

package com.projetos.springpad.controller.pad;

import com.projetos.springpad.model.OwnerModel;
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
public class NewController {

    private final PadsRepository padsRepository;
    private final OwnerRepository ownerRepository;

    @Autowired
    public NewController(PadsRepository padsRepository, OwnerRepository ownerRepository) {
        this.padsRepository = padsRepository;
        this.ownerRepository = ownerRepository;
    }

    // Rota GET para exibir o formulário de novo pad
    @GetMapping("/novo")
    public String newPadForm(
            @CookieValue(value = "owner_uid", required = false) String ownerUid,
            Model model
    ) {
        // Redireciona para a raiz se o cookie não existir ou estiver vazio
        if (ownerUid == null || ownerUid.isEmpty()) {
            return "redirect:/";
        }
        model.addAttribute("title", "Novo Pad");
        return "pad/new";
    }

    // Rota POST para processar o formulário e salvar o novo pad
    @PostMapping("/novo")
    public String createNewPad(
            @CookieValue(value = "owner_uid", required = false) String ownerUid,
            @RequestParam("padtitle") String title,
            @RequestParam("padcontent") String content,
            RedirectAttributes redirectAttributes
    ) {
        // Redireciona para a raiz se o cookie não existir ou estiver vazio
        if (ownerUid == null || ownerUid.isEmpty()) {
            return "redirect:/";
        }

        // Busca o owner pelo UID do cookie
        Optional<OwnerModel> optionalOwner = ownerRepository.findByUid(ownerUid);
        if (optionalOwner.isEmpty()) {
            // Caso o owner não seja encontrado, redireciona para a página inicial
            return "redirect:/";
        }

        // Obtém o 'id' do owner logado
        Long ownerId = optionalOwner.get().getId();

        // Sanitização mínima: Escapa HTML para prevenir XSS em title e content
        String sanitizedTitle = HtmlUtils.htmlEscape(title.trim());
        String sanitizedContent = HtmlUtils.htmlEscape(content.trim());

        // Validação mínima: Garante que title não esteja vazio
        if (sanitizedTitle.isEmpty()) {
            // Pode adicionar lógica de erro, mas por simplicidade, redireciona de volta ao form
            return "redirect:/novo"; // Idealmente, passar erro via model, mas mantendo simples
        }

        // Cria o novo PadsModel com valores default
        PadsModel newPad = PadsModel.builder()
                .title(sanitizedTitle)
                .content(sanitizedContent)
                .owner(ownerId)
                .status(PadsModel.Status.ON)
                .metadata(null) // Ou valor default se necessário
                .build();

        // Salva no repositório
        padsRepository.save(newPad);

        // Prepara a mensagem de feedback com o título do pad
        // Adiciona como flash attribute para ser exibida na home
        redirectAttributes.addFlashAttribute("flashMessage", sanitizedTitle + " cadastrado com sucesso!");
        redirectAttributes.addFlashAttribute("flashStyle", "success");  // success, danger, warning, info etc.

        // Redireciona para a página inicial
        return "redirect:/";
    }
}