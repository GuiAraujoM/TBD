package persistencia;

import negocio.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import com.google.gson.*;

import negocio.Article;
import redis.clients.jedis.Jedis;

public class ArticleDAO {
    private Conexao conexao;
    private Gson gson;

    public ArticleDAO() {
        this.gson = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeTypeAdapter())
                .create();
    }

    public void atualizar(Article article) {
        this.conexao = new Conexao();
        if (this.conexao.getConexao().exists(article.getId().toString())) {
            this.conexao.getConexao().set(article.getId().toString(), this.gson.toJson(article));
        }
        this.conexao.fechar();

    }

    public void adicionar(Article article) {

        this.conexao = new Conexao();        
        this.conexao.getConexao().set(article.getId().toString(), this.gson.toJson(article));
        this.conexao.fechar();
    }

    public Article obter(String id) {
        this.conexao = new Conexao();
        Article article = this.gson.fromJson(this.conexao.getConexao().get(id), Article.class);
        this.conexao.fechar();
        return article;

    }

    public void remover(UUID id) {
        this.conexao = new Conexao();
        this.conexao.getConexao().del(id.toString());
        this.conexao.fechar();

    }

    public List<Article> listar() {
        this.conexao = new Conexao();
        Set<String> vetLabels = this.conexao.getConexao().keys("*");
        Iterator iterator = vetLabels.iterator();
        List<Article> vetArticle = new ArrayList<>();
        while (iterator.hasNext()) {
            Object id = iterator.next();
            String json = this.conexao.getConexao().get(id.toString());
            Article article = this.gson.fromJson(json, Article.class);
            vetArticle.add(article);

        }
        this.conexao.fechar();
        return vetArticle;
    }
}
