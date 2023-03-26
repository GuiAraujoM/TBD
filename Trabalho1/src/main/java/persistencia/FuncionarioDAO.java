package persistencia;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import negocio.Funcionario;
import util.JPAUtil;

public class FuncionarioDAO extends GenericoDAO<Funcionario>{
    public Funcionario consultarPorCpf(String cpf) {
        EntityManager em = JPAUtil.getEntityManager();
        Funcionario funcionario = null;

        try {
            Query query = em.createQuery("SELECT f FROM Funcionario f WHERE f.cpf = :cpf");
            query.setParameter("cpf", cpf);
            List<Funcionario> resultList = query.getResultList();
            funcionario = resultList.get(0);
        } catch (Exception e) {
            funcionario = null;
        } finally {
            em.close();
        }

        return funcionario;
    }
}
