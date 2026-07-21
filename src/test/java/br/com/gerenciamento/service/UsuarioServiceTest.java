package br.com.gerenciamento.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.gerenciamento.exception.EmailExistsException;
import br.com.gerenciamento.model.Usuario;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UsuarioServiceTest {

    @Autowired
    private ServiceUsuario serviceUsuario;

    @Test
    public void salvarUsuarioEmailExistenteTest() throws Exception {
        Usuario u1 = new Usuario();
        u1.setEmail("admin@admin.com");
        u1.setUser("admin1");
        u1.setSenha("12345");
        serviceUsuario.salvarUsuario(u1);

        Usuario u2 = new Usuario();
        u2.setEmail("admin@admin.com");
        u2.setUser("admin2");
        u2.setSenha("54321");

        Assert.assertThrows(EmailExistsException.class, () -> {
            serviceUsuario.salvarUsuario(u2);
        });
    }

    @Test
    public void salvarUsuarioSucessoTest() throws Exception {
        Usuario u = new Usuario();
        u.setEmail("novo@usuario.com");
        u.setUser("novo_user");
        u.setSenha("senha123");
        
        serviceUsuario.salvarUsuario(u);
        Assert.assertNotNull(u.getId());
    }

    @Test
    public void loginUserSucessoTest() throws Exception {
        Usuario u = new Usuario();
        u.setEmail("loginsucesso@escola.com");
        u.setUser("loginSucesso");
        u.setSenha("123");
        serviceUsuario.salvarUsuario(u);
        
        // senhaEncriptada
        String senhaEncriptada = br.com.gerenciamento.util.Util.md5("123");
        Usuario usuarioLogado = serviceUsuario.loginUser("loginSucesso", senhaEncriptada);

        Assert.assertNotNull(usuarioLogado);
        Assert.assertEquals("loginsucesso@escola.com", usuarioLogado.getEmail());
    }

    @Test
    public void loginUserFalhaTest() throws Exception {
        Usuario usuarioLogado = serviceUsuario.loginUser("loginInexistente", "senhaFalsa");
        Assert.assertNull(usuarioLogado);
    }
}
