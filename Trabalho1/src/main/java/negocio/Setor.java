package negocio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "setor")
public class Setor implements Serializable, EntidadeGenerica{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private int id;
    @Column
    private String nome;

    @OneToMany(mappedBy = "setor")
    private List<Funcionario> funcionarios = new ArrayList<Funcionario>();

    public Setor(){
        
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<Funcionario> getfuncionarios() {
        return funcionarios;
    }

    public void setfuncionarios(List<Funcionario> funcionarios) {
        this.funcionarios = funcionarios;
    }

    public static Setor criaSetor(){
        Scanner sc = new Scanner(System.in);
        String stringInput;


        Setor setor = new Setor();
        System.out.println("Insira o nome do setor");
        stringInput = sc.nextLine();
        setor.setNome(stringInput);

        return setor;
    }

    @Override
    public String toString() {
        return "Setor [id=" + id + ", nome=" + nome + ", funcionarios=" + funcionarios + "]";
    } 
    
}
