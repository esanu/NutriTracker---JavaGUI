//Name: Sayo Sanu Andrew ID: esanu
package hw3;

import javafx.beans.property.FloatProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class RecommendedNutrient {
	
	private StringProperty nutrientCode = new SimpleStringProperty(); 
	private FloatProperty nutrientQuantity = new SimpleFloatProperty();
	
	RecommendedNutrient(){
		nutrientCode.set("");
		nutrientQuantity.set(0);
	}
	
	RecommendedNutrient(String nutrientCode, float nutrientQuantity){
		this.nutrientCode.set(nutrientCode);
		this.nutrientQuantity.set(nutrientQuantity);
	}
	
	//getters and setters to access private member variables
	public String getNutrientCode() {return nutrientCode.get();	}
	public StringProperty getNutrientCodeStringProperty() {return nutrientCode;	}
	public float getNutrientQuantity() {return nutrientQuantity.get();}
	public FloatProperty getNutrientQuantityFloatProperty() {return nutrientQuantity;}

	public void setNutrientCode(StringProperty nutrientCode) {this.nutrientCode = nutrientCode;}
	public void setNutrientCodeProperty(String nutrientCode) {this.nutrientCode.set(nutrientCode);;}
	public void setNutrientQuantity(FloatProperty nutrientQuantity) {this.nutrientQuantity = nutrientQuantity;}
	public void setNutrientQuantityProperty(float nutrientQuantity) {this.nutrientQuantity.set(nutrientQuantity);;}
}
