/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.PremierLeague;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.PremierLeague.model.Adiacenze;
import it.polito.tdp.PremierLeague.model.Match;
import it.polito.tdp.PremierLeague.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {

	Model model;
	
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="btnCreaGrafo"
    private Button btnCreaGrafo; // Value injected by FXMLLoader

    @FXML // fx:id="btnConnessioneMassima"
    private Button btnConnessioneMassima; // Value injected by FXMLLoader

    @FXML // fx:id="btnCollegamento"
    private Button btnCollegamento; // Value injected by FXMLLoader

    @FXML // fx:id="txtMinuti"
    private TextField txtMinuti; // Value injected by FXMLLoader

    @FXML // fx:id="cmbMese"
    private ComboBox<Integer> cmbMese; // Value injected by FXMLLoader

    @FXML // fx:id="cmbM1"
    private ComboBox<Match> cmbM1; // Value injected by FXMLLoader

    @FXML // fx:id="cmbM2"
    private ComboBox<Match> cmbM2; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void doConnessioneMassima(ActionEvent event) {
    	String min = txtMinuti.getText();
    	int minuti = Integer.parseInt(min);
    	int mese = cmbMese.getValue();
    	model.creaGrafo(minuti, mese);
    	List<Adiacenze> lista = model.getConnessioniMax(minuti, mese);
    	txtResult.appendText("Connessioni massime: \n");
    	for(Adiacenze adiacenze : lista) {
    		txtResult.appendText(adiacenze.toString());
    	}
    	
    }

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	String min = txtMinuti.getText();
    	int minuti = Integer.parseInt(min);
    	int mese = cmbMese.getValue();
    	model.creaGrafo(minuti, mese);
    	txtResult.appendText("Numero di vertici creato: "+ model.getVertici()+"\n");
    	txtResult.appendText("Numero di archi creato: "+ model.getArchi()+"\n\n");
    	cmbM1.getItems().addAll(model.listaMatchs());
    	cmbM2.getItems().addAll(model.listaMatchs());
    	
    }

    @FXML
    void doCollegamento(ActionEvent event) {
    	Match m1 = cmbM1.getValue();
    	Match m2 = cmbM2.getValue();
    	String s="";
    	List<Match> result = model.getPercorso(m1, m2);
    	
    	for(Match mmMatch : result) {
    		s=s+mmMatch.toString()+"\n";
    		
    	}
    	txtResult.setText(s);
    	
    	
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert btnCreaGrafo != null : "fx:id=\"btnCreaGrafo\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnConnessioneMassima != null : "fx:id=\"btnConnessioneMassima\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnCollegamento != null : "fx:id=\"btnCollegamento\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtMinuti != null : "fx:id=\"txtMinuti\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbMese != null : "fx:id=\"cmbMese\" was not injected: check your FXML file 'Scene.fxml'.";        assert cmbM1 != null : "fx:id=\"cmbM1\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbM2 != null : "fx:id=\"cmbM2\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";

    }
    
    public void setModel(Model model) {
    	this.model = model;
    	for(int i=1; i<=12; i++)
       	 cmbMese.getItems().add(i);
  
    }
    
    
}
