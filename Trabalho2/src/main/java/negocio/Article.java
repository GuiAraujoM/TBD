package negocio;

import com.sun.syndication.feed.synd.*;
import com.sun.syndication.io.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import org.jsoup.Jsoup;
import org.xml.sax.InputSource;

public class Article implements Comparable<Article>{
    private String title;
    private String link;
    private String body;
    private String author;
    private LocalDateTime dateTime;

    public Article(){
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getLink() {
        return link;
    }
    public void setLink(String link) {
        this.link = link;
    }
    public String getBody() {
        return body;
    }
    public void setBody(String body) {
        this.body = body;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public static List<Article> buscarArtigos(List<String> urlList, int pag, int tam) throws IllegalArgumentException, FeedException {
        
        List<Article> aList = new ArrayList<Article>();

        //String url = urlList.get(0);
        for (String url : urlList) {
            SyndFeedInput input = new SyndFeedInput();
            SyndFeed feed = input.build(new InputSource(url));
            Iterator itr = feed.getEntries().iterator();
            
            while (itr.hasNext()) {
                SyndEntry syndEntry = (SyndEntry) itr.next();

                Article a = new Article();
                a.setLink(syndEntry.getLink());
                a.setTitle(syndEntry.getTitle());
                a.setAuthor(syndEntry.getAuthor());

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE MMM d HH:mm:ss zzz yyyy", Locale.ENGLISH);
                LocalDateTime ldt = formatter.parse(syndEntry.getPublishedDate().toString(), LocalDateTime::from);                
                a.setDateTime(ldt);

                List<SyndContent> cList = syndEntry.getContents();

                String body = "";
                for (SyndContent c : cList) {

                    if (c.getType().equals("html")) {                        
                        String plainText = Jsoup.parse(c.getValue()).text();
                        body += plainText;
                    } else {
                        body += c.getValue();
                    }
                }

                a.setBody(body);

                aList.add(a);
            }
        }   
        
        Collections.sort(aList);

        if(pag == 0) {
            pag = 1;
        }
        int primeiroArticle = 2 * (pag-1);
        int ultimoArticle = primeiroArticle + (tam-1);

        List<Article> aRetList = new ArrayList<>();
        for(int i = primeiroArticle; i <= ultimoArticle; i++){
            try {
                if (aList.get(i) != null) aRetList.add(aList.get(i));
            } catch (Exception e) {
                return aRetList;
            }
            
        }

        return aRetList;
    }

    @Override
    public String toString() {
        String str = "\tARTIGO - " + link;
        str += "\n\tTítulo: " + title;
        str += "\n\tAutor: " + author;
        str += "\n\tPúblicado dia: " + dateTime.toString();
        str += "\n\t" + body;

        return str;
    }

    @Override
    public int compareTo(Article aToCompare) {
        if (this.dateTime.isAfter(aToCompare.dateTime)) {
            return -1;
        }

        if(this.dateTime.isBefore(aToCompare.dateTime)){
            return 1;
        }        

        return 0;
    }    
    
}
