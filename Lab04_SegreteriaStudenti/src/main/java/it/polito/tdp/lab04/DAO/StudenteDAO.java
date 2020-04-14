package it.polito.tdp.lab04.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import it.polito.tdp.lab04.model.Studente;

public class StudenteDAO {
	
	public Studente getStudenteByMatricola(int matricola) {
		String sql="SELECT cognome, nome " + 
				"FROM studente " + 
				"WHERE matricola=? ";
		Studente studente=null;
		try {
			Connection conn=ConnectDB.getConnection();
			PreparedStatement st=conn.prepareStatement(sql);
			st.setInt(1, matricola);
			ResultSet rs=st.executeQuery();
			
			if(rs.next()) {
				studente=new Studente(matricola, rs.getString("cognome"), rs.getString("nome"), null);
				
			}
			conn.close();
		}catch(SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Errore db");
		}
		return studente;
	}

}
