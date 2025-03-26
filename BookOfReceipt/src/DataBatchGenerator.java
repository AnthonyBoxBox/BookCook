import java.sql.*;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class DataBatchGenerator {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/cookbook";
    private static final String USER = "root";
    private static final String PASS = "";
    private static final int BATCH_SIZE = 1000;
    private static final int TOTAL_USERS = 6000;
    private static final int TOTAL_RECIPES = 9;

    public static void main(String[] args) {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
            conn.setAutoCommit(false);

            PreparedStatement userStmt = conn.prepareStatement(
                    "INSERT INTO users (login, haslo) VALUES (?, ?)",
                    Statement.RETURN_GENERATED_KEYS);

            PreparedStatement favoriteStmt = conn.prepareStatement(
                    "INSERT INTO ulubione_przepisy (id_usera, id_przepisu) VALUES (?, ?)");

            Random random = new Random();
            Set<String> usedUsernames = new HashSet<>();
            int processed = 0;

            while (processed < TOTAL_USERS) {
                for (int i = 0; i < BATCH_SIZE && processed < TOTAL_USERS; i++) {
                    String username;
                    do {
                        username = generateRandomString(random, 10);
                    } while (usedUsernames.contains(username));

                    usedUsernames.add(username);
                    String password = generateRandomString(random, 10);

                    userStmt.setString(1, username);
                    userStmt.setString(2, password);
                    userStmt.addBatch();

                    processed++;
                }

                try {
                    userStmt.executeBatch();
                    ResultSet generatedKeys = userStmt.getGeneratedKeys();

                    while (generatedKeys.next()) {
                        long userId = generatedKeys.getLong(1);
                        Set<Integer> usedRecipes = new HashSet<>(); // Przechowuje użyte ID przepisów dla danego użytkownika
                        for (int j = 0; j < 2; j++) {
                            int recipeId;
                            do {
                                recipeId = random.nextInt(TOTAL_RECIPES) + 1;
                            } while (usedRecipes.contains(recipeId)); // Generuj, aż będzie unikalna

                            usedRecipes.add(recipeId);
                            favoriteStmt.setLong(1, userId);
                            favoriteStmt.setInt(2, recipeId);
                            favoriteStmt.addBatch();
                        }
                    }

                    favoriteStmt.executeBatch();
                    conn.commit();

                    System.out.println("Przetworzono: " + processed + " rekordów");
                } catch (BatchUpdateException e) {
                    conn.rollback();
                    System.err.println("Błąd w batchu: " + e.getMessage());
                }
            }

            System.out.println("Zakończono generowanie danych");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static String generateRandomString(Random random, int maxLength) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        int length = random.nextInt(maxLength - 3) + 4;
        StringBuilder sb = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            sb.append(characters.charAt(random.nextInt(characters.length())));
        }

        return sb.toString();
    }
}