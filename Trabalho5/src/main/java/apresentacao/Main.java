/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package apresentacao;

import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.LocalDate;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Session;
import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.MappingManager;
import com.datastax.driver.mapping.Result;
import java.util.List;
import negocio.Pessoa;

/**
 *
 * @author iapereira
 */
public class Main {

    public static void main(String[] args) {

        Cluster cluster = null;
        try {
//            cluster = Cluster.builder().addContactPoint("127.0.0.1")
//                    .build();
            cluster = Cluster.builder().addContactPoint("localhost").build();
            Session session = cluster.connect("exemplo");
//
//           

//            ResultSet results = session.execute ("select * from pessoa");
            Mapper<Pessoa> pessoaMapper = new MappingManager(session)
                    .mapper(Pessoa.class);
//            pessoaMapper.save(p);
//        Result<Pessoa> pessoas = pessoaMapper.map(results);
//        pessoas.forEach(x -> System.out.println(x));
//            pessoaMapper.delete("333.333.333-33");

            PreparedStatement prepared
                    = session.prepare("UPDATE pessoa SET nome = ? WHERE cpf = ?");
            BoundStatement b = prepared.bind("cassia", "444.444.444-44");
            session.execute(b);
        } finally {
            if (cluster != null) {
                cluster.close();
            }
        }
    }
}
