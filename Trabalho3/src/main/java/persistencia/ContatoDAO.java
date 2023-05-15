package persistencia;

import static com.mongodb.client.model.Filters.eq;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

import javax.swing.text.html.HTMLDocument.Iterator;

import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.InsertOneResult;
import com.mongodb.client.result.UpdateResult;

import static com.mongodb.client.model.Updates.set;

import negocio.*;

public class ContatoDAO extends BaseMongoDAO{
    public ContatoDAO(){
    }

    public Contato obter(ObjectId id){
        Bson filter = Filters.eq("_id", id);
        Contato c = contatoCollection.find(filter).first();
        return c;
    }

    public boolean adicionar(Contato c){       
        try {
            contatoCollection.insertOne(c);

            return false;
        } catch (Exception e) {
            System.err.println("ERRO AO ADICIONAR");
            System.err.println(e);

            return false;
        }
    }

    public boolean remover(String id) {
        try {
            Bson filter = Filters.eq("_id", new ObjectId(id));
            contatoCollection.deleteOne(filter);
            
            return true;
        } catch (Exception e) {
            System.err.println("ERRO AO REMOVER");
            System.err.println(e);
            
            return false;
        }
    }

    public boolean editar(ObjectId id, Contato contato) {
        try {
            Bson filter = Filters.eq("_id", id);
            contatoCollection.replaceOne(filter, contato);
            
            return true;
        } catch (Exception e) {
            System.err.println("ERRO AO EDITAR");
            System.err.println(e);

            return false;
        }
    }

    // public void editarCamposEspecificos(Contato c) {
    //     Bson filter = Filters.eq("_id", c.getId());
    //     Bson update = Updates.combine(
    //         Updates.set("idade", c.getIdade()),
    //         Updates.set("nome", c.getNome())
    //     );
        
    //     contatoCollection.updateOne(filter, update);
    // }

    public MongoCursor<Contato> listarTodos(){    
        return contatoCollection.find().iterator();
    }

    public MongoCursor<Contato> listarPorCidade(String cidade){
        return contatoCollection.find(eq("endereco.cidade", cidade)).iterator();
    }

    public MongoCursor<Contato> listarComPeloMenosUmTelefone() {
        return contatoCollection.find(eq("telefones.0", new Document("$exists", true))).iterator();
    }
}
