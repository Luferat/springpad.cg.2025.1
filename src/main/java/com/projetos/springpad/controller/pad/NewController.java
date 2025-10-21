package com.projetos.springpad.controller.pad;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/pad")
public class NewController {

    @GetMapping("/novo")
    public String newPad(Model model) {
        return "pad/new";
    }

}
