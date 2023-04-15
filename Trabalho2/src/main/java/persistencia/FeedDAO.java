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

    public void atualizar(Feed feed) {
        this.conexao = new Conexao();
        if (this.conexao.getConexao().exists(feed.getUrl().toString())) {
            this.conexao.getConexao().set(feed.getUrl().toString(), this.gson.toJson(feed));
        }
        this.conexao.fechar();

    }

    public void adicionar(Feed feed) {

        this.conexao = new Conexao();
        this.conexao.getConexao().set(feed.getUrl().toString(), this.gson.toJson(feed));
        this.conexao.fechar();
    }

    public Feed obter(String url) {
        this.conexao = new Conexao();
        Feed feed = this.gson.fromJson(this.conexao.getConexao().get(url), Feed.class);
        this.conexao.fechar();
        return feed;

    }

    public void remover(String url) {
        this.conexao = new Conexao();
        if (this.conexao.getConexao().exists(url)) {
            this.conexao.getConexao().del(url);
        }        
        this.conexao.fechar();

    }

    public List<Feed> listar() {
        this.conexao = new Conexao();
        Set<String> vetLabels = this.conexao.getConexao().keys("*");
        Iterator iterator = vetLabels.iterator();
        List<Feed> vetFeed = new ArrayList<>();
        while (iterator.hasNext()) {
            Object url = iterator.next();
            String json = this.conexao.getConexao().get(url.toString());
            Feed feed = this.gson.fromJson(json, Feed.class);
            vetFeed.add(feed);
        }
        this.conexao.fechar();
        return vetFeed;
    }

    public void removerTudo(){
        this.conexao = new Conexao();
        this.conexao.getConexao().flushDB();
        this.conexao.fechar();
    }    
}