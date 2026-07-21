package br.com.gerenciamento.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.gerenciamento.model.Usuario;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UsuarioRepositoryTest {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Test
    public void buscarLoginTest() {
        Usuario u = new Usuario();
        u.setEmail("busca@login.com");
        u.setUser("userBusca");
        u.setSenha("senhaSecreta");
        usuarioRepository.save(u);

        Usuario encontrado = usuarioRepository.buscarLogin("userBusca", "senhaSecreta");
        Assertions.assertNotNull(encontrado);
        Assertions.assertEquals("busca@login.com", encontrado.getEmail());
    }

    @Test
    public void buscarLoginFalhaTest() {
        Usuario encontrado = usuarioRepository.buscarLogin("userInexistente", "senhaFalsa");
        Assertions.assertNull(encontrado);
    }

    @Test
    public void findByEmailTest() {
        Usuario u = new Usuario();
        u.setEmail("email@especifico.com");
        u.setUser("userEmail");
        u.setSenha("123");
        usuarioRepository.save(u);

        Usuario encontrado = usuarioRepository.findByEmail("email@especifico.com");
        Assertions.assertNotNull(encontrado);
    }

    @Test
    public void salvarUsuarioTest() {
        Usuario u = new Usuario();
        u.setEmail("repo@save.com");
        u.setUser("repoUser");
        u.setSenha("000");
        Usuario salvo = usuarioRepository.save(u);
        Assertions.assertNotNull(salvo.getId());
    }
}
