package persistencia;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import negocio.Atendimento;
import negocio.Paciente;
import util.JPAUtil;

public class PacienteDAO extends GenericoDAO<Paciente>{
    public Paciente consultarPorCpf(String cpf) {
        AtendimentoDAO atendimentoDAO = new AtendimentoDAO();
        EntityManager em = JPAUtil.getEntityManager();
        Paciente paciente = null;

        try {
            Query query = em.createQuery("SELECT p FROM Paciente p WHERE p.cpf = :cpf");
            query.setParameter("cpf", cpf);
            List<Paciente> resultList = query.getResultList();
            paciente = resultList.get(0);

            List<Atendimento> atendimentos = atendimentoDAO.consultarPorPaciente(paciente.getId());
            paciente.setAtendimentos(atendimentos);
            return paciente;            
        } catch (Exception e) {
            paciente = null;
        } finally {
            em.close();
        }

        return paciente;
    }
}
