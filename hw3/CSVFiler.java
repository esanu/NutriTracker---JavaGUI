//Name: Sayo Sanu Andrew ID: esanu
package hw3;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class CSVFiler extends DataFiler {
	String [] fileArray;
	boolean fileReadSuccess;

	@Override
	public void writeFile(String filename) {
		String profile = null;
		String dietProducts = null;
		List<String> iDietProducts = new ArrayList<>();
		float age = 0, weight = 0, height = 0;
		
		if(NutriByte.view.ageTextField.getText().trim().isEmpty() || Float.parseFloat(NutriByte.view.ageTextField.getText().trim()) < 0 ) {
			throw new InvalidProfileException("Missing age information.");
		} else {
			try {
				age = Float.parseFloat(NutriByte.view.ageTextField.getText().trim());
			}catch(InputMismatchException e) {
				e.getLocalizedMessage();
			}
		}
		
		if(NutriByte.view.weightTextField.getText().trim().isEmpty() || Float.parseFloat(NutriByte.view.weightTextField.getText().trim()) < 0 ) {
			throw new InvalidProfileException("Missing weight information.");
		} else {
			try {
				weight = Float.parseFloat(NutriByte.view.weightTextField.getText().trim());
			}catch(InputMismatchException e) {
				e.getLocalizedMessage();
			}
		}
		
		if(NutriByte.view.heightTextField.getText().trim().isEmpty() || Float.parseFloat(NutriByte.view.heightTextField.getText().trim()) < 0 ) {
			throw new InvalidProfileException("Missing height information.");
		} else {
			try {
				height = Float.parseFloat(NutriByte.view.heightTextField.getText().trim());
			}catch(InputMismatchException e) {
				e.getLocalizedMessage();
			}
		}
		
		String physicalActivityString = NutriByte.view.physicalActivityComboBox.getValue().trim();
		int index = 0;
		String ingredientsToWatch = null;
		while(!physicalActivityString.equals(NutriProfiler.PhysicalActivityEnum.values()[index].getName())) {
			index++;
		}
		float physicalActivity = NutriProfiler.PhysicalActivityEnum.values()[index].getPhysicalActivityLevel();
		if(!NutriByte.view.ingredientsToWatchTextArea.getText().isEmpty()) {
			ingredientsToWatch = NutriByte.view.ingredientsToWatchTextArea.getText().trim();
		} else {
			ingredientsToWatch = "";
		}
		if(!NutriByte.view.genderComboBox.getSelectionModel().isEmpty()) {
			if(NutriByte.person instanceof Female) {
				profile = "Female," + age + "," + weight + "," + height + "," + physicalActivity + "," + ingredientsToWatch;
			}else {
				profile = "Male," + age + "," + weight + "," + height + "," + physicalActivity + "," + ingredientsToWatch;
			}
		}
		
		if(!(NutriByte.view.dietProductsTableView.getItems().isEmpty())) {
			for(Product product: NutriByte.view.dietProductsTableView.getItems()) {
				dietProducts = product.getNdbNumber() + "," + product.getServingSize() + "," + product.getHouseholdSize();
				iDietProducts.add(dietProducts);
			}
		}

		try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename));) {		
			profile.split(",");
			bw.write(profile);
			bw.write("\n");
			for(String product: iDietProducts) {
				bw.write(String.format("%s\n", product));
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public boolean readFile(String filename) {
		FileReader file;
		Scanner readFile = null;
		try {
			file = new FileReader(filename);
			readFile = new Scanner(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		StringBuilder sb = new StringBuilder();
		while(readFile.useDelimiter(",").hasNextLine()) {
			sb.append(readFile.nextLine().trim() + "\n");
		}
		
		fileArray = sb.toString().trim().split("\n");
		validateData();
		if(fileArray != null && validateData() == true) {	
			fileReadSuccess = true;
		}
		return fileReadSuccess;
	}
	
	public boolean validateData() {
		boolean validData = false;
		boolean personValid = false, productValid = false;
		String personData = fileArray[0];
		Person validPerson = validatePersonData(personData);
		if(validPerson !=null) {
			personValid = true;
			if(fileArray.length > 1) {
				for(int i = 1; i < fileArray.length; i++) {
					String dietList = fileArray[i];
					Product dietProduct = validateProductData(dietList);
					if(dietProduct != null) {
						productValid = true;
						NutriByte.person.dietProductsList.add(dietProduct);
					}
				}
				if(personValid == true && productValid == true) {
					validData = true;
				}
			}
			if(personValid == true) {
				validData = true;
			}
		}
		return validData;
	}
	
	public Person validatePersonData(String data) {
		StringBuilder sb = new StringBuilder();
		String [] col = data.trim().split(",");
		String gender;
		float age, weight, height, physicalActivity;
		boolean validGender, validAge, validWeight, validHeight, validAct;
		for(int i = 5; i < col.length; i++) {
			if(!col[i].isEmpty()) {		//if ingredients to watch are entered...
				sb.append(col[i].trim() + ",");		//..use string builder to append
			}	
		}
		String ingredients = sb.toString().replaceAll(",$", "");

		gender = col[0].trim();
		if(!(gender.toLowerCase().contains("male"))) {
			throw new InvalidProfileException("The profile must have a gender: Female or Male as first word");
		} else {
			validGender = true;
		}
	
		try {
			age = Float.parseFloat(col[1].trim());
			validAge = true;
		}catch(InvalidProfileException e) {
			throw new InvalidProfileException("Invalid data for Age: " + col[1] + "\nAge must be a number.");
		}catch(NumberFormatException e1) {
			throw new InvalidProfileException("Invalid data for Age: " + col[1] + "\nAge must be a number.");
		}
		
		try {
			weight = Float.parseFloat(col[2].trim());
			validWeight = true;
		}catch(InvalidProfileException e) {
			throw new InvalidProfileException("Invalid data for Weight: " + col[2] + "\nWeight must be a number.");
		}catch(NumberFormatException e1) {
			throw new InvalidProfileException("Invalid data for Weight: " + col[2] + "\nWeight must be a number.");
		}
		
		try {
			height = Float.parseFloat(col[3].trim());
			validHeight = true;
		}catch(InvalidProfileException e) {
			throw new InvalidProfileException("Invalid data for Height: " + col[3] + "\nHeight must be a number.");
		}catch(NumberFormatException e1) {
			throw new InvalidProfileException("Invalid data for Height: " + col[3] + "\nHeight must be a number.");
		}
		
		try {
			physicalActivity = Float.parseFloat(col[4].trim());
			if(physicalActivity == 1.48f || physicalActivity == 1.0f || physicalActivity == 1.1f || physicalActivity == 1.25f) {
				validAct = true;
			} else {
				throw new InvalidProfileException("Invalid physical activity level: " + col[4].trim() + "\nPhysical Acitivity Level must be 1.0, 1.1, 1.25 or 1.48");
			}
		}catch(NumberFormatException e1) {
			throw new InvalidProfileException("Invalid	data for Level of Physical Activty: " + col[4] + "\nPhysical Activity Level must be a number.");
		}
		
		if(validAct == true && validAge == true && validGender == true && validHeight == true && validWeight == true) {
			switch(gender) {
			case "Female": NutriByte.person = new Female(age, weight, height,physicalActivity, ingredients); break;
			case "Male": NutriByte.person = new Male(age, weight, height,physicalActivity, ingredients); break;
			}	
		}
		return NutriByte.person;
	}
	
	public Product validateProductData(String data) {
		Product product = null;
		Product dietProduct = null;
		String [] col = data.split(",");
		String productNdb = null;
		float dietServing, houseServing;
		if(col.length < 3) {
			throw new InvalidProfileException("Cannot read: " + data + "\nThe data must be String, number, number for NDB Number, Serving Size, Household Size.");
		} else {
				productNdb = col[0].trim();
				if(Model.productsMap.containsKey(productNdb.trim())){
					product = Model.productsMap.get(productNdb);
					dietProduct = Model.productsMap.get(productNdb);
					NutriByte.model.searchResultsList.add(dietProduct);
				} else throw new InvalidProfileException("No product found with this code: " + col[0]);
			
			try {
				dietServing = Float.parseFloat(col[1]);			
			} catch(InvalidProfileException e) {
				throw new InvalidProfileException("Cannot read: " + data + "\nThe data must be String, number, number for NDB Number, Serving Size, Household Size.");
			}
			
			try {
				houseServing = Float.parseFloat(col[2]);	
			} catch(InvalidProfileException e) {
				throw new InvalidProfileException("Cannot read: " + data + "\nThe data must be String, number, number for NDB Number, Serving Size, Household Size.");	
			}
			
			product.setHouseholdSize(houseServing);
			product.setServingSize(dietServing);
		}		
		return product;
	}
}
