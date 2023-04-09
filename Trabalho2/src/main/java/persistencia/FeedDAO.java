package persistencia;

import negocio.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import com.google.gson.*;

import negocio.Feed;

public class FeedDAO {
    private Conexao conexao;
    private Gson gson;

    public FeedDAO() {
        this.gson = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeTypeAdapter())
                .create();
    }

    public void atualizar(Feed article) {
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

    public Feed obter(String id) {
        this.conexao = new Conexao();
        Feed feed = this.gson.fromJson(this.conexao.getConexao().get(id), Feed.class);
        this.conexao.fechar();
        return feed;

    }

    public void remover(UUID id) {
        this.conexao = new Conexao();
        this.conexao.getConexao().del(id.toString());
        this.conexao.fechar();

    }

    public List<Feed> listar() {
        this.conexao = new Conexao();
        Set<String> vetLabels = this.conexao.getConexao().keys("*");
        Iterator iterator = vetLabels.iterator();
        List<Feed> vetFeed = new ArrayList<>();
        while (iterator.hasNext()) {
            Object id = iterator.next();
            String json = this.conexao.getConexao().get(id.toString());
            Feed feed = this.gson.fromJson(json, Feed.class);
            vetFeed.add(feed);

        }
        this.conexao.fechar();
        return vetFeed;
    }
}