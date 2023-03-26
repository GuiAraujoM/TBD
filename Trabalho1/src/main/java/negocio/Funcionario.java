/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import persistencia.*;

@Entity
@Table(name = "funcionario")
public class Funcionario implements Serializable, EntidadeGenerica {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private int id;
    @Column
    private String cpf;
    @Column
    private String nome;
    @Column
    private String email;    
    @Column
    private String senha;
    
    @OneToMany(mappedBy = "funcionario")
    private List<Atendimento> atendimentos = new ArrayList<Atendimento>();

    @ManyToOne
    @JoinColumn(name = "setor_id")
    private Setor setor;

    public Funcionario() {
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
    
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public List<Atendimento> getAtendimentos() {
        return atendimentos;
    }

    public void setAtendimentos(List<Atendimento> atendimentos) {
        this.atendimentos = atendimentos;
    }

    public Setor getSetor() {
        return setor;
    }

    public void setSetor(Setor setor) {
        this.setor = setor;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public static Funcionario criarFuncionario(){
        Scanner sc = new Scanner(System.in);        
        SetorDAO setorDAO = new SetorDAO();
        String stringInput;

        Funcionario func = new Funcionario();

        System.out.println("Insira o nome do funcionário");
        stringInput = sc.nextLine();
        func.setNome(stringInput);

        System.out.println("Insira o email do funcionário");
        stringInput = sc.nextLine();
        func.setEmail(stringInput);

        System.out.println("Insira a senha do funcionário");
        stringInput = sc.nextLine();
        func.setSenha(stringInput);

        System.out.println("Insira o cpf do funcionário");
        stringInput = sc.nextLine();
        func.setCpf(stringInput);

        System.out.println("Insira o nome do setor");
        stringInput = sc.nextLine();
        
        if (setorDAO.consultarPorNome(stringInput) != null) {
            func.setSetor(setorDAO.consultarPorNome(stringInput));
        } else {
            System.out.println("Setor não encontrado, funcionário será criado/atualizado sem setor.");
        }

        return func;
    }


    @Override
    public String toString() {
        return "Funcionario [id=" + id + ", cpf=" + cpf + ", nome=" + nome + ", email=" + email + ", senha=" + senha
                + ", setor=" + setor + "]";
    }
    
}
