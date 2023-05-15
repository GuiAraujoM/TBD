package persistencia;

import static com.mongodb.client.model.Filters.eq;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

import javax.swing.text.html.HTMLDocument.Iterator;

import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.bson.types.ObjectId;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import static com.mongodb.client.model.Updates.set;

import negocio.*;

public class BaseMongoDAO {    
    protected ConnectionString connectionString;
    protected CodecRegistry pojoCodecRegistry;
    protected CodecRegistry codecRegistry;
    protected MongoClientSettings clientSettings;
    protected MongoDatabase db;
    protected MongoClient mongoClient;
    protected MongoCollection<Contato> contatoCollection;

    public BaseMongoDAO() {
        this.connectionString = new ConnectionString("mongodb://localhost:27017");
        this.pojoCodecRegistry = fromProviders(PojoCodecProvider.builder().automatic(true).build());
        this.codecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(), pojoCodecRegistry);
        this.clientSettings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .codecRegistry(codecRegistry)
                .build();
        
        this.mongoClient = MongoClients.create(clientSettings);
        this.db = mongoClient.getDatabase("agenda");
        this.contatoCollection = db.getCollection("contato", Contato.class);
    }
}
