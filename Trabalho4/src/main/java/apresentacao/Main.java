package apresentacao;

import negocio.Pessoa;

import java.time.LocalDateTime;

import persistencia.PessoaDAO;

public class Main {

    public static void main(String[] args) {
        PessoaDAO dao = new PessoaDAO();
        dao.deletarTudo();

        //Inserindo 3 pessoas (CREATE)
        Pessoa p1 = new Pessoa("Guilherme", "09878987654", "guiaraujo@gmail.com", "123", LocalDateTime.now());
        Pessoa p2 = new Pessoa("Michele", "1234567891", "michelearaujo@gmail.com", "123", LocalDateTime.now());
        Pessoa p3 = new Pessoa("Roselaine", "95782937423", "roselainearaujo@gmail.com", "123", LocalDateTime.now());
        Pessoa p4 = new Pessoa("Juliana", "74192481298", "julianaaraujo@gmail.com", "123", LocalDateTime.now());
        dao.inserir(p1);
        dao.inserir(p2);
        dao.inserir(p3);
        dao.inserir(p4);
        
        //Obtendo pessoa p1 (READ)
        Pessoa pObter = dao.obter(p1.getId());
        System.out.println(pObter != null); // registro obtido

        //Atualizando pessoa p1 (UPDATE)
        p1.setNome("Guilherme Araujo");
        p1.setCpf("123");
        dao.atualizar(p1);
        System.out.println(p1.getCpf().equals("123") && p1.getNome().equals("Guilherme Araujo")); // registro atualizada

        //Deletando pessoa p1 (DELETE)
        dao.deletar(p1.getId()); 
        pObter = dao.obter(p1.getId());
        System.out.println(pObter == null); // registro deletado
            

        //Definindo amizades
        dao.solicitarAmizade(p2.getId(), p3.getId()); //p2 solicita amizade de p3 e p4
        dao.solicitarAmizade(p2.getId(), p4.getId());
        System.out.println(dao.obterAmigos(p2.getId()).isEmpty()); // Nenhum amigo ainda
        
        dao.solicitarAmizade(p3.getId(), p2.getId());                // p3 Solicita (aceita) amizade de p2
        System.out.println(dao.obterAmigos(p2.getId()).size() == 1); // Primeira amizade
        dao.solicitarAmizade(p4.getId(), p2.getId());                // p4 Solicita (aceita) amizade de p2
        System.out.println(dao.obterAmigos(p2.getId()).size() == 2); // Segunda amizade

        //Desfazendo amizades
        dao.desfazerAmizade(p2.getId(), p3.getId());
        System.out.println(dao.obterAmigos(p2.getId()).size() == 1); // Com uma amizade desfeita p2 agora só tem 1 amigo


        //Listagem de amigos de p2
        dao.obterAmigos(p2.getId()).forEach(t -> System.out.println(t));
        }    
}
