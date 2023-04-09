/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */
package apresentacao;

import com.sun.syndication.feed.synd.*;
import com.sun.syndication.io.*;

import negocio.Article;
import persistencia.ArticleDAO;

import java.util.Iterator;
import java.util.Scanner;
import java.util.UUID;

import org.xml.sax.InputSource;

public class Main {

    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws IllegalArgumentException, FeedException {        

        /*String url = "https://ifrs.edu.br/rss";
        // URL feedSource = new URL(url);
        SyndFeedInput input = new SyndFeedInput();
        // SyndFeed feed = input.build(new XmlReader(feedSource));
        SyndFeed feed = input.build(new InputSource(url));
        Iterator itr = feed.getEntries().iterator();
        while (itr.hasNext()) {
            SyndEntry syndEntry = (SyndEntry) itr.next();
            
            System.out.println(syndEntry.getAuthor());
            // Dentro de syndEntry tem cada article
        }*/

        Article artigo = new Article();
        artigo.setBody("Artigo 1");
        artigo.setImgUrl("google.com/");
        artigo.setTitle("Titulo");

        ArticleDAO dao = new ArticleDAO();
        dao.adicionar(artigo);

        dao.listar().forEach(t -> dao.remover(t.getId()));

        /*boolean sair = false;
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
                    listarInscricoes();
                    break;
                case 4:
                    sair = true;
                    System.out.println("Finalizando!");
                    break;
                default:
                    System.out.println("Opção inválida!");
                    break;
            }
        }*/
    }

    private static void exibirMenuPrincipal() {
        System.out.println("MENU PRINCIPAL");
        System.out.println("-------------");
        System.out.println("1. Adicionar uma inscrição");
        System.out.println("2. Remover uma inscrição");
        System.out.println("3. Listar inscrições");
        System.out.println("4. Sair");
        System.out.println();
        System.out.print("Escolha uma opção: ");
    }

    private static void adicionarInscricao() {
        System.out.println("ADICIONAR INSCRIÇÃO");
        System.out.println("-------------------");

        System.out.print("Digite o url do feed que deseja inscrever-se: ");
        String url = scanner.nextLine();

        // add lógica

        System.out.println("A inscrição foi adicionada com sucesso!");
        System.out.println();
    }

    private static void removerInscricao() {
        System.out.println("REMOVER INSCRIÇÃO");
        System.out.println("-----------------");

        System.out.print("Digite o url do feed deseja remover: ");
        int url = scanner.nextInt();
        scanner.nextLine(); 

        // add lógica

        System.out.println("A inscrição foi removida com sucesso!");
        System.out.println();
    }

    private static void listarInscricoes() {
        System.out.println("LISTAR INSCRIÇÕES");
        System.out.println("-----------------");

        //add lógica
    }
}
