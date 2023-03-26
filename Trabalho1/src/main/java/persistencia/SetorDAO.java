package persistencia;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import negocio.Setor;
import util.JPAUtil;

public class SetorDAO extends GenericoDAO<Setor>{
    public Setor consultarPorNome(String nome){
        EntityManager em = JPAUtil.getEntityManager();
        Setor setor = null;

        try {
            Query query = em.createQuery("SELECT s FROM Setor s WHERE s.nome = :nome");
            query.setParameter("nome", nome);
            List<Setor> resultList = query.getResultList();
            setor = resultList.get(0);
        } catch (Exception e){
            setor = null;
        } finally {
            em.close();
        }

        return setor;   
    }
}
