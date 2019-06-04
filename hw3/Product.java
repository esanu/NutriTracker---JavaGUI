//Name: Sayo Sanu Andrew_ID: esanu
package hw3;

import java.util.Map;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;

public class Product {
	private StringProperty ndbNumber = new SimpleStringProperty(); 
	private StringProperty productName = new SimpleStringProperty(); 
	private StringProperty manufacturer = new SimpleStringProperty(); 
	private StringProperty ingredients = new SimpleStringProperty(); 
	private FloatProperty servingSize = new SimpleFloatProperty(); 
	private StringProperty servingUom = new SimpleStringProperty(); 
	private FloatProperty householdSize = new SimpleFloatProperty();
	private StringProperty householdUom = new SimpleStringProperty(); 
	private ObservableMap<String, ProductNutrient> productNutrients = FXCollections.observableHashMap();	
	//hashmap to store nutrients in the product; the key is nutrientCode
	
	Product(){
		ndbNumber.set("");
		productName.set("");
		manufacturer.set("");
		ingredients.set("");
		servingUom.set("");
		householdUom.set("");
	}
	
	Product(String ndbNumber, String produtName, String manufactuer, String ingredients, float servingSize, String servingUom, float householdSize, String householdUom){
		this.ndbNumber.set(ndbNumber);
		this.productName.set(produtName);
		this.manufacturer.set(manufactuer);
		this.ingredients.set(ingredients);
		this.servingSize.set(servingSize);
		this.servingUom.set(servingUom);
		this.householdSize.set(householdSize);
		this.householdUom.set(householdUom);
	}
	
	Product(String ndbNumber, String produtName, String manufactuer, String ingredients){
		this.ndbNumber.set(ndbNumber);
		this.productName.set(produtName);
		this.manufacturer.set(manufactuer);
		this.ingredients.set(ingredients);
	}
	
	public String toString() {
		return productName.get().toString() + " by " +  manufacturer.get().toString();
	}

	//getters and setters to access private member variables
	public String getNdbNumber() {return ndbNumber.get();}
	public String getProductName() {return productName.get();}
	public String getManufacturer() {return manufacturer.get();}
	public String getIngredients() {return ingredients.get();}
	public float getServingSize() {return servingSize.get();}
	public String getServingUom() {return servingUom.get();}
	public float getHouseholdSize() {return householdSize.get();}
	public String getHouseholdUom() {return householdUom.get();}
	public Map<String, ProductNutrient> getProductNutrients() {return productNutrients;}	
	
	public StringProperty getNdbNumberProperty() {return ndbNumber;}
	public StringProperty getProductNameProperty() {return productName;}
	public StringProperty getManufacturerProperty() {return manufacturer;}
	public StringProperty getIngredientsProperty() {return ingredients;}
	public FloatProperty getServingSizeProperty() {return servingSize;}
	public StringProperty getServingUomProperty() {return servingUom;}
	public FloatProperty getHouseholdSizeProperty() {return householdSize;}
	public StringProperty getHouseholdUomProperty() {return householdUom;}

	public void setNdbNumber(String ndbNumber) {this.ndbNumber.set(ndbNumber);}
	public void setProductName(String productName) {this.productName.set(productName);}
	public void setManufacturer(String manufacturer) {this.manufacturer.set(manufacturer);}
	public void setIngredients(String ingredients) {this.ingredients.set(ingredients);}
	public void setServingSize(float servingSize) {this.servingSize.set(servingSize);}
	public void setServingUom(String servingUom) {this.servingUom.set(servingUom);}
	public void setHouseholdSize(float householdSize) {this.householdSize.set(householdSize);}
	public void setHouseholdUom(String householdUom) {	this.householdUom.set(householdUom);}
	public void setProductNutrients(String nutrientCode, ProductNutrient productNutrient) {
		this.productNutrients.put(nutrientCode, productNutrient);
	}

	public void setNdbNumberProperty(StringProperty ndbNumber) {this.ndbNumber = ndbNumber;}
	public void setProductNameProperty(StringProperty productName) {this.productName = productName;}
	public void setManufacturerProperty(StringProperty manufacturer) {this.manufacturer = manufacturer;}
	public void setIngredientsProperty(StringProperty ingredients) {this.ingredients = ingredients;}
	public void setServingSizeProperty(FloatProperty servingSize) {this.servingSize = servingSize;}
	public void setServingUomProperty(StringProperty servingUom) {this.servingUom = servingUom;}
	public void setHouseholdSizeProperty(FloatProperty householdSize) {this.householdSize = householdSize;}
	public void setHouseholdUomProperty(StringProperty householdUom) {	this.householdUom = householdUom;}
	public void setProductNutrientsProperty(ObservableMap<String, ProductNutrient> productNutrients) {this.productNutrients = productNutrients;}
	
	//inner class with getters and setters to access member variables
	public class ProductNutrient {
		private StringProperty nutrientCode = new SimpleStringProperty();
		private FloatProperty nutrientQuantity = new SimpleFloatProperty();
		
		public ProductNutrient() {
			nutrientCode.set("");
			nutrientQuantity.set(0);
		}
		
		ProductNutrient(String nutrientCode, float nutrientQuantity){
			this.nutrientCode.set(nutrientCode);
			this.nutrientQuantity.set(nutrientQuantity);
		}

		public String getNutrientCode() {return nutrientCode.get();}
		public float getNutrientQuantity() {return nutrientQuantity.get();}
		public StringProperty getNutrientCodeProperty() {return nutrientCode;}
		public FloatProperty getNutrientQuantityProperty () {return nutrientQuantity;}
		
		public void setNutrientCode(String nutrientCode) {this.nutrientCode.set(nutrientCode);}
		public void setNutrientQuantity(float nutrientQuantity) {this.nutrientQuantity.set(nutrientQuantity);}
		public void setNutrientCodeProperty(StringProperty nutrientCode) {this.nutrientCode = nutrientCode;}
		public void setNutrientQuantityProperty(FloatProperty nutrientQuantity) {this.nutrientQuantity = nutrientQuantity;}

	}

}
