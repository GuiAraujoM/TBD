package persistencia;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import negocio.Atendimento;
import util.JPAUtil;

public class AtendimentoDAO extends GenericoDAO<Atendimento>{
    public List<Atendimento> consultarPorFuncionario(int funcId){
        EntityManager em = JPAUtil.getEntityManager();
        List<Atendimento> atendimentos = null;

        try {
            Query query = em.createQuery("SELECT a FROM Atendimento a WHERE a.funcionario.id = :funcId");
            query.setParameter("funcId", funcId);
            List<Atendimento> resultList = query.getResultList();            
            atendimentos = resultList;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            atendimentos = null;
        } finally {
            em.close();
        }

        return atendimentos;
    }

    public List<Atendimento> consultarPorPaciente(int pacId) {
        EntityManager em = JPAUtil.getEntityManager();
        List<Atendimento> atendimentos = null;

        try {
            Query query = em.createQuery("SELECT a FROM Atendimento a WHERE a.paciente.id = :pacId");
            query.setParameter("pacId", pacId);
            List<Atendimento> resultList = query.getResultList();
            atendimentos = resultList;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            atendimentos = null;
        } finally {
            em.close();
        }

        return atendimentos;
    }

    public List<Atendimento> listar(){
        EntityManager em = JPAUtil.getEntityManager();
        List<Atendimento> atendimentos = em.createNamedQuery("Atendimento.listar", Atendimento.class).getResultList();
        return atendimentos;
    }
}
