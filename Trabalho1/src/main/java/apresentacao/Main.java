/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */

package apresentacao;

import java.util.Scanner;

import negocio.*;
import persistencia.*;

public class Main {  

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        while(true){
            System.out.println("Insira a operação que deseja realizar");
            System.out.println("(1 - Criar, 2 - Editar, 3 - Apagar, 4 - Consultar)");
            int operacao = sc.nextInt();

            System.out.println("Insira a entidade da operação que deseja realizar");
            System.out.println("(1 - Funcionario, 2 - Paciente, 3 - Atendimento, 4 - Setor)");
            int entidade = sc.nextInt();

            menu(operacao, entidade);
        }

    }

    public static void menu(int operacao, int entidade){
        Scanner sc = new Scanner(System.in);
        String stringInput;

        FuncionarioDAO funcDAO = new FuncionarioDAO();
        PacienteDAO pacDAO = new PacienteDAO();
        AtendimentoDAO atendDAO = new AtendimentoDAO();
        SetorDAO setorDAO = new SetorDAO();
        
        if(operacao == 1){ //Criar
            if(entidade == 1){ //Criar Funcionario
                
                Funcionario func = Funcionario.criarFuncionario();               

                try {
                    func = funcDAO.salvar(func);
                    System.out.println("Funcionario criado");
                    System.out.println(func.toString());
                } catch (Exception e) {
                    throw e;
                }

            }else if (entidade == 2) { //Criar Paciente

                Paciente pac = Paciente.criarPaciente();

                try {
                    pac = pacDAO.salvar(pac);                    
                    System.out.println("Paciente criado");
                    System.out.println(pac.toString());
                } catch (Exception e) {
                    throw e;
                }

            }else if (entidade == 3) { //Criar Atendimento

                Atendimento atend = Atendimento.criarAtendimento();

                try {
                    atend = atendDAO.salvar(atend);
                    System.out.println("Atendimento criado");
                    System.out.println(atend.toString());
                } catch (Exception e) {
                    throw e;
                }

            }else if(entidade == 4){ //Criar Setor
                
                Setor setor = Setor.criaSetor();                

                try {
                    setor = setorDAO.salvar(setor);
                    System.out.println("Setor criado");
                    System.out.println(setor.toString());
                } catch (Exception e) {
                    throw e;
                }
            }
        }else if(operacao == 2){ //Editar
            if(entidade == 1){      

                System.out.println("Insira o cpf do funcionário que deseja editar");
                stringInput = sc.nextLine();
                Funcionario func = funcDAO.consultarPorCpf(stringInput);

                if(func != null){
                    int idFunc = func.getId();
                    System.out.println("Funcionário encontrado!");
                    System.out.println(func.toString());
                    
                    func = Funcionario.criarFuncionario();
                    func.setId(idFunc);                    
                    funcDAO.salvar(func);

                    System.out.println("Funcionario atualizado");
                    System.out.println(func.toString());
                }else{
                    System.out.println("Funcionário não encontrado, tente novamente");
                } 

            }else if(entidade == 2){ //Editar Paciente

                System.out.println("Insira o cpf do paciente que deseja editar");
                stringInput = sc.nextLine();
                Paciente pac = pacDAO.consultarPorCpf(stringInput);

                if (pac != null) {
                    int idPac = pac.getId();
                    System.out.println("Paciente encontrado!");
                    System.out.println(pac.toString());

                    pac = Paciente.criarPaciente();
                    pac.setId(idPac);
                    pacDAO.salvar(pac);

                    System.out.println("Paciente atualizado");
                    System.out.println(pac.toString());
                } else {
                    System.out.println("Paciente não encontrado, tente novamente");
                }

            }else if(entidade == 3){ //Editar Atendimento

                System.out.println("Insira o id do atendimento que deseja editar");
                stringInput = sc.nextLine();
                Atendimento atend = atendDAO.consultarPorId(Atendimento.class, Integer.parseInt(stringInput));

                if (atend != null) {
                    int idAtend = atend.getId();
                    System.out.println("Atendimento encontrado!");
                    System.out.println(atend.toString());

                    atend = Atendimento.criarAtendimento();
                    atend.setId(idAtend);
                    atendDAO.salvar(atend);

                    System.out.println("Atendimento atualizado");
                    System.out.println(atend.toString());
                } else {
                    System.out.println("Atendimento não encontrado, tente novamente");
                }

            }else if(entidade == 4){ //Editar Setor

                System.out.println("Insira o nome do setor que deseja editar");
                stringInput = sc.nextLine();
                Setor setor = setorDAO.consultarPorNome(stringInput);

                if (setor != null) {
                    int idSetor = setor.getId();
                    System.out.println("Setor encontrado!");
                    System.out.println(setor.toString());

                    setor = Setor.criaSetor();
                    setor.setId(idSetor);
                    setorDAO.salvar(setor);

                    System.out.println("Setor atualizado");
                    System.out.println(setor.toString());
                } else {
                    System.out.println("Setor não encontrado, tente novamente");
                }

            }
        }else if(operacao == 3){ //Deletar
            if(entidade == 1){ //Deletar Funcionário

                System.out.println("Insira o cpf do funcionário que deseja deletar");
                stringInput = sc.nextLine();                
                try{
                    funcDAO.remover(Funcionario.class, funcDAO.consultarPorCpf(stringInput).getId());
                    System.out.println("Deletado");
                }catch(Exception e){
                    System.out.println("Erro ao deletar: " + e.getMessage());
                }
                    
            }else if(entidade == 2){ //Deleter Paciente

                System.out.println("Insira o cpf do paciente que deseja deletar");
                stringInput = sc.nextLine();
                try {
                    pacDAO.remover(Paciente.class, pacDAO.consultarPorCpf(stringInput).getId());
                    System.out.println("Deletado");
                } catch (Exception e) {
                    System.out.println("Erro ao deletar: " + e.getMessage());
                }

            }else if(entidade == 3){ //Deleter Atendimento

                System.out.println("Insira o id do atendimento que deseja deletar");
                stringInput = sc.nextLine();
                try {
                    atendDAO.remover(Atendimento.class, atendDAO.consultarPorId(Atendimento.class, Integer.parseInt(stringInput)).getId());
                    System.out.println("Deletado");
                } catch (Exception e) {
                    System.out.println("Erro ao deletar: " + e.getMessage());
                }

            }else if(entidade == 4){ //Deleter Setor

                System.out.println("Insira o nome do setor que deseja deletar");
                stringInput = sc.nextLine();
                try {
                    setorDAO.remover(Setor.class, setorDAO.consultarPorNome(stringInput).getId());
                    System.out.println("Deletado");
                } catch (Exception e) {
                    System.out.println("Erro ao deletar: " + e.getMessage());
                }

            }
        }else if(operacao == 4){ //Consultar
            if(entidade == 1){ //Consultar Funcionário

                System.out.println("Insira o cpf do funcionário que deseja consultar");
                stringInput = sc.nextLine();
                System.out.println(funcDAO.consultarPorCpf(stringInput).toString());

            }else if(entidade == 2){ //Consultar Paciente

                System.out.println("Insira o cpf do paciente que deseja consultar");
                stringInput = sc.nextLine();
                System.out.println(pacDAO.consultarPorCpf(stringInput).toString());

            }else if(entidade == 3){ //Consultar Atendimento

                System.out.println("Insira o id do atendimento que deseja consultar");
                stringInput = sc.nextLine();                
                System.out.println(atendDAO.consultarPorId(Atendimento.class, Integer.parseInt(stringInput)).toString());

            }else if(entidade == 4){ //Consultar Setor

                System.out.println("Insira o nome do setor que deseja consultar");
                stringInput = sc.nextLine();
                System.out.println(setorDAO.consultarPorNome(stringInput).toString());
            }
        }
    }
}
