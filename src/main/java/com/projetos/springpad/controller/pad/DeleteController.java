/**
 * com.projetos.springpad.controller.pad.DeleteController
 * Apaga o registro identificado pelo `pad.id` (soft delete via status=DEL)
 */

package com.projetos.springpad.controller.pad;

import com.projetos.springpad.model.PadsModel;
import com.projetos.springpad.repository.PadsRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
public class DeleteController {

    @Autowired
    private PadsRepository padsRepository;

    @GetMapping("/apaga/{id}")
    public String deletePad(
            @PathVariable Long id,
            @CookieValue(value = "owner_uid", required = false) String ownerUid,
            RedirectAttributes redirectAttributes,
            HttpSession session // força criação da sessão
    ) {
        // Pesquisa no banco de dados pelo pad com o {id}
        Optional<PadsModel> optionalPad = padsRepository.findById(id);

        // Se o pad não existe OU o status do pad é diferente de 'ON'
        if (optionalPad.isEmpty() || optionalPad.get().getStatus() != PadsModel.Status.ON) {
            // Mostra a home page
            return "redirect:/";
        }

        // Obtém os dados do Pad do banco de dados
        PadsModel pad = optionalPad.get();

        // Por padrão NÃO é proprietário
        boolean isOwner = false;
        // Se o cookie existe E uid do usuário está vazio E se o usuário existe no Pad
        if (ownerUid != null && !ownerUid.isEmpty() && pad.getOwnerModel() != null) {
            isOwner = ownerUid.equals(pad.getOwnerModel().getUid());
        }

        // Se não é o owner
        if (!isOwner) {
            // Redireciona para a visão do pad
            return "redirect:/ver/" + id;
        }

        // Altera o status do pad para 'DEL', "apagando-o"
        pad.setStatus(PadsModel.Status.DEL);
        padsRepository.save(pad);

        // Monta mensagem de confirmação
        redirectAttributes.addFlashAttribute("flashMessage", pad.getTitle() + " excluído com sucesso!");
        redirectAttributes.addFlashAttribute("flashStyle", "success");  // success, danger, warning, info etc.

        // Redireciona para a página inicial
        return "redirect:/";
    }

}