package negocio;

import java.util.ArrayList;
import org.bson.types.ObjectId;

public class Contato {
    private ObjectId id;
    private String nome;
    private int idade;
    private Endereco endereco;
    private ArrayList<Telefone> telefones;
    

    public Contato() {
        this.endereco = new Endereco();
        this.telefones = new ArrayList<Telefone>();
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(final ObjectId id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public ArrayList<Telefone> getTelefones() {
        return telefones;
    }

    public void setTelefones(ArrayList<Telefone> telefones) {
        this.telefones = telefones;
    }

    public String toStringSimples(){
        return "[Id= " + id +" Nome=" + nome +"]";
    }

    @Override
    public String toString() {
        String telefones = "";

        for (Telefone telefone : this.telefones) {
            telefones += telefone.getNumero();
        }        

        return "[Id=" + id + ", nome=" + nome + ", idade=" + idade + ", endereco=" + endereco + " telefones= :telefones ]".replace(":telefones", telefones);
    }    
}