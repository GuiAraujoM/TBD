package negocio;

import java.util.ArrayList;

public class Feed {
    private String name;
    private String url;
    private String category;
    private ArrayList<Article> articles;

    public Feed(){
        articles = new ArrayList<Article>();
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

    @Override
    public String toString() {
        return "Feed [name=" + name + ", url=" + url + ", category=" + category + "]";
    }
    
}
