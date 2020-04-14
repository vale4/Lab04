package it.polito.tdp.lab04.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.polito.tdp.lab04.model.Corso;
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
	
	
	public boolean isStudenteIscrittoAlCorso(Studente studente, Corso corso) {
		String sql="SELECT * " + 
				"FROM iscrizione " + 
				"WHERE matricola=? AND codins=? ";
		boolean iscritto=false;
		try {
			Connection conn=ConnectDB.getConnection();
			PreparedStatement st=conn.prepareStatement(sql);
			st.setInt(1, studente.getMatricola());
			st.setString(2, corso.getCodins());
			ResultSet rs=st.executeQuery();
			
			if(rs.next()) {
				iscritto=true;
			}
			conn.close();
			
		}catch(SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Errore db");
		}
		return iscritto;
	}
	
	public List<Corso>getCorsiByStudente(Studente studente){
		String sql="SELECT * " + 
				"FROM iscrizione AS i, corso AS c " + 
				"WHERE i.codins=c.codins AND i.matricola=? ";
		List<Corso>corsi=new ArrayList<>();
		try {
			Connection conn=ConnectDB.getConnection();
			PreparedStatement st=conn.prepareStatement(sql);
			st.setInt(1, studente.getMatricola());
			ResultSet rs=st.executeQuery();
			
			while(rs.next()) {
				Corso c=new Corso(rs.getString("codins"), rs.getInt("crediti"), rs.getString("nome"), rs.getInt("pd"));
				corsi.add(c);
			}
			conn.close();
			
			return corsi;
			
		}catch(SQLException e) {
			throw new RuntimeException("Errore db", e);
		}
		
	}

}
