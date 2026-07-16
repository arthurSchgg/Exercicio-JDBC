package org.example.dao;

import org.example.db.ConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.example.model.Contato;

public class ContatoDao {
    public void salvar(Contato contato) throws SQLException {

        String command = """
                INSERT INTO contatos
                (nome,numero)
                VALUES
                (?,?)
                """;

        try (Connection conn = ConnectionFactory.conectar();
                PreparedStatement stmt = conn.prepareStatement(command)) {

            stmt.setString(1, contato.getNome());
            stmt.setString(2, contato.getNumero());
            stmt.executeUpdate();
        }
    }

    public void editarContato(Contato contatoAtualizado) throws SQLException {
        String command = """
                UPDATE contatos
                SET
                nome = ?
                , numero = ?
                WHERE id = ?
                """;

        try (Connection conn = ConnectionFactory.conectar();
                PreparedStatement stmt = conn.prepareStatement(command)) {

            stmt.setString(1, contatoAtualizado.getNome());
            stmt.setString(2, contatoAtualizado.getNumero());
            stmt.setInt(3, contatoAtualizado.getId());

            stmt.executeUpdate();
        }
    }

    public StringBuilder listarContatos() throws SQLException {
        String command = """
                SELECT id, nome, numero
                FROM contatos
                """;
        try (Connection conn = ConnectionFactory.conectar();
                PreparedStatement stmt = conn.prepareStatement(command)) {

            ResultSet rs = stmt.executeQuery();

            var listaContatos = new StringBuilder();

            while (rs.next()) {
                int id = rs.getInt("id");
                String nome = rs.getString("nome");
                String numero = rs.getString("numero");

                Contato contato = new Contato(id, nome, numero);
                listaContatos.append(contato).append("\n");
            }
            return listaContatos;
        }
    }

    public StringBuilder buscarContatoPorNome(String nome) throws SQLException {
        String command = """
                SELECT nome,
                numero
                FROM contatos
                WHERE
                nome LIKE ?
                """;

        try (Connection conn = ConnectionFactory.conectar();
                PreparedStatement stmt = conn.prepareStatement(command)) {

            var listaContatos = new StringBuilder();

            stmt.setString(1, "%" + nome + "%");

            ResultSet rs = stmt.executeQuery();

            Contato contato = null;

            while (rs.next()) {
                contato = new Contato(rs.getString("nome"), rs.getString("numero"));
                listaContatos.append(contato).append("\n");
            }
            return listaContatos;
        }
    }

    public String listarPorVariosIDs(List<Integer> listaIDs) throws SQLException {
        var lista
        String  command = """
                SELECT nome,
                numero
                FROM contatos
                WHERE 
                id IN (?, ?)
                """;
        List<
        try(Connection conn = ConnectionFactory.conectar();
            PreparedStatement stmt = conn.prepareStatement(command)){

            }

    }
}