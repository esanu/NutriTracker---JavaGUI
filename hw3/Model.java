//Name: Sayo Sanu Andrew_ID: esanu
package hw3;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

public class Model {
	
	static ObservableMap<String, Product> productsMap = FXCollections.observableHashMap();
	static ObservableMap<String, Nutrient> nutrientsMap = FXCollections.observableHashMap();
	ObservableList<Product> searchResultsList = FXCollections.observableArrayList();
	
	public void readProducts(String productFile) {
		CSVFormat csvFormat = CSVFormat.DEFAULT.withFirstRecordAsHeader();
		try {
			CSVParser csvParser = CSVParser.parse(new FileReader(productFile), csvFormat);	//reads product file
			for (CSVRecord csvRecord : csvParser) {		//for each record in file...
				Product product = new Product(csvRecord.get(0), csvRecord.get(1),	//..create a new product object..
						csvRecord.get(4), csvRecord.get(7));
				productsMap.put(csvRecord.get(0), product);	//..and put the product in a products map using the NDB# as the key
			}
		}
		catch (FileNotFoundException e1) { e1.printStackTrace(); }
		catch (IOException e1) { e1.printStackTrace(); }
	}

	public void readNutrients(String nutrientFile) {
		CSVFormat csvFormat = CSVFormat.DEFAULT.withFirstRecordAsHeader();
		try {
			CSVParser csvParser = CSVParser.parse(new FileReader(nutrientFile), csvFormat);
			for (CSVRecord csvRecord : csvParser) {
				Nutrient nutrient = new Nutrient(csvRecord.get(1), csvRecord.get(2), csvRecord.get(5));
				nutrientsMap.put(csvRecord.get(1), nutrient);	//puts nutrient objects with nutrient map (unique on nutrient number)
				if(productsMap.get(csvRecord.get(0)).getNdbNumber().equals(csvRecord.get(0)) && !(csvRecord.get(4).trim().equals("0.0"))) {	
					//if the NDBnumber in the product map matches the NDB number in nutrients file, and the nutrient quantity is greater than zero...
					Product product = productsMap.get(csvRecord.get(0));	//...get the corresponding product assign it to a product object..
					Product.ProductNutrient productNutrient = product.new ProductNutrient (csvRecord.get(1), Float.parseFloat(csvRecord.get(4)));
					//..create a product nutrient for that product..
					product.setProductNutrients(csvRecord.get(1), productNutrient);		//..and put the product nutrient in the product's product nutrient map 
				}	
			}
		}
		catch (FileNotFoundException e1) { e1.printStackTrace(); }
		catch (IOException e1) { e1.printStackTrace(); }
	}

	public void readServingSizes(String servingSizeFile) {
		CSVFormat csvFormat = CSVFormat.DEFAULT.withFirstRecordAsHeader();
		try {
			CSVParser csvParser = CSVParser.parse(new FileReader(servingSizeFile), csvFormat);	//reads serving size file
			for (CSVRecord csvRecord : csvParser) {
				if(!(csvRecord.get(1).isEmpty() || csvRecord.get(2).isEmpty() || csvRecord.get(3).isEmpty() || csvRecord.get(4).isEmpty())) {
					//if the values are not empty
					//put the values in the productmap where the ndbnumber in the servings size file matches the ndbnumber in productsmap
					productsMap.get(csvRecord.get(0)).setServingSize(Float.parseFloat(csvRecord.get(1)));		
					productsMap.get(csvRecord.get(0)).setServingUom(csvRecord.get(2));
					productsMap.get(csvRecord.get(0)).setHouseholdSize(Float.parseFloat(csvRecord.get(3)));
					productsMap.get(csvRecord.get(0)).setHouseholdUom(csvRecord.get(4));;
				}
			}
		}
		catch (FileNotFoundException e1) { e1.printStackTrace(); }
		catch (IOException e1) { e1.printStackTrace(); }
	}
	
	public boolean readProfiles(String profileFile) {
		boolean readFileSuccess = false;
		DataFiler dataFile = null;
		String [] fileType = profileFile.split("\\.");
		if(fileType[1].equals("csv")) {	
			dataFile = new CSVFiler();
		} else {
			dataFile = new XMLFiler();
		}
		readFileSuccess = dataFile.readFile(profileFile);	//calls readFile method in CSVFiler/XMLFiler and sets readFileSuccess to value returned by method
		return readFileSuccess;		
	}
	
	public void writeFile(String fileName) {
		DataFiler datafile = new CSVFiler();
		datafile.writeFile(fileName);
	}
}
