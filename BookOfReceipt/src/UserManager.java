import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class UserManager {


    private static int zalogowanyUserId = -1;
    private final Connection conn = Database.connect();







    public static void zarejestruj(Connection conn, Scanner scanner) {
        try {
            System.out.print("Podaj login: ");
            String login = scanner.nextLine();


            if (czyLoginIstnieje(conn, login)) {
                System.out.println("Ten login jest już zajęty. Wybierz inny.");
                return;
            }

            System.out.print("Podaj hasło: ");
            String haslo = scanner.nextLine();

            System.out.print("Powtórz hasło: ");
            String powtorzoneHaslo = scanner.nextLine();

            if (!haslo.equals(powtorzoneHaslo)) {
                System.out.println("Hasła się nie zgadzają! Rejestracja anulowana.");
                return;
            }


            // Dodanie użytkownika do bazy
            String query = "INSERT INTO users (login, haslo) VALUES (?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, login);
                stmt.setString(2, haslo);
                stmt.executeUpdate();
                System.out.println("Rejestracja zakończona sukcesem. Możesz się teraz zalogować.");
            }

            log(login, haslo, conn);

        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    private static boolean czyLoginIstnieje(Connection conn, String login) throws SQLException {
        String query = "SELECT COUNT(*) FROM users WHERE login = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, login);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        }
        return false;
    }


    public void zaloguj() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Podaj nazwę użytkownika: ");
        String login = scanner.next();
        System.out.print("Podaj hasło: ");
        String haslo = scanner.next();
        log(login, haslo, conn);

    }

    public void wyloguj() {
        if (zalogowanyUserId != -1) {
            System.out.println("Wylogowano.");
            zalogowanyUserId = -1;
        } else {
            System.out.println("Nie jesteś zalogowany.");}
    }

    private static void log(String login, String haslo, Connection conn){
        String sql = "SELECT id_usera FROM users WHERE login = ? AND haslo = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, login);
            stmt.setString(2, haslo);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                zalogowanyUserId = rs.getInt("id_usera");
                System.out.println("Zalogowano pomyślnie!");
            } else {
                System.out.println("Nieprawidłowa nazwa użytkownika lub hasło.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    public int getZalogowanyUserId() {
        return zalogowanyUserId;
    }



}

