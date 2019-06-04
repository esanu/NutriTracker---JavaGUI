//Name: Sayo Sanu Andrew_ID: esanu
package hw3;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Nutrient {
	private StringProperty nutrientCode = new SimpleStringProperty(); 
	private StringProperty nutrientName = new SimpleStringProperty();
	private StringProperty nutrientUom = new SimpleStringProperty();

	Nutrient(){
		nutrientCode.set("");
		nutrientName.set("");
		nutrientUom.set("");
	}
	
	Nutrient(String nutrientCode, String nutrientName, String nutrientUom){
		this.nutrientCode.set(nutrientCode);
		this.nutrientName.set(nutrientName);
		this.nutrientUom.set(nutrientUom);
	}
	
	//getters and setters to access private member variables
	public String getNutrientCode() {return nutrientCode.get();}
	public String getNutrientName() {return nutrientName.get();}
	public String getNutrientUom() {return nutrientUom.get();}
	public StringProperty getNutrientCodeProperty() {return nutrientCode;}
	public StringProperty nutrientNameProperty () {return nutrientName;}
	public StringProperty getNutrientUomProperty () {return nutrientUom;}
	
	public void setNutrientCode(String nutrientCode) {this.nutrientCode.set(nutrientCode);}
	public void setNutrientName(String nutrientName) {this.nutrientName.set(nutrientName);}
	public void setNutrientUom(String nutrientUom) {this.nutrientUom.set(nutrientUom);}
	public void setNutrientCodeProperty(StringProperty nutrientCode) {this.nutrientCode = nutrientCode;}
	public void setNutrientNameProperty(StringProperty nutrientName) {this.nutrientName = nutrientName;}
	public void setNutrientUomProperty(StringProperty nutrientUom) {this.nutrientUom = nutrientUom;}
}
