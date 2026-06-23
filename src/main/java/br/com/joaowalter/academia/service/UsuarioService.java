package br.com.joaowalter.academia.service;

import br.com.joaowalter.academia.model.Papel;
import br.com.joaowalter.academia.model.Usuario;
import br.com.joaowalter.academia.repository.UsuarioRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository usuarioRepository,
                          PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Page<Usuario> listar(Pageable pageable) {
        return usuarioRepository.findAll(pageable);
    }

    public Usuario buscarPorId(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
    }

    public Usuario salvar(Long id,
                          String nome,
                          String login,
                          String senha,
                          Papel papel) {

        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("Informe o nome do usuário.");
        }

        if (login == null || login.isBlank()) {
            throw new IllegalArgumentException("Informe o login do usuário.");
        }

        if (papel == null) {
            throw new IllegalArgumentException("Selecione o papel do usuário.");
        }

        Usuario usuario;

        if (id == null) {
            if (senha == null || senha.isBlank()) {
                throw new IllegalArgumentException("Informe a senha do usuário.");
            }

            if (usuarioRepository.existsByLogin(login)) {
                throw new IllegalArgumentException("Já existe um usuário com este login.");
            }

            usuario = new Usuario();
            usuario.setSenha(passwordEncoder.encode(senha));
        } else {
            usuario = buscarPorId(id);

            if (!usuario.getLogin().equals(login) && usuarioRepository.existsByLogin(login)) {
                throw new IllegalArgumentException("Já existe um usuário com este login.");
            }

            if (senha != null && !senha.isBlank()) {
                usuario.setSenha(passwordEncoder.encode(senha));
            }
        }

        usuario.setNome(nome);
        usuario.setLogin(login);
        usuario.setPapel(papel);

        return usuarioRepository.save(usuario);
    }

    public void excluir(Long id) {
        Usuario usuario = buscarPorId(id);

        if ("admin".equals(usuario.getLogin())) {
            throw new IllegalArgumentException("O usuário admin não pode ser excluído.");
        }

        usuarioRepository.deleteById(id);
    }
}