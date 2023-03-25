package negocio;

import java.time.LocalDateTime;
import javax.annotation.processing.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import negocio.Funcionario;
import negocio.Paciente;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2023-03-25T14:28:01", comments="EclipseLink-2.7.10.v20211216-rNA")
@StaticMetamodel(Atendimento.class)
public class Atendimento_ { 

    public static volatile SingularAttribute<Atendimento, String> observacao;
    public static volatile SingularAttribute<Atendimento, Paciente> paciente;
    public static volatile SingularAttribute<Atendimento, LocalDateTime> data_hora;
    public static volatile SingularAttribute<Atendimento, Integer> id;
    public static volatile SingularAttribute<Atendimento, Funcionario> funcionario;

}