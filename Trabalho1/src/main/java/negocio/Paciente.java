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

import persistencia.PacienteDAO;

@Entity
@Table(name = "paciente")
public class Paciente implements Serializable, EntidadeGenerica{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private int id;
    @Column
    private String nome;
    @Column
    private String cpf;

    @OneToMany(mappedBy = "paciente")
    private List<Atendimento> atendimentos = new ArrayList<Atendimento>();

    public Paciente(){

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
    public String getCpf() {
        return cpf;
    }
    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public static Paciente criarPaciente(){
        Paciente pac = new Paciente();
        Scanner sc = new Scanner(System.in);
        String stringInput;

        System.out.println("Insira o nome do paciente");
        stringInput = sc.nextLine();
        pac.setNome(stringInput);

        System.out.println("Insira o cpf do paciente");
        stringInput = sc.nextLine();
        pac.setCpf(stringInput);

        return pac;
    }

    @Override
    public String toString() {
        return "Paciente [id=" + id + ", nome=" + nome + ", cpf=" + cpf + "]";
    } 
    
}
