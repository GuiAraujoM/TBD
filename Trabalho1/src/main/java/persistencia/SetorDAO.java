package persistencia;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import negocio.Setor;
import util.JPAUtil;

public class SetorDAO extends GenericoDAO<Setor>{
    public Setor consultarPorNome(String nome){
        FuncionarioDAO funcDAO = new FuncionarioDAO();
        EntityManager em = JPAUtil.getEntityManager();
        Setor setor = null;

        try {
            Query query = em.createQuery("SELECT s FROM Setor s WHERE s.nome = :nome");
            query.setParameter("nome", nome);
            List<Setor> resultList = query.getResultList();
            setor = resultList.get(0);
                       
            setor.setfuncionarios(funcDAO.consultarPorSetor(setor.getId()));
            return setor;
        } catch (Exception e){
            System.out.println(e.getMessage());
            setor = null;
        } finally {
            em.close();
        }

        return setor;   
    }
}
