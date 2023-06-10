package persistencia;

import negocio.Pessoa;
import org.neo4j.driver.AuthTokens;
import org.neo4j.driver.Driver;
import org.neo4j.driver.GraphDatabase;
import org.neo4j.driver.Query;
import org.neo4j.driver.Result;
import org.neo4j.driver.Session;
import org.neo4j.driver.Value;
import static org.neo4j.driver.Values.parameters;

import java.util.ArrayList;
import java.util.List;

public class PessoaDAO {
    
    public boolean inserir(Pessoa p){
        Driver driver = GraphDatabase.driver("bolt://localhost:7687", AuthTokens.basic("neo4j", "password"));
        try (Session session = driver.session()) {
            Query query = new Query("CREATE (p:Pessoa {nome: $nome, cpf:$cpf, email:$email, senha:$senha, nascimento:$nascimento}) return p", parameters("nome", p.getNome(), "cpf", p.getCpf(), "email", p.getEmail(), "senha", p.getSenha(), "nascimento", p.getNascimento()));
            Result result = session.run(query);
            if(result.hasNext()) {
                Value v = result.next().get(0);
                p.setId(v.asNode().id());
                return true;
            }
        }
        return false;
    }
    
    public void deletar(long id) {
        Driver driver = GraphDatabase.driver("bolt://localhost:7687", AuthTokens.basic("neo4j", "password"));
        try (Session session = driver.session()) {
            Query query = new Query("match(p:Pessoa) where ID(p) = $id DETACH DELETE p;", parameters("id", id));
            Result result = session.run(query);
            
        }
    }

    public void deletarTudo() {
        Driver driver = GraphDatabase.driver("bolt://localhost:7687", AuthTokens.basic("neo4j", "password"));
        try (Session session = driver.session()) {
            Query query1 = new Query("match(a)-[r]-> () delete a, r");
            Query query2 = new Query("match(n) DETACH DELETE n");
            session.run(query1);
            session.run(query2);
            
        }
    }

    public Pessoa obter(long id) {
        Driver driver = GraphDatabase.driver("bolt://localhost:7687", AuthTokens.basic("neo4j", "password"));
        Pessoa p = null;
        try (Session session = driver.session()) {
            Query query = new Query("MATCH(p:Pessoa) WHERE ID(p) = $id RETURN p", parameters("id", id));
            Result result = session.run(query);
            if (result.hasNext()){
                Value v = result.next().get(0);
                p = new Pessoa();
                p.setId(v.asNode().id());
                p.setNome(v.get("nome").asString());
                p.setCpf(v.get("cpf").asString());
                p.setEmail(v.get("email").asString());
                p.setSenha(v.get("senha").asString());                
                p.setNascimento(v.get("nascimento").asLocalDateTime());
            }
        }
        return p;
    }

    public Pessoa atualizar(Pessoa p) {
        Driver driver = GraphDatabase.driver("bolt://localhost:7687", AuthTokens.basic("neo4j", "password"));
        try (Session session = driver.session()) {
            Query query = new Query("MATCH(p:Pessoa) WHERE ID(p) = $id set p.nome = $nome, p.cpf = $cpf, p.email = $email, p.senha = $senha, p.nascimento = $nascimento RETURN p", 
                                    parameters("id", p.getId(), "nome", p.getNome(), "cpf", p.getCpf(), "email", p.getEmail(), "senha", p.getSenha(), "nascimento", p.getNascimento()));
            Result result = session.run(query);

            if (result.hasNext()){
                Value v = result.next().get(0);
                p = new Pessoa();
                p.setId(v.asNode().id());
                p.setNome(v.get("nome").asString());
                p.setCpf(v.get("cpf").asString());
                p.setEmail(v.get("email").asString());
                p.setSenha(v.get("senha").asString());                
                p.setNascimento(v.get("nascimento").asLocalDateTime());
            }
        }
        return p;
    }

    public void solicitarAmizade(long id1, long id2){
        Driver driver = GraphDatabase.driver("bolt://localhost:7687", AuthTokens.basic("neo4j", "password"));
        try (Session session = driver.session()) {
            Query querySolicitacaoAmizade = new Query("MATCH(p2:Pessoa)-[rel:SOLICITA_AMIZADE]->(p1:Pessoa) WHERE ID(p1) = $p1 AND ID(p2) = $p2 RETURN rel", 
                                    parameters("p1", id1, "p2", id2));
            Result resultSolicitacaoAmizade = session.run(querySolicitacaoAmizade);
            
            if(resultSolicitacaoAmizade.hasNext()){
                cancelarSolicitacaoAmizade(id2, id1);
                criarAmizade(id1, id2);
            }else{
                 Query query = new Query("MATCH(p1:Pessoa), (p2:Pessoa) WHERE ID(p1) = $p1 AND ID(p2) = $p2 CREATE (p1)-[:SOLICITA_AMIZADE]->(p2)", 
                                    parameters("p1", id1, "p2", id2));
                Result result = session.run(query);
            }
           
        }
    }

    public void cancelarSolicitacaoAmizade(long id1, long id2){
        Driver driver = GraphDatabase.driver("bolt://localhost:7687", AuthTokens.basic("neo4j", "password"));
        try (Session session = driver.session()) {
            Query query = new Query("MATCH(p1:Pessoa)-[a:SOLICITA_AMIZADE]->(p2:Pessoa) WHERE ID(p1) = $p1 AND ID(p2) = $p2 DELETE a", 
                                    parameters("p1", id1, "p2", id2));
            Result result = session.run(query);
        }
    }

    public void criarAmizade(long id1, long id2){
        Driver driver = GraphDatabase.driver("bolt://localhost:7687", AuthTokens.basic("neo4j", "password"));
        try (Session session = driver.session()) {
            Query query = new Query("MATCH(p1:Pessoa), (p2:Pessoa) WHERE ID(p1) = $p1 AND ID(p2) = $p2 CREATE (p1)-[:AMIGO]->(p2)", 
                                    parameters("p1", id1, "p2", id2));
            Result result = session.run(query);
        }
    }

    public void desfazerAmizade(long id1, long id2){
        Driver driver = GraphDatabase.driver("bolt://localhost:7687", AuthTokens.basic("neo4j", "password"));
        try (Session session = driver.session()) {
            Query query = new Query("MATCH(p1:Pessoa)-[a:AMIGO]-(p2:Pessoa) WHERE ID(p1) = $p1 AND ID(p2) = $p2 DELETE a", 
                                    parameters("p1", id1, "p2", id2));
            Result result = session.run(query);
        }
    }

    public ArrayList<Pessoa> obterAmigos(long id){
        Driver driver = GraphDatabase.driver("bolt://localhost:7687", AuthTokens.basic("neo4j", "password"));
        Pessoa p = null;
        ArrayList<Pessoa> amigos = new ArrayList<Pessoa>();
        try (Session session = driver.session()) {
            Query query = new Query("MATCH(p1:Pessoa)-[rel:AMIGO]-(amigo:Pessoa) WHERE ID(p1) = $id RETURN amigo", parameters("id", id));
            Result result = session.run(query);
            while (result.hasNext()){
                Value v = result.next().get(0);
                p = new Pessoa();
                p.setId(v.asNode().id());
                p.setNome(v.get("nome").asString());
                p.setCpf(v.get("cpf").asString());
                p.setEmail(v.get("email").asString());
                p.setSenha(v.get("senha").asString());                
                p.setNascimento(v.get("nascimento").asLocalDateTime());
                amigos.add(p);
            }
        }

        return amigos;
    }
}
