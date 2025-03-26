import java.sql.*;
import java.util.*;

public class DodajPrzepis {
    private Connection connection;
    private int userId;

    public DodajPrzepis(Connection connection, int userId) {
        this.connection = connection;
        this.userId = userId;
    }

    public void dodajNowyPrzepis() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Podaj nazwę przepisu: ");
        String nazwaPrzepisu = scanner.nextLine();

        System.out.print("Podaj instrukcję: ");
        String instrukcja = scanner.nextLine();

        System.out.println("Wybierz typ przepisu: 1. Śniadanie, 2. Obiad, 3. Deser");
        int typId = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Czy przepis jest wegetariański? (tak/nie): ");
        String vegeInput = scanner.nextLine().trim().toLowerCase();
        int vege = vegeInput.equals("tak") ? 1 : 0;

        System.out.print("Podaj liczbę składników: ");
        int liczbaSkladnikow = scanner.nextInt();
        scanner.nextLine();

        List<Integer> skladnikiIds = new ArrayList<>();
        List<Double> ilosciSkladnikow = new ArrayList<>();

        for (int i = 0; i < liczbaSkladnikow; i++) {
            System.out.println("Wybierz kategorię składnika:");
            int kategoriaId = wybierzKategorie(scanner);

            System.out.println("Wybierz składnik:");
            int skladnikId = wybierzSkladnik(scanner, kategoriaId);
            skladnikiIds.add(skladnikId);

            String miara = pobierzMiareSkladnika(skladnikId);
            System.out.print("Podaj ilość składnika (" + miara + "): ");
            double ilosc = scanner.nextDouble();
            scanner.nextLine();
            ilosciSkladnikow.add(ilosc);
        }

        int przepisId = zapiszPrzepisDoBazy(nazwaPrzepisu, instrukcja, typId, vege, liczbaSkladnikow);
        zapiszSkladnikiDoPrzepisu(przepisId, skladnikiIds, ilosciSkladnikow);

        System.out.println("Przepis został dodany!");
    }

    private int wybierzKategorie(Scanner scanner) {
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM kategorie");
            List<String> kategorie = new ArrayList<>();
            while (rs.next()) {
                kategorie.add(rs.getInt("kategoria_id") + ". " + rs.getString("nazwa"));
            }

            for (int i = 0; i < kategorie.size(); i++) {
                System.out.print(kategorie.get(i) + "  ");
                if ((i + 1) % 3 == 0 || i == kategorie.size() - 1) {
                    System.out.println();
                }
            }

            System.out.print("Wybierz numer kategorii: ");
            return scanner.nextInt();
        } catch (SQLException e) {
            throw new RuntimeException("Błąd pobierania kategorii", e);
        }
    }

    private int wybierzSkladnik(Scanner scanner, int kategoriaId) {
        try {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM skladniki WHERE kategoria_id = ?");
            stmt.setInt(1, kategoriaId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                System.out.println(rs.getInt("id_skladnika") + ". " + rs.getString("nazwa_skladnika"));
            }
            System.out.print("Wybierz numer składnika: ");
            return scanner.nextInt();
        } catch (SQLException e) {
            throw new RuntimeException("Błąd pobierania składników", e);
        }
    }

    private String pobierzMiareSkladnika(int skladnikId) {
        try {
            PreparedStatement stmt = connection.prepareStatement("SELECT jednostka_miary FROM skladniki WHERE id_skladnika = ?");
            stmt.setInt(1, skladnikId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("jednostka_miary");
            }
            return "szt."; // Domyślna jednostka
        } catch (SQLException e) {
            throw new RuntimeException("Błąd pobierania miary składnika", e);
        }
    }

    private int zapiszPrzepisDoBazy(String nazwa, String instrukcja, int typId, int vege, int iloscSkladnikow) {
        try {
            PreparedStatement stmt = connection.prepareStatement(
                    "INSERT INTO przepisy (user_id, nazwa, instrukcje, typ_przepisu, vege, ilosc_skladnikow) VALUES (?, ?, ?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS
            );
            stmt.setInt(1, userId);
            stmt.setString(2, nazwa);
            stmt.setString(3, instrukcja);
            stmt.setInt(4, typId);
            stmt.setInt(5, vege);
            stmt.setInt(6, iloscSkladnikow);
            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            } else {
                throw new SQLException("Nie udało się uzyskać ID nowego przepisu");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Błąd zapisywania przepisu", e);
        }
    }

    private void zapiszSkladnikiDoPrzepisu(int przepisId, List<Integer> skladnikiIds, List<Double> ilosci) {
        try {
            PreparedStatement stmt = connection.prepareStatement(
                    "INSERT INTO przepisy_skladniki (id_przepisu, id_skladnika, ilosc) VALUES (?, ?, ?)"
            );
            for (int i = 0; i < skladnikiIds.size(); i++) {
                stmt.setInt(1, przepisId);
                stmt.setInt(2, skladnikiIds.get(i));
                stmt.setDouble(3, ilosci.get(i));
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Błąd zapisywania składników", e);
        }
    }
}
