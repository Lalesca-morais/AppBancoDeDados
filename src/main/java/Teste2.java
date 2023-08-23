import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Teste2 {
    public static void main(String[] args) {
        try {
            Connection connection = DriverManager.getConnection("jdbc:sqlite:usuarios.db");

            String createTableSQL = "CREATE TABLE IF NOT EXISTS usuarios (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "nome TEXT NOT NULL," +
                    "senha TEXT NOT NULL" +
                    ")";
            PreparedStatement createTableStatement = connection.prepareStatement(createTableSQL);
            createTableStatement.execute();

            Scanner scanner = new Scanner(System.in);

            System.out.print("Digite o nome de usuário: ");
            String nomeUsuario = scanner.nextLine();
            System.out.print("Digite a senha: ");
            String senha = scanner.nextLine();

            String insertSQL = "INSERT INTO usuarios (nome, senha) VALUES (?, ?)";
            PreparedStatement insertStatement = connection.prepareStatement(insertSQL);
            insertStatement.setString(1, nomeUsuario);
            insertStatement.setString(2, senha);
            insertStatement.executeUpdate();

            System.out.println("\nUsuário cadastrado com sucesso!");

            System.out.print("\nDigite o nome de usuário para fazer login: ");
            String nomeUsuarioLogin = scanner.nextLine();
            System.out.print("Digite a senha: ");
            String senhaLogin = scanner.nextLine();

            String loginSQL = "SELECT * FROM usuarios WHERE nome = ? AND senha = ?";
            PreparedStatement loginStatement = connection.prepareStatement(loginSQL);
            loginStatement.setString(1, nomeUsuarioLogin);
            loginStatement.setString(2, senhaLogin);

            ResultSet resultSet = loginStatement.executeQuery();

            if (resultSet.next()) {
                System.out.println("Login bem-sucedido!");
            } else {
                System.out.println("Nome de usuário ou senha incorretos/usuário não encontrado no banco de dados. Tente novamente.");
            }

            resultSet.close();
            loginStatement.close();
            insertStatement.close();
            createTableStatement.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
