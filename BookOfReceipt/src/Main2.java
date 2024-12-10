import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
public class Main2{
	public static void main(String[] args){
		Connection conn = Database.connect();
		
		try {
			PreparedStatement statement = conn.prepareStatement("SELECT * FROM przepisy");
			
			ResultSet resultSet = statement.executeQuery();
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
			for(Przepis p: przepisyy) {
				p.wypiszPrzepis();
			}
		
		
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
		
		          	
		             	
		          


}}
