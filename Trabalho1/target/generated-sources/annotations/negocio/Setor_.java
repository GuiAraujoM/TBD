package negocio;

import javax.annotation.processing.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import negocio.Funcionario;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2023-03-26T16:28:09", comments="EclipseLink-2.7.10.v20211216-rNA")
@StaticMetamodel(Setor.class)
public class Setor_ { 

    public static volatile SingularAttribute<Setor, String> nome;
    public static volatile ListAttribute<Setor, Funcionario> atendimentos;
    public static volatile SingularAttribute<Setor, Integer> id;

}