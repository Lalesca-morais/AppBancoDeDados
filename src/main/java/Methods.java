import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
public class Methods {
    public void methods(){
        try {
            Scanner scanner = new Scanner(System.in);
            Connection connection = DriverManager.getConnection("jdbc:sqlite:usuarios.db");

            String createTableSQL = "CREATE TABLE IF NOT EXISTS usuarios (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "nome TEXT NOT NULL," +
                    "senha TEXT NOT NULL" +
                    ")";
            PreparedStatement createTableStatement = connection.prepareStatement(createTableSQL);
            createTableStatement.execute();

            System.out.print("Digite o nome de usuário para cadastro: ");
            String userName = scanner.nextLine();
            System.out.print("Digite a senha: ");
            String senha = scanner.nextLine();

            String insertSQL = "INSERT INTO usuarios (nome, senha) VALUES (?, ?)";
            PreparedStatement insertStatement = connection.prepareStatement(insertSQL);
            insertStatement.setString(1, userName);
            insertStatement.setString(2, senha);
            insertStatement.executeUpdate();

            System.out.println("Usuário cadastrado com sucesso!");

            System.out.print("\nDigite o nome de usuário para fazer login: ");
            String userNameLogin = scanner.nextLine();
            System.out.print("Digite a senha: ");
            String senhaLogin = scanner.nextLine();

            String loginSQL = "SELECT * FROM usuarios WHERE nome = ? AND senha = ?";
            PreparedStatement loginStatement = connection.prepareStatement(loginSQL);
            loginStatement.setString(1, userNameLogin);
            loginStatement.setString(2, senhaLogin);

            ResultSet resultSet = loginStatement.executeQuery();

            if (resultSet.next()) {
                System.out.println("\nLogin bem-sucedido!");

                System.out.println("\nUsuários cadastrados no banco de dados:");
                ListUsers.listUsers(connection);
            } else {
                System.out.println("Usuário/senha incorretos ou usuário não encontrado no banco de dados. Tente novamente.");
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
