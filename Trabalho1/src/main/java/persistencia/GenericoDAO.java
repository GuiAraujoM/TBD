package persistencia;

import javax.persistence.EntityManager;

import negocio.EntidadeGenerica;
import util.JPAUtil;

public class GenericoDAO <T extends EntidadeGenerica>{
    public T salvar(T t){
        EntityManager em = JPAUtil.getEntityManager();
        try {            
            em.getTransaction().begin();
            if (t.getId() == 0) {
                em.persist(t);
            } else {
                t = em.merge(t);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            throw e;
        } finally {
            em.close();
        }

        return t;
    }

    public void remover(Class<T> cls, int id){
        EntityManager em = JPAUtil.getEntityManager();
        T t = em.find(cls, id);
        try {
            em.getTransaction().begin();
            em.remove(t);
            em.getTransaction().commit();
        } catch (Exception e) {            
            throw e;
        } finally {
            em.close();
        }
    }

    public T consultarPorId(Class<T> cls, int id){
        EntityManager em = JPAUtil.getEntityManager();
        T t = null;

        try {
            t = em.find(cls, id);
        } catch (Exception e) {
            t = null;
        } finally {
            em.close();
        }

        return t;
    }
}
