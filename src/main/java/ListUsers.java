import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.stream.Stream;

public class ListUsers {
    static void listUsers(Connection connection) throws SQLException {
        String listarUsuariosSQL = "SELECT * FROM usuarios";
        PreparedStatement listarUsuariosStatement = connection.prepareStatement(listarUsuariosSQL);
        ResultSet resultSet = listarUsuariosStatement.executeQuery();

        Stream.generate(() -> "".repeat(40)).limit(2).forEach(System.out::println);
        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String nome = resultSet.getString("nome");
            System.out.println("ID: " + id + ", Usuário: " + nome);
            Stream.generate(() -> "-".repeat(40)).limit(1).forEach(System.out::println);
        }
        resultSet.close();
        listarUsuariosStatement.close();
    }
}
