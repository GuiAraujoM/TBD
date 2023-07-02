package apresentacao;

import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.LocalDate;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Session;
import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.MappingManager;
import com.datastax.driver.mapping.Result;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import negocio.Pessoa;
import persistencia.PessoaDAO;

public class Main {

    public static void main(String[] args) {
        PessoaDAO.deletarTudo();

        //CREATE
        System.out.println("Criando pessoa p1");
        ArrayList<String> gostos = new ArrayList<String>();
        gostos.add("desenvolvimento");
        gostos.add("jogos");
        Pessoa p1 = new Pessoa("12345678910", "Guilherme", LocalDate.fromYearMonthDay(1998, 11, 3), gostos);
        PessoaDAO.criar(p1);
        
        //READ
        System.out.println("Lendo pessoa p1");
        System.out.println(PessoaDAO.obterPorCpf("12345678910"));

        //UPDATE
        System.out.println("Atualizando pessoa p1");
        p1.getGostos().add("passar na disciplina de TDB");
        p1.getGostos().add("e terminar o TADS");
        p1.setNome("Guilherme de Araujo Medeiros");

        PessoaDAO.atualizar(p1);

        System.out.println("Lendo pessoa p1 atualizada");
        System.out.println(PessoaDAO.obterPorCpf("12345678910"));

        //DELETE
        PessoaDAO.deletar("12345678910");
        System.out.println("Lendo pessoa p1 após deleção: " + PessoaDAO.obterPorCpf("12345678910"));
    }
}
