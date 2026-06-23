package br.com.joaowalter.academia.config;

import br.com.joaowalter.academia.model.Papel;
import br.com.joaowalter.academia.model.Usuario;
import br.com.joaowalter.academia.repository.UsuarioRepository;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class UsuarioInicialConfig {

    @Bean
    public ApplicationRunner criarUsuarioAdmin(UsuarioRepository usuarioRepository,
                                               PasswordEncoder passwordEncoder) {
        return args -> {
            if (!usuarioRepository.existsByLogin("admin")) {
                Usuario usuario = new Usuario();

                usuario.setNome("Administrador");
                usuario.setLogin("admin");
                usuario.setSenha(passwordEncoder.encode("123456"));
                usuario.setPapel(Papel.ADMIN);

                usuarioRepository.save(usuario);
            }
        };
    }
}