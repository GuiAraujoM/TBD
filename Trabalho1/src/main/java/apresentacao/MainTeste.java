package apresentacao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import negocio.Funcionario;
import util.JPAUtil;

public class MainTeste {
    public static void Main2(String[] args){
        EntityManager em = JPAUtil.getEntityManager();
        Funcionario funcionario = null;
        String cpf = "1";

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
    }
}
