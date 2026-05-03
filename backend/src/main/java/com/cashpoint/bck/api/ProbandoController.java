package com.cashpoint.bck.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/probando")
public class ProbandoController {

        @GetMapping
        public String probando() {
            return "funciona?";
        }

}
