package persistencia;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import negocio.Paciente;
import util.JPAUtil;

public class PacienteDAO extends GenericoDAO<Paciente>{
    public Paciente consultarPorCpf(String cpf) {
        EntityManager em = JPAUtil.getEntityManager();
        Paciente paciente = null;

        try {
            Query query = em.createQuery("SELECT p FROM Paciente p WHERE p.cpf = :cpf");
            query.setParameter("cpf", cpf);
            List<Paciente> resultList = query.getResultList();
            paciente = resultList.get(0);
        } catch (Exception e) {
            paciente = null;
        } finally {
            em.close();
        }

        return paciente;
    }
}
