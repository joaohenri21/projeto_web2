package br.com.joaowalter.academia.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DataIntegrityViolationException.class)
    public String tratarErroDeIntegridade(DataIntegrityViolationException e,
                                          HttpServletRequest request,
                                          Model model) {

        model.addAttribute("status", 500);
        model.addAttribute("erro", "Erro de integridade dos dados");
        model.addAttribute("mensagem", "Não foi possível concluir a operação porque existem dados vinculados.");
        model.addAttribute("caminho", request.getRequestURI());
        model.addAttribute("dataHora", LocalDateTime.now());

        return "error/500";
    }

    @ExceptionHandler(RuntimeException.class)
    public String tratarRuntimeException(RuntimeException e,
                                         HttpServletRequest request,
                                         Model model) {

        model.addAttribute("status", 500);
        model.addAttribute("erro", "Erro interno");
        model.addAttribute("mensagem", e.getMessage());
        model.addAttribute("caminho", request.getRequestURI());
        model.addAttribute("dataHora", LocalDateTime.now());

        return "error/500";
    }

    @ExceptionHandler(Exception.class)
    public String tratarException(Exception e,
                                  HttpServletRequest request,
                                  Model model) {

        model.addAttribute("status", 500);
        model.addAttribute("erro", "Erro inesperado");
        model.addAttribute("mensagem", "Ocorreu um erro inesperado no sistema.");
        model.addAttribute("caminho", request.getRequestURI());
        model.addAttribute("dataHora", LocalDateTime.now());

        return "error/500";
    }
}