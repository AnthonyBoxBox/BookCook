import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Exdb {
    private final Connection _conn = Database.connect();

    private ArrayList<Przepis> getRes(ResultSet resultSet) {
        try {
            ArrayList<Przepis> przepisyy = new ArrayList<>();
            while (resultSet.next()) {
                boolean vege = resultSet.getBoolean("vege");
                Przepis przepiss;
                if (vege) {
                    przepiss = new PrzepisVege(resultSet.getInt("ilosc_skladnikow"), resultSet.getString("instrukcje"), resultSet.getString("nazwa"));
                } else {
                    przepiss = new Przepis(resultSet.getInt("ilosc_skladnikow"), resultSet.getString("instrukcje"), resultSet.getString("nazwa"));
                }
                przepisyy.add(przepiss);
            }
            return przepisyy;
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
    public void getAll(){
        try {
           PreparedStatement allStatement = _conn.prepareStatement("SELECT id_przepisu, nazwa FROM przepisy WHERE user_id=1");
           ResultSet resultSet = allStatement.executeQuery();
           while (resultSet.next()){
               int id = resultSet.getInt("id_przepisu");
               String nazw = resultSet.getString("nazwa");
               System.out.println(id + "  " + nazw);
           }
        }   catch (SQLException e) {
            e.printStackTrace();
        }


    }

    public void printType() {
        try {
            PreparedStatement typeStatement = _conn.prepareStatement("SELECT DISTINCT typ FROM przepisy");
            ResultSet resultSet = typeStatement.executeQuery();
            int y = 1;
            while (resultSet.next()) {
                String type = resultSet.getString("typ");
                System.out.println(y + " " + type + "\n");
                y++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void dodajDoUlubionych(int userId, int przepisId) {
        String checkSql = "SELECT * FROM ulubione_przepisy WHERE id_usera = ? AND id_przepisu = ?";
        try (PreparedStatement checkStmt = _conn.prepareStatement(checkSql)) {
            checkStmt.setInt(1, userId);
            checkStmt.setInt(2, przepisId);
            ResultSet rs = checkStmt.executeQuery();
            if (rs.next()) {
                System.out.println("Ten przepis jest już w ulubionych.");
                return;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }








        String sql = "INSERT INTO ulubione_przepisy (id_usera, id_przepisu) VALUES (?, ?)";
        try (PreparedStatement stmt = _conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.setInt(2, przepisId);
            stmt.executeUpdate();
            System.out.println("Dodano do ulubionych!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public ArrayList<Przepis> getType(String type) {
        String query = "SELECT * FROM przepisy WHERE typ = ?";
        try (PreparedStatement statement = _conn.prepareStatement(query)) {
            statement.setString(1, type);

            try (ResultSet resultSet = statement.executeQuery()) {
                return getRes(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }



    public void wyswietlUlubione(int userId) {
        String sql = "SELECT p.id_przepisu, p.nazwa FROM ulubione_przepisy up " +
                "JOIN przepisy p ON up.id_przepisu = p.id_przepisu " +
                "WHERE up.id_usera = ?";
        try (PreparedStatement stmt = _conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            System.out.println("\nTwoje ulubione przepisy:");
            while (rs.next()) {
                System.out.println(rs.getInt("id_przepisu") + ". " + rs.getString("nazwa"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}