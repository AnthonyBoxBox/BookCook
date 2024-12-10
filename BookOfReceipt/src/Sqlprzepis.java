
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
public class Sqlprzepis {
		
		 	String pass = "";
	
	        String url = "jdbc:mysql://localhost:3306/cookbook";
	        String user = "root";
	       
	        try (Connection connection = DriverManager.getConnection(url, user, pass);
	             PreparedStatement statement = connection.prepareStatement(
	                     		"SELECT p.nazwa, s.nazwa_skladnika, ps.ilosc, s.jednostka_miary " +
	                             "FROM przepisy p " +
	                             "JOIN przepisy_skladniki ps ON p.id_przepisu = ps.id_przepisu " +
	                             "JOIN skladniki s ON ps.id_skladnika = s.id_skladnika " +
	                             "WHERE p.id_przepisu = ?")) {
	
	            
	            statement.setInt(1, 2);
	
	            
	            ResultSet resultSet = statement.executeQuery();
	
	            
	            while (resultSet.next()) {
	                String nazwaPrzepisu = resultSet.getString("nazwa");
	                String nazwaSkladnika = resultSet.getString("nazwa_skladnika");
	                int ilosc = resultSet.getInt("ilosc");
	                String jednostkaMiary = resultSet.getString("jednostka_miary");
	
	                System.out.println("Przepis: " + nazwaPrzepisu);
	                System.out.println("Składnik: " + nazwaSkladnika + " (" + ilosc + " " + jednostkaMiary + ")");
	            }
	
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
}
}
                                            
          
          
          
          
          