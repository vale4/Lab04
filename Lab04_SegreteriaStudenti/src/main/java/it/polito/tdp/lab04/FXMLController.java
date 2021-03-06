package it.polito.tdp.lab04;

import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.lab04.model.Corso;
import it.polito.tdp.lab04.model.Model;
import it.polito.tdp.lab04.model.Studente;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {
	
	private Model model;
	private List<Corso>corsi;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label lblCorso;

    @FXML
    private ComboBox<Corso> boxCorsi;

    @FXML
    private Button btnCercaIscrittiCorso;

    @FXML
    private Label lblStudente;

    @FXML
    private TextField txtMatricola;

    @FXML
    private Button btnCompleta;

    @FXML
    private TextField txtNome;

    @FXML
    private TextField txtCognome;

    @FXML
    private Button btnCercaCorsi;

    @FXML
    private Button btnIscrivi;

    @FXML
    private TextArea txtRisultato;

    @FXML
    private Button btnReset;

    @FXML
    void doCercaCorsi(ActionEvent event) {
    	try {
    		
    	int matricola=Integer.parseInt(txtMatricola.getText());
    	Studente studente=model.getStudenteByMatricola(matricola);
    	
    	if(studente==null) {
    		txtRisultato.appendText("Matricola inesistente\n");
    		return;
    	}
    	
    	List<Corso>corsi=model.getCorsiByStudente(studente);
    	StringBuilder sb=new StringBuilder();
    	
    	for(Corso corso : corsi) {
    		sb.append(String.format("%-10s", corso.getCodins()));
    		sb.append(String.format("%-10s", corso.getCrediti()));
    		sb.append(String.format("%-50s", corso.getNome()));
    		sb.append(String.format("%-10s", corso.getPd()));
    		sb.append("\n");
    		
    		txtRisultato.appendText(sb.toString());
    	}
    	
    	}catch(NumberFormatException e ) {
    		txtRisultato.appendText("Inserire la matricola in formato numerico");
    	}catch(RuntimeException e) {
    		txtRisultato.appendText("Errore di connessione ad database");
    	}

    }

    @FXML
    void doCercaIscrittiCorso(ActionEvent event) {
    	try {
    	Corso corso=boxCorsi.getValue();
    	if(corso==null) {
    		txtRisultato.setText("Selezionare un corso\n");
    		return;
    	}
    	List<Studente>studenti=model.getStudentiIscrittiAlCorso(corso);
    	StringBuilder sb=new StringBuilder();
    	
    	for(Studente studente: studenti) {
    		sb.append(String.format("%-10s", studente.getMatricola()));
    		sb.append(String.format("%-20s", studente.getCognome()));
    		sb.append(String.format("%-20s", studente.getNome()));
    		sb.append(String.format("%-10s", studente.getCds()));
    		sb.append("\n");
    	}
    	
    	txtRisultato.appendText(sb.toString());
    	
    	}catch(RuntimeException e) {
    		txtRisultato.setText("Errore di connessione al database\n");
    	}

    }

    @FXML
    void doCompleta(ActionEvent event) {
    	
    	try {
    		int matricola=Integer.parseInt(txtMatricola.getText());
    		Studente studente=model.getStudenteByMatricola(matricola);
    		
    		if(studente==null) {
    			txtRisultato.appendText("Studente inesistente\n");
    			return;
    		}
    		txtNome.setText(studente.getNome());
    		txtCognome.setText(studente.getCognome());
    		
    	}catch(NumberFormatException e) {
    		txtRisultato.setText("Inserire una matricola di soli numeri\n");
    	}catch(RuntimeException e){
    		txtRisultato.setText("Errore di connessione al database");
    	}
    	

    }

    @FXML
    void doIscrivi(ActionEvent event) {
    	
    	try {
    		if(txtMatricola.getText().isEmpty()) {
    			txtRisultato.appendText("Inserire la matricola\n");
    			return;
    		}
    		
    		if(boxCorsi.getValue()==null) {
    			txtRisultato.appendText("Selezionare un corso\n");
    			return;
    		}
    		
    		int matricola=Integer.parseInt(txtMatricola.getText());
    		Studente studente=model.getStudenteByMatricola(matricola);
    		
    		if(studente==null) {
    			txtRisultato.appendText("Studente inesistente\n");
    			return;
    		}
    		
    		txtNome.setText(studente.getNome());
    		txtCognome.setText(studente.getCognome());
    		
    		Corso corso=boxCorsi.getValue();
    		
    		if(model.isStudenteIscrittoAlCorso(studente, corso)) {
    			txtRisultato.appendText("Lo studente è già iscritto al corso\n");
    			return;
    		}
    		
    		if(model.iscriviStudenteAlCorso(studente, corso)) {
    			txtRisultato.appendText("Lo studente è stato iscritto al corso con successo!\n");
    		}
    		else {
    			txtRisultato.appendText("Errore nell'iscrizione\n");
    		}
    		
    		
    		
    		
    	}catch(NumberFormatException e) {
    		txtRisultato.appendText("La matricola deve avere solo caratteri numerici\n");
    	}catch(RuntimeException e) {
    		txtRisultato.appendText("Errore di connessione al database\n");
    	}

    }

    @FXML
    void doReset(ActionEvent event) {
    	txtRisultato.clear();
    	txtMatricola.clear();
    	txtNome.clear();
    	txtCognome.clear();
    	boxCorsi.getSelectionModel().clearSelection();

    }
    
    private void setComboItems() {
    	corsi=model.getTuttiICorsi();
    	Collections.sort(corsi, new ComparatoreCorsiNome());
    	boxCorsi.getItems().addAll(corsi);
    }

    @FXML
    void initialize() {
        assert lblCorso != null : "fx:id=\"lblCorso\" was not injected: check your FXML file 'Scene.fxml'.";
        assert boxCorsi != null : "fx:id=\"boxCorsi\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnCercaIscrittiCorso != null : "fx:id=\"btnCercaIscrittiCorso\" was not injected: check your FXML file 'Scene.fxml'.";
        assert lblStudente != null : "fx:id=\"lblStudente\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtMatricola != null : "fx:id=\"txtMatricola\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnCompleta != null : "fx:id=\"btnCompleta\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtNome != null : "fx:id=\"txtNome\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtCognome != null : "fx:id=\"txtCognome\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnCercaCorsi != null : "fx:id=\"btnCercaCorsi\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnIscrivi != null : "fx:id=\"btnIscrivi\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtRisultato != null : "fx:id=\"txtRisultato\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnReset != null : "fx:id=\"btnReset\" was not injected: check your FXML file 'Scene.fxml'.";

    }
    
    public void setModel(Model model) {
    	this.model=model;
    	setComboItems();
    }
}
