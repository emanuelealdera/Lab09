package it.polito.tdp.borders.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.borders.model.Border;
import it.polito.tdp.borders.model.Country;

public class BordersDAO {

	public void loadAllCountries(Map <Integer, Country> idMap) {

		String sql = "SELECT ccode, StateAbb, StateNme FROM country ORDER BY StateAbb";
		
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				if (!idMap.containsKey(rs.getInt("ccode"))) {
					Country c = new Country(rs.getInt("ccode"), rs.getString("StateAbb"), rs.getString("StateNme"));
					idMap.put(c.getId(), c);
				}
			}
			
			conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
	}

	public List<Border> getCountryPairs(int anno) {

		String sql = "SELECT c.state1no as c1, c.state2no as c2 " + 
				"FROM contiguity AS c " + 
				"WHERE c.year<= ? AND c.conttype=1";
		List <Border> borders = new ArrayList<> ();
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, anno);
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				borders.add(new Border(rs.getInt("c1"), rs.getInt("c2")));
			}
			
			conn.close();
			
		} catch (SQLException e) {
			System.out.println("Errore nel metodo getCountryPairs");
			e.printStackTrace();
		}
		return borders;
	}
}
