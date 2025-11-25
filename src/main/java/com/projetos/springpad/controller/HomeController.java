/**
 * com.projetos.springpad.controller.HomeController
 * Rota da página inicial do site.
 */

package com.projetos.springpad.controller;

import com.projetos.springpad.dto.PadSummaryDTO;
import com.projetos.springpad.model.PadsModel;
import com.projetos.springpad.repository.PadsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private PadsRepository padsRepository;

    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    @GetMapping("/")
    public String home(Model model) {
        // Busca resumos com status ON, ordenados por createdAt DESC
        List<PadSummaryDTO> padsSummaries = padsRepository.findSummariesByStatusOrderByCreatedAtDesc(PadsModel.Status.ON);

        model.addAttribute("pads", padsSummaries);
        model.addAttribute("title", "Página Inicial");

        return "home"; // home.html
    }
}