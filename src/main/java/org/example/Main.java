package org.example;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;
import org.example.dao.ContatoDao;
import org.example.model.Contato;

public class Main {
    static Scanner SC = new Scanner(System.in);

    public static void main(String[] args) {
        inicio();
    }

    public static void inicio() {
        int opcao;

        do {
            
        System.out.println("""
                -----Lista de Contatos-----
                1 - Cadastrar contato
                2 - Editar contato
                3 - Listar contato
                4 - Buscar por nome
                5 - Listar por vários IDs
                0 - Sair
                Escolha uma opção:""");
        opcao = SC.nextInt();
        SC.nextLine();

            switch (opcao) {
                case 1: {
                    cadastrarContato();
                    break;
                }
    
                case 2: {
                    editarContato();
                    break;
                }
    
                case 3: {
                    listarContatos();
                    break;
                }
    
                case 4: {
                    buscarContatoPorNome();
                    break;
                }
    
                case 5: {
                    listarPorVariosIDs();
                    break;
                }

                case 0: {
                    System.out.println("Saindo...");
                    break;
                }
    
                default:
                    throw new AssertionError();
            }
        } while(opcao != 0);
    }

    public static void cadastrarContato() {
        System.out.println("Digite o nome do contato: ");
        String nome = SC.nextLine();

        System.out.println("Digite o numero do contato: ");
        String numero = SC.nextLine();

        var contato = new Contato(nome, numero);

        var contatoDao = new ContatoDao();

        try {
            contatoDao.salvar(contato);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void editarContato() {

        var contatoDao = new ContatoDao();

        try {
            System.out.println(contatoDao.listarContatos());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        System.out.print("Digite o id do contato que você deseja editar: ");
        int id = SC.nextInt();
        SC.nextLine();

        System.out.print("Digite o novo nome do contato: ");
        String novoNome = SC.nextLine();

        System.out.print("Digite o novo número do contato: ");
        String novoNumero = SC.nextLine();

        var contatoAtualizado = new Contato(id, novoNome, novoNumero);

        try {
            contatoDao.editarContato(contatoAtualizado);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void listarContatos() {
        var contatoDao = new ContatoDao();

        try {
            System.out.println(contatoDao.listarContatos());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void buscarContatoPorNome() {
        System.out.println("Escolha um nome para buscar: ");
        String nome = SC.nextLine();

        var contatoDao = new ContatoDao();

        try{
            System.out.println(contatoDao.buscarContatoPorNome(nome));
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public static void listarPorVariosIDs(){
        var listaIDs = new ArrayList<Integer>();
        int op;

        do{
            System.out.println("Digite o ID que deseja buscar: ");
            int id = SC.nextInt();

            listaIDs.add(id);

            System.out.println("""
                Deseja continuar buscando IDs? 
                [1] Sim     
                [2] Não
                Escolha uma opção: """);
                op = SC.nextInt();
                
        } while(op == 1);

        var contatoDao = new ContatoDao();

        try{
            System.out.println(contatoDao.listarPorVariosIDs(listaIDs));
        } catch(SQLException e){
            e.printStackTrace();
        }
    }
}