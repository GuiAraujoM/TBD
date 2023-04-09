package negocio;

import java.util.ArrayList;
import java.util.UUID;

public class Feed {
    private UUID id;
    private String name;
    private String url;
    private String category;
    private ArrayList<Article> articles;

    public Feed(){
        this.id = UUID.randomUUID();
    }

    public UUID getId() {
        return id;
    }
    public void setId(UUID id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public String getCategory() {
        return category;
    }
    public void setCategory(String category) {
        this.category = category;
    }
    public ArrayList<Article> getArticles() {
        return articles;
    }
    public void setArticles(ArrayList<Article> articles) {
        this.articles = articles;
    }


}
