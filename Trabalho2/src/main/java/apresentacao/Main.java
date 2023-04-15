/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */
package apresentacao;

import com.google.gson.internal.sql.SqlTypesSupport;
import com.sun.syndication.io.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import negocio.*;
import persistencia.FeedDAO;

public class Main {
    private static Scanner scanner = new Scanner(System.in);    
    private static FeedDAO dao = new FeedDAO();

    public static void main(String[] args) throws IllegalArgumentException, FeedException {
        boolean sair = false;
        while (!sair) {
            exibirMenuPrincipal();
            int opcao = scanner.nextInt();
            scanner.nextLine(); 
            switch (opcao) {
                case 1:
                    adicionarInscricao();
                    break;
                case 2:
                    removerInscricao();
                    break;
                case 3:
                    atualizarCategoria();
                    break;
                case 4:
                    listarInscricoes();
                    break;   
                case 5:
                    removerTudo();
                    break;     
                case 6:
                    listarArtigos();
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

    public static void exibirMenuPrincipal(){
        System.out.println("MENU PRINCIPAL");
        System.out.println("-------------");
        System.out.println("1. Adicionar uma inscrição");
        System.out.println("2. Remover uma inscrição");
        System.out.println("3. Alterar categoria de inscrição");
        System.out.println("4. Listar inscrições");
        System.out.println("5. Remover todas inscrições");
        System.out.println("6. Listar artigos de inscrições");
        System.out.println("7. Sair");
        System.out.println();
        System.out.print("Escolha uma opção: ");        
    }

    private static void adicionarInscricao() {
        System.out.println("ADICIONAR INSCRIÇÃO");
        System.out.println("-------------------");

        Feed f = new Feed();

        System.out.print("Digite o url do feed que deseja inscrever-se: ");     
        String url = scanner.nextLine();

        if(url.isBlank()){
            System.out.println();
            System.err.println("<< URL INVÁLIDO >>");
            System.out.println();

            return;
        }

        if(dao.obter(url) != null){
            System.out.println();
            System.err.println("<< VOCÊ JÁ ESTÁ INSCRITO NESSE FEED >>");
            System.out.println();
            
            return;
        }
        
        f.setUrl(url);

        System.out.print("Digite o nome do feed: ");
        String name = scanner.nextLine();
        f.setName(name);

        System.out.print("Digite a categoria do feed: ");
        String category = scanner.nextLine();
        f.setCategory(category);

        dao.adicionar(f);

        System.out.println();
        System.out.println("<< INSCRIÇÃO ADICIONADA COM SUCESSO! >>");
        System.out.println();
    }

    private static void removerInscricao() {
        System.out.println("REMOVER INSCRIÇÃO");
        System.out.println("-----------------");

        System.out.print("Digite o url do feed deseja remover: ");
        String url = scanner.nextLine();

        if(dao.obter(url) == null){
            System.out.println();
            System.out.println("<< INSCRIÇÃO NÃO EXISTE >>");
            System.out.println();
        }

        dao.remover(url);        

        System.out.println();
        System.out.println("<< INSCRIÇÃO REMOVIDA COM SUCESSO >>");
        System.out.println();
    }

    private static void listarInscricoes() {
        System.out.println("LISTANDO INSCRIÇÕES");
        System.out.println("-----------------");

        List<Feed> fList = dao.listar();
        if(fList.isEmpty()){
            System.out.println("<< VAZIO >>");
        }else{
            fList.forEach(System.out::println);
        }

        System.out.println();
    }

    private static void removerTudo() {
        System.out.println("REMOVER TODAS INSCRIÇÕES");
        System.out.println("-----------------");

        dao.removerTudo();

        System.out.println("<< INSCRIÇÕES REMOVIDAS >>");
        System.out.println();
    }

    private static void atualizarCategoria() {
        System.out.println("ATUALIZAR CATEGORIA DO FEED");
        System.out.println("-----------------");

        System.out.print("Digite a URL do feed que deseja atualizar a categoria: ");
        String url = scanner.nextLine();

        Feed f = dao.obter(url);

        if(f == null){
            System.out.println();
            System.out.println("<< FEED NÃO ENCONTRADO >>");
            System.out.println();
            return;
        }        

        System.out.print("Digite a nova categoria do feed: ");
        String category = scanner.nextLine();
        f.setCategory(category);
        
        dao.atualizar(f);

        System.out.println();
        System.out.println("<< FEED ATUALIZADO >>");
        System.out.println();
    }

    private static void listarArtigos() throws IllegalArgumentException, FeedException {
        System.out.println("LISTAR ARTIGOS");
        System.out.println("-----------------");

        boolean permitirProximo = true;

        List<String> urlList = new ArrayList<String>();
        List<Feed> fList = dao.listar();

        if(fList.isEmpty()) {
            System.out.println();
            System.out.println("<< NENHUM FEED CADASTRADO >>");
            System.out.println();
            return;
        }

        fList.forEach(f -> urlList.add(f.getUrl()));

        int pag = 1;
        int tam = 2;
        boolean sair = false;

        System.out.println("<< LISTANDO PÁG. " + pag + " >>");
        List<Article> aList = Article.buscarArtigos(urlList, pag, tam);

        if (!(aList.size() > 0)) {
            System.out.println();
            System.out.println("<< NENHUM ARTIGO >>");
            System.out.println();
            return;
        }

        for (Article a : aList) {
            System.out.println("");
            System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
            System.out.println(a);
            System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
            System.out.println("");
        }                

        if(aList.size() < tam){
            permitirProximo = false;
        }

        while (!sair) {
            System.out.println("LISTAR ARTIGOS");
            System.out.println("-----------------");
            System.out.println("1 - AVANÇAR PÁGINA");
            System.out.println("2 - RETORNAR PÁGINA");
            System.out.println("3 - Menu");

            System.out.print("Opção: ");
            String opc = scanner.nextLine();

            if(opc.equals("1")){
                if(permitirProximo == false){
                    System.out.println("<< PRÓXIMA PÁG ESTÁ VÁZIA >>");
                    break;
                }else{
                    pag ++;
                }                
            }else if(opc.equals("2")){
                pag --;
            }else{
                return;
            }

            System.out.println("<< LISTANDO PÁG. " + pag + " >>");
            aList = Article.buscarArtigos(urlList, pag, tam);

            if (!(aList.size() > 0)) {
                System.out.println();
                System.out.println("<< NENHUM ARTIGO NESSA PÁGINA >>");
                System.out.println();
                break;
            }

            for (Article a : aList) {
                System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
                System.out.println(a);
                System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
                System.out.println("");
            }
            if (aList.size() < tam) {
                permitirProximo = false;
            }            
        }
        
        
    }
}
