package persistencia;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import negocio.Atendimento;
import negocio.Funcionario;
import util.JPAUtil;

public class FuncionarioDAO extends GenericoDAO<Funcionario>{
    public Funcionario consultarPorCpf(String cpf) {
        EntityManager em = JPAUtil.getEntityManager();
        AtendimentoDAO atendimentoDAO = new AtendimentoDAO();
        Funcionario funcionario = null;

        try {
            Query query = em.createQuery("SELECT f FROM Funcionario f WHERE f.cpf = :cpf");
            query.setParameter("cpf", cpf);
            List<Funcionario> resultList = query.getResultList();
            funcionario = resultList.get(0);
            
            List<Atendimento> atendimentos = atendimentoDAO.consultarPorFuncionario(funcionario.getId());
            funcionario.setAtendimentos(atendimentos);
            return funcionario;
        } catch (Exception e) {
            funcionario = null;
        } finally {
            em.close();
        }

        return funcionario;
    }

    public List<Funcionario> consultarPorSetor(int setorId) {
        EntityManager em = JPAUtil.getEntityManager();
        List<Funcionario> funcionarios = null;

        try {
            Query query = em.createQuery("SELECT f FROM Funcionario f WHERE f.setor.id = :setorId");
            query.setParameter("setorId", setorId);
            List<Funcionario> resultList = query.getResultList();
            funcionarios = resultList;

            return funcionarios;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            funcionarios = null;
        } finally {
            em.close();
        }

        return funcionarios;
    }

    public List<Funcionario> listar() {
        EntityManager em = JPAUtil.getEntityManager();
        List<Funcionario> funcionarios = em.createNamedQuery("Funcionario.listar", Funcionario.class).getResultList();
        return funcionarios;
    }
}
