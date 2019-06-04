//Name: Sayo Sanu Andrew ID: esanu
package hw3;

import hw2.NutriProfiler.AgeGroupEnum;
import hw2.NutriProfiler.PhysicalActivityEnum;
import hw3.Product.ProductNutrient;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

public abstract class Person {

	float age, weight, height, physicalActivityLevel;
	String ingredientsToWatch;
	float[][] nutriConstantsTable = new float[NutriProfiler.RECOMMENDED_NUTRI_COUNT][NutriProfiler.AGE_GROUP_COUNT];
	AgeGroupEnum ageGroup;
	int groupIndex, physLevel;
	
	ObservableList<RecommendedNutrient> recommendedNutrientsList = FXCollections.observableArrayList();
	ObservableList<Product> dietProductsList = FXCollections.observableArrayList();
	ObservableMap<String, RecommendedNutrient> dietNutrientsMap = FXCollections.observableHashMap();
	
	abstract void initializeNutriConstantsTable();
	abstract float calculateEnergyRequirement();
	
	Person(float age, float weight, float height, float physicalActivityLevel, String ingredientsToWatch) {
		this.age = age;
		this.weight = weight;
		this.height = height;
		this.physicalActivityLevel = physicalActivityLevel;
		this.ingredientsToWatch = ingredientsToWatch;
		while(this.age >= AgeGroupEnum.values()[groupIndex].getAge()) {		//if the age of person is greater or equal to the max age in age enum..
			groupIndex++;	//increase the index, until the age is less than the greatest max age in age num
		}
		ageGroup = AgeGroupEnum.values()[groupIndex];	//gets the age group at that index
		while(this.physicalActivityLevel != PhysicalActivityEnum.values()[physLevel].getPhysicalActivityLevel()) {
			physLevel++;	//gets the physical level position while there's no match in text
		}
	}
	
	float[] calculateNutriRequirement() {
		//returns an array of nutrient values of size NutriProfiler.RECOMMENDED_NUTRI_COUNT.	
		float [] nutrientValues = new float[NutriProfiler.RECOMMENDED_NUTRI_COUNT];
		for(int i = 0; i < NutriProfiler.RECOMMENDED_NUTRI_COUNT; i++) {
			if(i == 0) {
				nutrientValues[i] =  nutriConstantsTable[i][groupIndex]*weight;
			} else if (i > 0 && i < 3) {
				nutrientValues[i] =  nutriConstantsTable[i][groupIndex];
			} else {
				nutrientValues[i] = (nutriConstantsTable[i][groupIndex]*weight)/1000; 
			}
		}
		return nutrientValues;
	}
	
	public void populateDietNutrientMap() {
		dietNutrientsMap.clear();
		for(Product product: dietProductsList) {
			for(ProductNutrient productNutrient: product.getProductNutrients().values()) {
				float servingSize = product.getServingSize();
				if(servingSize == 0f) {
					servingSize = product.getHouseholdSize();
				}
				float nutrientQuantity = productNutrient.getNutrientQuantity();
				float nutrientInDiet =(servingSize*nutrientQuantity)/100;
				RecommendedNutrient dietRecommendedNutrient = new RecommendedNutrient(productNutrient.getNutrientCode(), nutrientInDiet);
				if(!(dietNutrientsMap.containsKey(productNutrient.getNutrientCode()))) {
					dietNutrientsMap.put(productNutrient.getNutrientCode(), dietRecommendedNutrient);
				} else {
					RecommendedNutrient moreNutrient = dietNutrientsMap.get(productNutrient.getNutrientCode());
					float nutrientInMap = moreNutrient.getNutrientQuantity();
					float totalNutrient = nutrientInMap + nutrientInDiet;
					moreNutrient.setNutrientQuantityProperty(totalNutrient);
				}
//				System.out.println(productNutrient.getNutrientCode() + productNutrient.getNutrientQuantity());
			}
		}
	}
}
