package negocio;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import persistencia.FuncionarioDAO;
import persistencia.PacienteDAO;


@Entity
@Table(name = "atendimento")
@NamedQueries({
        @NamedQuery(name = "Atendimento.listar", query = "SELECT a FROM Atendimento a")
})
public class Atendimento implements Serializable, EntidadeGenerica{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private int id;

    private LocalDateTime data_hora;
    private String observacao;

    @ManyToOne
    @JoinColumn(name = "funcionario_id")
    private Funcionario funcionario;

    @ManyToOne
    @JoinColumn(name = "paciente_id")
    private Paciente paciente;

    public Atendimento(){
        
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getData_hora() {
        return data_hora;
    }

    public void setData_hora(LocalDateTime data_hora) {
        this.data_hora = data_hora;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public Funcionario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public static Atendimento criarAtendimento(){
        Scanner sc = new Scanner(System.in);
        FuncionarioDAO funcDAO = new FuncionarioDAO();
        PacienteDAO pacDAO = new PacienteDAO();
        String stringInput;

        Atendimento atend = new Atendimento();
        System.out.println("Insira o cpf do paciente");
        stringInput = sc.nextLine();
        atend.setPaciente(pacDAO.consultarPorCpf(stringInput));

        System.out.println("Insira o cpf do funcionario");
        stringInput = sc.nextLine();
        atend.setFuncionario(funcDAO.consultarPorCpf(stringInput));

        System.out.println("Insira a data/hora do atendimento no formato: dd/MM/yyyy HH:mm");
        stringInput = sc.nextLine();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        LocalDateTime dateTime = LocalDateTime.parse(stringInput, dtf);
        atend.setData_hora(dateTime);

        System.out.println("Insira a observação do atendimento");
        stringInput = sc.nextLine();
        atend.setObservacao(stringInput);
        
        return atend;
    }

    @Override
    public String toString() {
        return "Atendimento [id=" + id + ", data_hora=" + data_hora + ", observacao=" + observacao + ", funcionario="
                + funcionario + ", paciente=" + paciente + "]";
    }   
    
}
