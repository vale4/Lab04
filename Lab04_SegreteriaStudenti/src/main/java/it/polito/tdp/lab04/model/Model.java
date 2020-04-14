package it.polito.tdp.lab04.model;

import java.util.List;

import it.polito.tdp.lab04.DAO.CorsoDAO;
import it.polito.tdp.lab04.DAO.StudenteDAO;

public class Model {
	
	private CorsoDAO corsoDAO;
	private StudenteDAO studenteDAO;
	
	public Model() {
		this.corsoDAO=new CorsoDAO();
		this.studenteDAO=new StudenteDAO();
	}

	public List<Corso> getTuttiICorsi() {
		// TODO Auto-generated method stub
		return corsoDAO.getTuttiICorsi();
	}
	
	public Studente getStudenteByMatricola(int matricola) {
		return studenteDAO.getStudenteByMatricola(matricola);
	}
	
	public boolean isStudenteIscrittoAlCorso(Studente studente, Corso corso) {
		return studenteDAO.isStudenteIscrittoAlCorso(studente, corso);
	}
	
	public List<Studente>getStudentiIscrittiAlCorso(Corso corso){
		return corsoDAO.getStudentiIscrittiAlCorso(corso);
	}
	
	public List<Corso>getCorsiByStudente(Studente studente){
		return studenteDAO.getCorsiByStudente(studente);
	}
	
	public boolean iscriviStudenteAlCorso(Studente studente, Corso corso) {
		return corsoDAO.inscriviStudenteACorso(studente, corso);
	}
	

}
