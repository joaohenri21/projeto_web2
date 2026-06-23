package br.com.joaowalter.academia.controller;

import br.com.joaowalter.academia.service.RelatorioService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/relatorios")
public class RelatorioController {

    private final RelatorioService relatorioService;

    public RelatorioController(RelatorioService relatorioService) {
        this.relatorioService = relatorioService;
    }

    @GetMapping("/turmas")
    public ResponseEntity<byte[]> relatorioTurmas() {
        byte[] pdf = relatorioService.gerarRelatorioTurmas();

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=relatorio-turmas.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdf);
    }
}