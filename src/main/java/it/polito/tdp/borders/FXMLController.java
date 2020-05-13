 package it.polito.tdp.borders;

 import java.util.List;

import it.polito.tdp.borders.model.Country;
import it.polito.tdp.borders.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
 import javafx.scene.control.Button;
 import javafx.scene.control.ComboBox;
 import javafx.scene.control.TextArea;
 import javafx.scene.control.TextField;

 public class FXMLController {
	 
	 Model model;
	 
	 public void setModel (Model model) {
		 this.model = model;
	 }

     @FXML
     private TextField txtAnno;

     @FXML
     private ComboBox<Country> comboBox;

     @FXML
     private Button btnConfinanti;

     @FXML
     private TextArea txtResult;

     @FXML
     void doCalcolaConfini(ActionEvent event) {
    	 txtResult.clear();
     	String annoString = txtAnno.getText();
     	int anno = -1;
     	try {
     		anno = Integer.parseInt(annoString);
     	} catch (NumberFormatException e) {
     		e.printStackTrace();
     	}
     	if (anno > 2006 || anno < 1816) {
     		txtResult.setText("Inserire un anno tra 1812 e 2006");
     		return;
     	}
     	
     	model.creaGrafo(anno);
     	comboBox.getItems().addAll(model.getVertexSet());
     	txtResult.appendText(model.getComponentiConnesse());
     	txtResult.appendText(model.vertexSetToString());
     }

     @FXML
     void trovaConfinanti(ActionEvent event) {

    	 txtResult.clear();
    	 Country c = comboBox.getValue();
    	 if (c==null) {
    		 txtResult.setText("Inserire uno stato dalla comboBox");
    		 return;
    	 }
    	 
    	 List <Country> result = model.getConfinanti(c);
    	 String str = c.getName()+" è connesso ai seguenti stati:\n";
    	 
    	 for (Country country : result) {
    		 if (!c.equals(country))
    			 str+=country.getName()+"\n";
    	 }
    	 
    	 txtResult.appendText(str);
    	 
    	 
     }

 }
