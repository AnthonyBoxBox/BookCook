import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Exdb {
    private Connection _conn = Database.connect();
    private ArrayList<Przepis> getRes(ResultSet resultSet){

        try {
            ArrayList<Przepis> przepisyy = new ArrayList<Przepis>();

            while (resultSet.next()) {
                Boolean vege = resultSet.getBoolean("vege");
                Przepis przepiss;
                if(vege) {
                    przepiss = new PrzepisVege(resultSet.getInt("ilosc_skladnikow"), resultSet.getString("instrukcje"), resultSet.getString("nazwa"));
                } else {
                    przepiss = new Przepis(resultSet.getInt("ilosc_skladnikow"), resultSet.getString("instrukcje"), resultSet.getString("nazwa"));
                }
                przepisyy.add(przepiss);

            }
            return przepisyy;


        } catch (SQLException e) {

            e.printStackTrace();
            return new ArrayList<Przepis>();
        }


    }
    public ArrayList<Przepis> getAll() {
        try{
            PreparedStatement statement = _conn.prepareStatement("SELECT * FROM przepisy");
            ResultSet resultSet = statement.executeQuery();
            return getRes(resultSet);

        } catch (SQLException e){
            e.printStackTrace();
            return new ArrayList<Przepis>();
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
    public void printType() {
        try {
            PreparedStatement typeStatement = _conn.prepareStatement("SELECT DISTINCT typ FROM przepisy");
            ResultSet resultSet = typeStatement.executeQuery();
            int y = 1;
            while (resultSet.next()) {
                String type = resultSet.getString("typ");
                System.out.println(y + " " + type + "\n"); // Usuń `\n`, bo println już dodaje nową linię
                y++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }




}
