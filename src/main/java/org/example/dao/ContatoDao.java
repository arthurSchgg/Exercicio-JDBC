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
                SELECT id,
                nome,
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
                contato = new Contato(rs.getInt("id"), rs.getString("nome"), rs.getString("numero"));
                listaContatos.append(contato).append("\n");
            }
            return listaContatos;
        }
    }

    public StringBuilder listarPorVariosIDs(List<Integer> listaIDs) throws SQLException {
        StringBuilder placeholders = new StringBuilder();
        for (int i = 0; i < listaIDs.size(); i++) {
            placeholders.append("?");
            if (i < listaIDs.size() - 1) {
                placeholders.append(", ");
            }
        }
        String command = """
                    SELECT id,
                    nome,
                    numero
                    FROM contatos
                    WHERE
                    id IN (%s)
                """.formatted(placeholders); /*
                                              * .formatted(placeholders) é para os ? que no caso são os IDs que o usuário
                                              * irá inserir, onde o (placeholders) é os "inputs" do usuário.
                                              */

        try (Connection conn = ConnectionFactory.conectar();
                PreparedStatement stmt = conn.prepareStatement(command)) {
            int i = 1;
            for (int id : listaIDs) {
                stmt.setInt(i, id);
                i++;
            }

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
}