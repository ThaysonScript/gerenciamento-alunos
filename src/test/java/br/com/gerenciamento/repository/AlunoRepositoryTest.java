package br.com.gerenciamento.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.gerenciamento.enums.Curso;
import br.com.gerenciamento.enums.Turno;
import br.com.gerenciamento.model.Aluno;

import br.com.gerenciamento.enums.Status;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AlunoRepositoryTest {

    @Autowired
    private AlunoRepository alunoRepository;

    @Test
    public void findByStatusInativoTest() {
        Aluno alunoAtivo = new Aluno();
        alunoAtivo.setNome("Joao Ativo");
        alunoAtivo.setTurno(Turno.MATUTINO);
        alunoAtivo.setCurso(Curso.BIOMEDICINA);
        alunoAtivo.setStatus(Status.ATIVO);
        alunoAtivo.setMatricula("100100");
        alunoRepository.save(alunoAtivo);

        Aluno alunoInativo = new Aluno();
        alunoInativo.setNome("Maria Inativa");
        alunoInativo.setTurno(Turno.NOTURNO);
        alunoInativo.setCurso(Curso.DIREITO);
        alunoInativo.setStatus(Status.INATIVO);
        alunoInativo.setMatricula("200200");
        alunoRepository.save(alunoInativo);

        List<Aluno> listaInativos = alunoRepository.findByStatusInativo();
        
        Assertions.assertTrue(listaInativos.stream().allMatch(a -> a.getStatus() == Status.INATIVO));
        Assertions.assertTrue(listaInativos.stream().anyMatch(a -> a.getNome().equals("Maria Inativa")));
        Assertions.assertFalse(listaInativos.stream().anyMatch(a -> a.getNome().equals("Joao Ativo")));
    }

    @Test
    public void findByStatusAtivoTest() {
        List<Aluno> listaAtivos = alunoRepository.findByStatusAtivo();
        Assertions.assertTrue(listaAtivos.stream().allMatch(a -> a.getStatus() == Status.ATIVO));
    }

    @Test
    public void saveAlunoTest() {
        Aluno aluno = new Aluno();
        aluno.setNome("Ana Salva");
        aluno.setTurno(Turno.NOTURNO);
        aluno.setCurso(Curso.ENFERMAGEM);
        aluno.setStatus(Status.ATIVO);
        aluno.setMatricula("444555");
        Aluno salvo = alunoRepository.save(aluno);
        Assertions.assertNotNull(salvo.getId());
    }

    @Test
    public void findAllAlunosTest() {
        List<Aluno> todos = alunoRepository.findAll();
        Assertions.assertNotNull(todos);
    }
}
