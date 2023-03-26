package negocio;

import javax.annotation.processing.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import negocio.Atendimento;
import negocio.Setor;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2023-03-26T18:46:13", comments="EclipseLink-2.7.10.v20211216-rNA")
@StaticMetamodel(Funcionario.class)
public class Funcionario_ { 

    public static volatile SingularAttribute<Funcionario, String> senha;
    public static volatile SingularAttribute<Funcionario, Setor> setor;
    public static volatile SingularAttribute<Funcionario, String> cpf;
    public static volatile SingularAttribute<Funcionario, String> nome;
    public static volatile ListAttribute<Funcionario, Atendimento> atendimentos;
    public static volatile SingularAttribute<Funcionario, Integer> id;
    public static volatile SingularAttribute<Funcionario, String> email;

}