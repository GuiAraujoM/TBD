package apresentacao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import negocio.Atendimento;
import negocio.Funcionario;
import persistencia.AtendimentoDAO;
import persistencia.FuncionarioDAO;
import util.JPAUtil;

public class MainTeste {
    public static void main(String[] args){
        List<Atendimento> atendimentos = null;
        String funcId = "1";

        FuncionarioDAO funcDAO = new FuncionarioDAO();

        Funcionario func = new Funcionario();
        func.setCpf("1");
        func = funcDAO.salvar(func);

        AtendimentoDAO atendDAO = new AtendimentoDAO();
        Atendimento ated = new Atendimento();
        ated.setFuncionario(func);
        atendDAO.salvar(ated);        
        
        func = funcDAO.consultarPorCpf("1");

        System.out.println(func);
    }
}
