package persistencia;

import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.LocalDate;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Session;
import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.MappingManager;
import com.datastax.driver.mapping.Result;

import java.util.ArrayList;
import java.util.List;

import negocio.Pessoa;

public class PessoaDAO {
    public static void criar(Pessoa p) {
        Cluster cluster = null;
        try {
            cluster = Cluster.builder().addContactPoint("localhost").build();
            Session session = cluster.connect("cassandra");
            Mapper<Pessoa> pessoaMapper = new MappingManager(session).mapper(Pessoa.class);
            pessoaMapper.save(p);
        } finally {
            if (cluster != null) {
                cluster.close();
            }
        }
    }

    public static Pessoa obterPorCpf(String cpf) {
        Cluster cluster = null;
        try {
            cluster = Cluster.builder().addContactPoint("localhost").build();
            Session session = cluster.connect("cassandra");
            Mapper<Pessoa> pessoaMapper = new MappingManager(session).mapper(Pessoa.class);
            return pessoaMapper.get(cpf);
        } finally {
            if (cluster != null) {
                cluster.close();
            }
        }
    }

    public static void deletar(String cpf) {
        Cluster cluster = null;
        try {
            cluster = Cluster.builder().addContactPoint("localhost").build();
            Session session = cluster.connect("cassandra");
            Mapper<Pessoa> pessoaMapper = new MappingManager(session).mapper(Pessoa.class);
            pessoaMapper.delete(cpf);
        } finally {
            if (cluster != null) {
                cluster.close();
            }
        }
    }

    public static void atualizar(Pessoa p) {
        Cluster cluster = null;
        try {
            cluster = Cluster.builder().addContactPoint("localhost").build();
            Session session = cluster.connect("cassandra");
            PreparedStatement prepared
                    = session.prepare("UPDATE pessoa SET nome = ?, nascimento = ?, gostos = ? WHERE cpf = ?;");
            BoundStatement b = prepared.bind(p.getNome(), p.getNascimento(), p.getGostos(), p.getCpf());
            session.execute(b);
        } finally {
            if (cluster != null) {
                cluster.close();
            }
        }
    }

    public static void deletarTudo() {
        Cluster cluster = null;
        try {
            cluster = Cluster.builder().addContactPoint("localhost").build();
            Session session = cluster.connect("cassandra");
            PreparedStatement prepared
                    = session.prepare("TRUNCATE pessoa;");
            BoundStatement b = prepared.bind();            
            session.execute(b);
            
        } finally {
            if (cluster != null) {
                cluster.close();
            }
        }
    }
}
