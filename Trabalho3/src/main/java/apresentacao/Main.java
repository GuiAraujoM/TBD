package apresentacao;

import java.util.Scanner;

import org.bson.types.ObjectId;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;

import negocio.*;
import persistencia.ContatoDAO;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static ContatoDAO cDao = new ContatoDAO();

    public static void main(String[] args) {        
        boolean sair = false;
        while (!sair) {
            exibirMenuPrincipal();
            
            int opcao = scanner.nextInt();
            scanner.nextLine();
            switch (opcao) {
                case 1:
                    adicionarContato();
                    break;
                case 2:
                    removerContato();
                    break;
                case 3:
                    editarContato();
                    break;
                case 4:
                    listarContatos();
                    break;
                case 5:
                    listarContatosPorCidade();
                    break;
                case 6:
                    listarContatosComPeloMenosUmTelefone();
                    break;
                case 7:
                    sair = true;
                    System.out.println("Finalizando!");
                    break;
                default:
                    System.out.println("Opção inválida!");
                    break;
            }
        }
    }

    public static void exibirMenuPrincipal() {
        System.out.println("MENU PRINCIPAL");
        System.out.println("-------------");
        System.out.println("1. Adicionar um contato");
        System.out.println("2. Remover um contato");
        System.out.println("3. Editar contato");
        System.out.println("4. Listar todos os contatos");
        System.out.println("5. Listar todos os contatos de uma cidade");
        System.out.println("6. Listar todos os contatos com pelo menos 1 telefone");
        System.out.println("7. Sair");
        System.out.println();
        System.out.print("Escolha uma opção: ");
    }

    private static Contato montarContato(){
        Contato c = new Contato();

        System.out.print("Digite o nome: ");
        String nome = scanner.nextLine();
        c.setNome(nome);

        System.out.print("Digite a idade: ");
        String idade = scanner.nextLine();
        c.setIdade(Integer.valueOf(idade));

        System.out.print("Digite a rua: ");
        String rua = scanner.nextLine();        

        System.out.print("Digite o numero: ");
        String numero = scanner.nextLine();

        System.out.print("Digite o cep: ");
        String cep = scanner.nextLine();

        System.out.print("Digite a cidade: ");
        String cidade = scanner.nextLine();

        System.out.print("Digite o estado: ");
        String estado = scanner.nextLine();

        c.setEndereco(new Endereco(rua, Integer.parseInt(numero), cidade, estado, cep));

        while (true) {
            System.out.print("Deseja adicionar um telefone? (S / N): ");
            String adicionarTelefone = scanner.nextLine();

            if("N".equalsIgnoreCase(adicionarTelefone)){
                break;
            }

            System.out.print("Digite o número: ");
            String num = scanner.nextLine();

            c.getTelefones().add(new Telefone(num));
        }

        return c;
    }

    private static void exibirTodosContatos(boolean exibicaoSimples){
        MongoCursor<Contato> lista = cDao.listarTodos();
        if(lista.hasNext()){
            while (lista.hasNext()) {
                if (exibicaoSimples) {
                    System.out.println(lista.next().toStringSimples());
                } else {
                    System.out.println(lista.next());
                }
            }
        }else{
            System.out.println("<< NENHUM CONTATO >>");
        }

        
        System.out.println();
    }

    private static void exibirContatosPorCidade(String cidade) {
        MongoCursor<Contato> lista = cDao.listarPorCidade(cidade);
        if(lista.hasNext()){
            while (lista.hasNext()) {
                System.out.println(lista.next());
            }
        }else{
            System.out.println("<< NENHUM CONTATO >>");
        }
        

        System.out.println();
    }

    private static void adicionarContato() {
        System.out.println("ADICIONAR CONTATO");
        System.out.println("-------------------");

        Contato c = montarContato();
        boolean sucesso = cDao.adicionar(c);

        if(sucesso){
            System.out.println();
            System.out.println("<< CONTATO ADICIONADO COM SUCESSO! >>");
            System.out.println();
        }        
    }

    private static void removerContato() {
        System.out.println("REMOVER CONTATO");
        System.out.println("-------------------");
        
        exibirTodosContatos(true);    
        
        System.out.print("INSIRA O ID QUE DESEJA REMOVER, CONSULTE O ID NA LISTA ACIMA: ");
        String id = scanner.nextLine();
        boolean sucesso = cDao.remover(id);

        if(sucesso){
            System.out.println();
            System.out.println("<< CONTATO REMOVIDO COM SUCESSO! >>");
            System.out.println();
        }
    }

    private static void editarContato() {
        System.out.println("EDITAR CONTATO");
        System.out.println("-------------------");

        exibirTodosContatos(true);

        System.out.print("INSIRA O ID QUE DESEJA EDITAR, CONSULTE O ID NA LISTA ACIMA: ");
        String id = scanner.nextLine();

        Contato c = cDao.obter(new ObjectId(id));

        boolean sair = false;
        while (!sair) {
            menuEdicao();
            int opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1:
                    editarDadosBasicos(c);
                    break;
                case 2:
                    editarEndereco(c);
                    break;
                case 3:
                    adicionarTelefone(c);
                    break;
                case 4:
                    removerTelefone(c);
                    break;
                case 5:   
                    sair = true;             
                    System.out.println("Edição finalizada.");
                default:
                    break;
            }
        }
        
    }

    private static void editarDadosBasicos(Contato c){
        System.out.print("Digite o nome: ");
        String nome = scanner.nextLine();
        c.setNome(nome);

        System.out.print("Digite a idade: ");
        String idade = scanner.nextLine();
        c.setIdade(Integer.valueOf(idade));

        boolean sucesso = cDao.editar(c.getId(), c);
        if (sucesso) {
            System.out.println();
            System.out.println("<< CONTATO EDITADO COM SUCESSO! >>");
            System.out.println();
        }
    }

    private static void editarEndereco(Contato c) {
        System.out.print("Digite a rua: ");
        String rua = scanner.nextLine();

        System.out.print("Digite o numero: ");
        String numero = scanner.nextLine();

        System.out.print("Digite o cep: ");
        String cep = scanner.nextLine();

        System.out.print("Digite a cidade: ");
        String cidade = scanner.nextLine();

        System.out.print("Digite o estado: ");
        String estado = scanner.nextLine();

        c.setEndereco(new Endereco(rua, Integer.parseInt(numero), cidade, estado, cep));

        boolean sucesso = cDao.editar(c.getId(), c);
        if (sucesso) {
            System.out.println();
            System.out.println("<< CONTATO EDITADO COM SUCESSO! >>");
            System.out.println();
        }
    }

    private static void adicionarTelefone(Contato c){
        while (true) {  
            System.out.print("Digite o número: ");
            String num = scanner.nextLine();

            c.getTelefones().add(new Telefone(num));

            System.out.print("Deseja adicionar mais um telefone? (S / N): ");
            String adicionarTelefone = scanner.nextLine();

            if ("N".equalsIgnoreCase(adicionarTelefone)) {
                break;
            }
        }

        boolean sucesso = cDao.editar(c.getId(), c);
        if (sucesso) {
            System.out.println();
            System.out.println("<< CONTATO EDITADO COM SUCESSO! >>");
            System.out.println();
        }
    }

    private static void removerTelefone(Contato c) {
        while (true) {
            System.out.print("Digite o número: ");
            String num = scanner.nextLine();

            c.getTelefones().removeIf(tel -> tel.getNumero().equals(num));

            System.out.print("Deseja remover mais um telefone? (S / N): ");
            String removerTelefone = scanner.nextLine();

            if ("N".equalsIgnoreCase(removerTelefone)) {
                break;
            }
        }

        boolean sucesso = cDao.editar(c.getId(), c);
        if (sucesso) {
            System.out.println();
            System.out.println("<< CONTATO EDITADO COM SUCESSO! >>");
            System.out.println();
        }
    }

    private static void menuEdicao(){
        System.out.println("O que deseja editar desse contato?");
        System.out.println("1. Editar dados básicos (Nome, Idade)");
        System.out.println("2. Editar endereço");
        System.out.println("3. Adicionar um telefone");
        System.out.println("4. Remover um telefone");
        System.out.println("5. Sair da edição");
        System.out.print("Escolha uma opção: ");
    }

    private static void listarContatos() {
        System.out.println("LISTAR CONTATOS");
        System.out.println("-------------------");

        exibirTodosContatos(false);
    }

    private static void listarContatosPorCidade() {
        System.out.println("LISTAR CONTATOS POR CIDADE");
        System.out.println("-------------------");

        System.out.print("INSIRA O NOME DA CIDADE QUE DESEJA CONSULTAR: ");
        String cidade = scanner.nextLine();

        System.out.println("CONTATOS EM " + cidade.toUpperCase()+ ": \n");
        exibirContatosPorCidade(cidade);
    }

    public static void listarContatosComPeloMenosUmTelefone(){
        System.out.println("LISTAR CONTATOS COM PELO MENOS 1 TELEFONE");
        System.out.println("-------------------\n");

        MongoCursor<Contato> i = cDao.listarComPeloMenosUmTelefone();

        if(i.hasNext()){
            while (i.hasNext()) {
                System.out.println(i.next());
            }
            System.out.println();
        }else{
            System.out.println("<< NENHUM CONTATO >>\n");
        }
        
    }
}