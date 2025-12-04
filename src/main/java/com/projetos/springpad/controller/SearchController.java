/**
 * com.projetos.springpad.controller.SearchController
 * Rotas para pesquisas
 */

package com.projetos.springpad.controller;

import com.projetos.springpad.dto.PadSummaryDTO;
import com.projetos.springpad.model.PadsModel;
import com.projetos.springpad.repository.PadsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class SearchController {

    @Autowired
    private PadsRepository padsRepository;

    @GetMapping("/procurar")
    public String searchPage(
            @RequestParam(name = "q", required = false) String q,
            Model model
    ) {

        List<PadSummaryDTO> pads = padsRepository.searchSummariesByStatusAndTerm(PadsModel.Status.ON, q);

        int total = pads.size();

        model.addAttribute("total", total);
        model.addAttribute("pads", pads);
        model.addAttribute("query", q);

        // q pode ser null
        return "search";
    }

}