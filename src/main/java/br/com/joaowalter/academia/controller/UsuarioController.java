package br.com.joaowalter.academia.controller;

import br.com.joaowalter.academia.model.Papel;
import br.com.joaowalter.academia.model.Usuario;
import br.com.joaowalter.academia.service.UsuarioService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public String listar(
            @PageableDefault(size = 5, sort = "nome", direction = Sort.Direction.ASC) Pageable pageable,
            Model model) {

        model.addAttribute("paginaUsuarios", usuarioService.listar(pageable));
        return "usuarios/lista";
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("usuario", new Usuario());
        model.addAttribute("papeis", Papel.values());
        return "usuarios/form";
    }

    @PostMapping("/salvar")
    public String salvar(@RequestParam(required = false) Long id,
                         @RequestParam String nome,
                         @RequestParam String login,
                         @RequestParam(required = false) String senha,
                         @RequestParam Papel papel,
                         Model model) {

        try {
            usuarioService.salvar(id, nome, login, senha, papel);
            return "redirect:/usuarios";
        } catch (IllegalArgumentException e) {
            Usuario usuario = new Usuario();
            usuario.setId(id);
            usuario.setNome(nome);
            usuario.setLogin(login);
            usuario.setPapel(papel);

            model.addAttribute("usuario", usuario);
            model.addAttribute("papeis", Papel.values());
            model.addAttribute("erro", e.getMessage());

            return "usuarios/form";
        }
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        model.addAttribute("usuario", usuarioService.buscarPorId(id));
        model.addAttribute("papeis", Papel.values());
        return "usuarios/form";
    }

    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable Long id, Model model) {
        try {
            usuarioService.excluir(id);
            return "redirect:/usuarios";
        } catch (IllegalArgumentException e) {
            model.addAttribute("erro", e.getMessage());
            model.addAttribute("paginaUsuarios", usuarioService.listar(Pageable.unpaged()));
            return "usuarios/lista";
        }
    }
}