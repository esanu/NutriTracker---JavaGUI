//Name: Sayo Sanu Andrew ID: esanu
package hw3;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javafx.beans.binding.ObjectBinding;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.util.Callback;

public class Controller {

	ObjectBinding<ObservableList<RecommendedNutrient>> recommendedNutrientBinding = new ObjectBinding<ObservableList<RecommendedNutrient>>() {
		{
			super.bind(NutriByte.view.genderComboBox.valueProperty(), NutriByte.view.ageTextField.textProperty(),
					NutriByte.view.weightTextField.textProperty(), NutriByte.view.heightTextField.textProperty(), 
					NutriByte.view.physicalActivityComboBox.valueProperty());
		}
		@Override
		protected ObservableList<RecommendedNutrient> computeValue() {
		int index = 0;
		float age, weight, height, physicalActivity;
		String ingredients = NutriByte.view.ingredientsToWatchTextArea.getText();
		if(NutriByte.view.genderComboBox.getValue()!=null)
		{
			try{
				if(!NutriByte.view.ageTextField.getText().isEmpty()||Float.parseFloat(NutriByte.view.ageTextField.getText()) > 0) {
					age= Float.parseFloat(NutriByte.view.ageTextField.getText()); 
				} else return null; 
				
				if(!NutriByte.view.weightTextField.getText().isEmpty()||Float.parseFloat(NutriByte.view.weightTextField.getText()) > 0) {
					weight = Float.parseFloat(NutriByte.view.weightTextField.getText());
				} else return null;
				
				if(!NutriByte.view.heightTextField.getText().isEmpty()||Float.parseFloat(NutriByte.view.heightTextField.getText()) > 0) {
					height=Float.parseFloat(NutriByte.view.heightTextField.getText());
				} else return null;
			} catch(NumberFormatException e){
				return null;
			}
			
			if(NutriByte.view.physicalActivityComboBox.getValue() != null) {
				String physActivityLevel = NutriByte.view.physicalActivityComboBox.getValue();
				while(!physActivityLevel.equals(NutriProfiler.PhysicalActivityEnum.values()[index].getName())) {	
					index++;
				}
				physicalActivity = NutriProfiler.PhysicalActivityEnum.values()[index].getPhysicalActivityLevel();
			} else physicalActivity = 1.0f;
			
			
			if(NutriByte.view.genderComboBox.getValue().equals("Female")){
				NutriByte.person= new Female(age, weight, height, physicalActivity, ingredients);
			}
			else if(NutriByte.view.genderComboBox.getValue().equals("Male")){
				NutriByte.person= new Male(age, weight, height, physicalActivity, ingredients);	
			}
			NutriProfiler.createNutriProfile(NutriByte.person); 
			return NutriByte.person.recommendedNutrientsList; //returns person's recommendedNutrientsList
		}
		return  null;
	}
};
		
	class RecommendNutrientsButtonHandler implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent event) {
			int index = 0;		//declared and initialized an index to get the position of the physical activity in enum	
			float age = 0.0f, weight = 0.0f, height = 0.0f, physicalActivity = 0.0f;
			String gender = null;
			String ingredients = NutriByte.view.ingredientsToWatchTextArea.getText();
			String physActivityLevel = NutriByte.view.physicalActivityComboBox.getValue();
			
			try {
				if(NutriByte.view.genderComboBox.getSelectionModel().isEmpty()) throw new InvalidProfileException("Missing gender information");
				else gender = NutriByte.view.genderComboBox.getSelectionModel().getSelectedItem();
				
				if(NutriByte.view.ageTextField.getText().isEmpty()) throw new InvalidProfileException("Missing age information");
				if(Float.parseFloat(NutriByte.view.ageTextField.getText()) < 0) throw new InvalidProfileException("Age must be a positive number");
				else age = Float.parseFloat(NutriByte.view.ageTextField.getText());
					
			
				if(NutriByte.view.weightTextField.getText().isEmpty()) throw new InvalidProfileException("Missing weight information");
				if(Float.parseFloat(NutriByte.view.weightTextField.getText()) < 0) throw new InvalidProfileException("Weight must be a positive number");
				else weight = Float.parseFloat(NutriByte.view.weightTextField.getText());
		
				if(NutriByte.view.heightTextField.getText().isEmpty()) throw new InvalidProfileException("Missing height information");
				if(Float.parseFloat(NutriByte.view.heightTextField.getText()) < 0) throw new InvalidProfileException("Height must be a positive number");
				else height = Float.parseFloat(NutriByte.view.heightTextField.getText());
				
				while(!physActivityLevel.equals(NutriProfiler.PhysicalActivityEnum.values()[index].getName())) {	
					index++;
				}
				physicalActivity = NutriProfiler.PhysicalActivityEnum.values()[index].getPhysicalActivityLevel();
				
				switch(gender) {
				case "Female": NutriByte.person = new Female(age, weight, height, physicalActivity, ingredients); break;
				case "Male": NutriByte.person = new Male(age, weight, height, physicalActivity, ingredients); break;
				}
				NutriProfiler.createNutriProfile(NutriByte.person);	
			}catch(InvalidProfileException e) {
					
			}catch(NumberFormatException e1) {
				try {
					throw new InvalidProfileException("Invalid data entered. Must be a number. Please check the profile and try again.");
				} catch(InvalidProfileException e) {
					
				}
			}			
		}			
	}

	class OpenMenuItemHandler implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent event) {			
			NutriByte.view.recommendedNutrientsTableView.itemsProperty().unbind();
			NutriByte.view.initializePrompts();	
			NutriByte.view.productIngredientsTextArea.clear();
			NutriByte.view.servingSizeLabel.setText("");
			NutriByte.view.householdSizeLabel.setText("");
			NutriByte.view.householdServingUom.setText("");
			NutriByte.view.servingUom.setText("");
			NutriByte.view.dietProductsTableView.getItems().clear();
			NutriByte.view.productSearchTextField.clear();
			NutriByte.view.ingredientSearchTextField.clear();
			NutriByte.view.nutrientSearchTextField.clear();
			if(NutriByte.view.productsComboBox.getValue() != null) {
				NutriByte.view.productsComboBox.getItems().clear();
			}
			NutriByte.view.searchResultSizeLabel.setText("");
			NutriByte.view.productNutrientsTableView.getItems().clear();
			NutriByte.view.dietHouseholdUomLabel.setText("");
			NutriByte.view.dietServingUomLabel.setText("");
			NutriByte.view.dietHouseholdSizeTextField.clear();
			NutriByte.view.dietServingSizeTextField.clear();
			NutriByte.view.nutriChart.clearChart();
			

			FileChooser fileChooser = new FileChooser();
			fileChooser.setTitle("Select profile");	
//			fileChooser.setInitialDirectory(new File("C:\\Users\\ebuns\\eclipse-workspace\\Homework\\profiles")); 
			fileChooser.getExtensionFilters().addAll(
					new ExtensionFilter("CSV Files", "*.csv"),
					new ExtensionFilter("XML Files", "*.xml"),
					new ExtensionFilter("All Files", "*.*"));
			File file = null;
			if((file = fileChooser.showOpenDialog(NutriByte.stage)) != null) {			
				
				
				Model m = new Model();
				try {
					String profileFile = file.getAbsolutePath();
					if(m.readProfiles(profileFile) == true) {
						NutriByte.view.root.setCenter(NutriByte.view.nutriTrackerPane);
						
						if(NutriByte.person instanceof Female) {
							NutriByte.view.genderComboBox.getSelectionModel().selectFirst();
						} else {
							NutriByte.view.genderComboBox.getSelectionModel().select(1);;
						}
						NutriByte.view.ageTextField.setText(Float.toString(NutriByte.person.age));
						NutriByte.view.weightTextField.setText(Float.toString(NutriByte.person.weight));
						NutriByte.view.heightTextField.setText(Float.toString(NutriByte.person.height));
						NutriByte.view.ingredientsToWatchTextArea.setText(NutriByte.person.ingredientsToWatch);
						
						int phyActLevel = NutriByte.person.physLevel;	
						NutriByte.view.physicalActivityComboBox.getSelectionModel().select(phyActLevel);	//..to set physical activity level in combo box
						NutriByte.view.recommendedNutrientsTableView.setItems(NutriByte.person.recommendedNutrientsList);
						if(!(NutriByte.person.dietProductsList.isEmpty())) {
							NutriByte.view.productsComboBox.setItems(NutriByte.person.dietProductsList);
							NutriByte.view.productsComboBox.getSelectionModel().selectFirst();
							NutriByte.view.dietProductsTableView.setItems(NutriByte.person.dietProductsList);
							NutriByte.view.searchResultSizeLabel.setText(NutriByte.person.dietProductsList.size() + " product(s) found");
							Product product = NutriByte.view.productsComboBox.getValue();
//							Product pos = m.searchResultsList.get(m.searchResultsList.indexOf(product));
//							
							NutriByte.view.servingSizeLabel.setText(String.format("%.2f", (NutriByte.model.searchResultsList.get(0).getServingSize()/NutriByte.model.searchResultsList.get(0).getHouseholdSize())));
							NutriByte.view.householdSizeLabel.setText(String.format("%.2f", NutriByte.model.searchResultsList.get(0).getHouseholdSize()/NutriByte.model.searchResultsList.get(0).getHouseholdSize()));
							NutriByte.view.dietServingUomLabel.setText(product.getServingUom());
							NutriByte.view.dietHouseholdUomLabel.setText(product.getHouseholdUom());
							NutriByte.view.servingUom.setText(product.getServingUom());
							NutriByte.view.householdServingUom.setText(product.getHouseholdUom());
							NutriByte.view.productIngredientsTextArea.setText("Product ingredients: " + product.getIngredients());
							ObservableList<Product.ProductNutrient> prodNutrientList = FXCollections.observableArrayList();
							if(NutriByte.view.productsComboBox.getValue() != null) {
								for(Product.ProductNutrient productNutrient: product.getProductNutrients().values()) {
									prodNutrientList.add(productNutrient);
								}
							}						
							NutriByte.view.productNutrientsTableView.setItems(prodNutrientList);
						}
						Callback<CellDataFeatures<Product.ProductNutrient, String>, ObservableValue<String>> productNutrientNameCallBack = new Callback<CellDataFeatures<Product.ProductNutrient, String>, ObservableValue<String>>() {
							@Override
							public ObservableValue<String> call(CellDataFeatures<Product.ProductNutrient, String> product) {
								Nutrient nutrient = Model.nutrientsMap.get(product.getValue().getNutrientCode());
								return nutrient.nutrientNameProperty();		
							}
						};
						
						Callback<CellDataFeatures<Product.ProductNutrient, String>, ObservableValue<String>> productNutrientUomCallBack = new Callback<CellDataFeatures<Product.ProductNutrient, String>, ObservableValue<String>>() {
							@Override
							public ObservableValue<String> call(CellDataFeatures<Product.ProductNutrient, String> product) {
								Nutrient nutrient = Model.nutrientsMap.get(product.getValue().getNutrientCode());
								return nutrient.getNutrientUomProperty();		
							}
						};						
						NutriByte.view.productNutrientNameColumn.setCellValueFactory(productNutrientNameCallBack);
						NutriByte.view.productNutrientQuantityColumn.setCellValueFactory(new PropertyValueFactory<>("nutrientQuantity"));
						NutriByte.view.productNutrientUomColumn.setCellValueFactory(productNutrientUomCallBack); 
						
						NutriProfiler.createNutriProfile(NutriByte.person);	//createNutriProfile populates the recommendedNutrients list
						NutriByte.person.populateDietNutrientMap();
						NutriByte.view.nutriChart.updateChart();
					} 	
				}catch(InvalidProfileException e) {
					e.getMessage();		
				}	
			} 
			NutriByte.view.recommendedNutrientsTableView.itemsProperty().bind(recommendedNutrientBinding);
		}
	}

	class NewMenuItemHandler implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent event) {
			NutriByte.view.root.setCenter(NutriByte.view.nutriTrackerPane);		//sets the border pane view to the nutri tracker pane
			NutriByte.view.initializePrompts();	
			NutriByte.view.recommendedNutrientsTableView.itemsProperty().bind(recommendedNutrientBinding);

			NutriByte.view.productIngredientsTextArea.clear();
			NutriByte.view.servingSizeLabel.setText("");
			NutriByte.view.householdSizeLabel.setText("");
			NutriByte.view.householdServingUom.setText("");
			NutriByte.view.servingUom.setText("");
			NutriByte.view.dietProductsTableView.getItems().clear();
			NutriByte.view.productSearchTextField.clear();
			NutriByte.view.ingredientSearchTextField.clear();
			NutriByte.view.nutrientSearchTextField.clear();
			if(NutriByte.view.productsComboBox.getValue() != null) {
				NutriByte.view.productsComboBox.getItems().clear();
			}
			NutriByte.view.searchResultSizeLabel.setText("");
			NutriByte.view.productNutrientsTableView.getItems().clear();
			NutriByte.view.dietHouseholdUomLabel.setText("");
			NutriByte.view.dietServingUomLabel.setText("");
			NutriByte.view.dietHouseholdSizeTextField.clear();
			NutriByte.view.dietServingSizeTextField.clear();
			NutriByte.view.nutriChart.clearChart();			
		}
	}

	class AboutMenuItemHandler implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent event) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("About");
			alert.setHeaderText("NutriByte");
			alert.setContentText("Version 2.0 \nRelease 1.0\nCopyleft Java Nerds\nThis software is designed purely for educational purposes.\nNo commercial use intended");
			Image image = new Image(getClass().getClassLoader().getResource(NutriByte.NUTRIBYTE_IMAGE_FILE).toString());
			ImageView imageView = new ImageView();
			imageView.setImage(image);
			imageView.setFitWidth(300);
			imageView.setPreserveRatio(true);
			imageView.setSmooth(true);
			alert.setGraphic(imageView);
			alert.showAndWait();
		}
	}
	
	class SaveMenuItemHandler implements EventHandler<ActionEvent>{
		@Override
		public void handle(ActionEvent event) {
			FileChooser fileChooser = new FileChooser();
			fileChooser.setTitle("Save profile");			
			fileChooser.getExtensionFilters().addAll(		//adds extensions for csv and xml files
					new ExtensionFilter("CSV Files", "*.csv"),
					new ExtensionFilter("All Files", "*.*"));
//			fileChooser.setInitialDirectory(new File("C:\\Users\\ebuns\\eclipse-workspace\\Homework\\profiles")); 
			File file = null;
			try {
				if(NutriByte.view.genderComboBox.getSelectionModel().isEmpty()) throw new InvalidProfileException("Missing gender information");
				
				if(NutriByte.view.ageTextField.getText().isEmpty()) throw new InvalidProfileException("Missing age information");
				if(Float.parseFloat(NutriByte.view.ageTextField.getText()) < 0) throw new InvalidProfileException("Age must be a positive number");
			
				if(NutriByte.view.weightTextField.getText().isEmpty()) throw new InvalidProfileException("Missing weight information");
				if(Float.parseFloat(NutriByte.view.weightTextField.getText()) < 0) throw new InvalidProfileException("Weight must be a positive number");
			
				if(NutriByte.view.heightTextField.getText().isEmpty()) throw new InvalidProfileException("Missing height information");
				if(Float.parseFloat(NutriByte.view.heightTextField.getText()) < 0) throw new InvalidProfileException("Height must be a positive number");
				
				else if((file = fileChooser.showSaveDialog(NutriByte.stage)) != null) {
					String profileFile = file.getAbsolutePath();
					Model m = new Model();	
					m.writeFile(profileFile);
				}
			}catch(InvalidProfileException e) {
					
			}catch(NumberFormatException e1) {
				try {
					throw new InvalidProfileException("Invalid input. Input for age, weight and height must be numbers.");
				} catch(InvalidProfileException e) {
					
				}
			}		
		}
	}
	
	class SearchButtonHandler implements EventHandler<ActionEvent>{
		@Override
		public void handle(ActionEvent event) {
			Model m = new Model();
			String productSearch = NutriByte.view.productSearchTextField.getText();
			List<Product> noIngredientList = new ArrayList<>();
			List<Product> noNutrient = new ArrayList<>();
			
			for(Product product: Model.productsMap.values()) {
				if(product.getProductName().toLowerCase().contains(productSearch.toLowerCase())) {
					m.searchResultsList.add(product);
				}
				NutriByte.view.searchResultSizeLabel.setText(m.searchResultsList.size() + " product(s) found");

				if(!NutriByte.view.nutrientSearchTextField.getText().isEmpty()) {
					String nutrientSearch = NutriByte.view.nutrientSearchTextField.getText();
					String searchNutrientCode = null;
					//get nutrient code of the nutrientSearch word
					for(Nutrient nutrient: Model.nutrientsMap.values()) {
						if(nutrient.getNutrientName().toLowerCase().contains(nutrientSearch.toLowerCase())) {
							searchNutrientCode = nutrient.getNutrientCode();
						}
					}
					for(Product productInSearch: m.searchResultsList) {
						if(!(productInSearch.getProductNutrients().containsKey(searchNutrientCode))) {
							noNutrient.add(productInSearch);
						}
					}
					m.searchResultsList.removeAll(noNutrient);
				}
				NutriByte.view.searchResultSizeLabel.setText(m.searchResultsList.size() + " product(s) found");
				
				if(!NutriByte.view.ingredientSearchTextField.getText().isEmpty()) {
					String ingredientSearch = NutriByte.view.ingredientSearchTextField.getText();
					for(Product productFound: m.searchResultsList) {
						if(!(productFound.getIngredients().toLowerCase().contains(ingredientSearch.toLowerCase()))) {
							noIngredientList.add(productFound);
						}
					}
					m.searchResultsList.removeAll(noIngredientList);
					NutriByte.view.searchResultSizeLabel.setText(m.searchResultsList.size() + " product(s) found");
				}
				
				NutriByte.view.productsComboBox.setItems(m.searchResultsList);
				NutriByte.view.productsComboBox.getSelectionModel().selectFirst();
			}
		}
	}
	
	class ClearButtonHandler implements EventHandler<ActionEvent>{
		@Override
		public void handle(ActionEvent event) {
			NutriByte.view.productSearchTextField.clear();
			NutriByte.view.nutrientSearchTextField.clear();
			NutriByte.view.ingredientSearchTextField.clear();
			NutriByte.view.productsComboBox.getSelectionModel().clearSelection();
			NutriByte.view.productIngredientsTextArea.clear();
			NutriByte.view.householdSizeLabel.setText("");
			NutriByte.view.servingSizeLabel.setText("");
			NutriByte.view.dietServingUomLabel.setText("");
			NutriByte.view.dietHouseholdUomLabel.setText("");
			NutriByte.view.searchResultSizeLabel.setText("");
			NutriByte.view.dietServingSizeTextField.clear();
			NutriByte.view.dietHouseholdSizeTextField.clear();
			NutriByte.view.productsComboBox.setItems(null);
			NutriByte.view.productNutrientsTableView.getItems().clear();	
		}
	}
	
	class ProductsComboBoxListener implements EventHandler<ActionEvent>{
		@Override
		public void handle(ActionEvent event) {
			//display product nutrients in productNutrientsTableView
			if(NutriByte.view.productsComboBox.getValue() != null) {
				Product product = NutriByte.view.productsComboBox.getValue();
				ObservableList<Product.ProductNutrient> prodNutrientList = FXCollections.observableArrayList();
				NutriByte.view.productIngredientsTextArea.setText("Product ingredients: " + product.getIngredients());
				NutriByte.view.householdSizeLabel.setText(product.getHouseholdSize()+ " " + product.getHouseholdUom());
				NutriByte.view.servingSizeLabel.setText(product.getServingSize()+ " " + product.getServingUom());
				NutriByte.view.dietServingUomLabel.setText(product.getServingUom());
				NutriByte.view.dietHouseholdUomLabel.setText(product.getHouseholdUom());
				for(Product.ProductNutrient productNutrient: product.getProductNutrients().values()) {
					prodNutrientList.add(productNutrient);
				}
				NutriByte.view.productNutrientsTableView.setItems(prodNutrientList);
				
				Callback<CellDataFeatures<Product.ProductNutrient, String>, ObservableValue<String>> productNutrientNameCallBack = new Callback<CellDataFeatures<Product.ProductNutrient, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Product.ProductNutrient, String> product) {
						Nutrient nutrient = Model.nutrientsMap.get(product.getValue().getNutrientCode());
						return nutrient.nutrientNameProperty();		
					}
				};
				
				Callback<CellDataFeatures<Product.ProductNutrient, String>, ObservableValue<String>> productNutrientUomCallBack = new Callback<CellDataFeatures<Product.ProductNutrient, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Product.ProductNutrient, String> product) {
						Nutrient nutrient = Model.nutrientsMap.get(product.getValue().getNutrientCode());
						return nutrient.getNutrientUomProperty();		
					}
				};
				NutriByte.view.productNutrientNameColumn.setCellValueFactory(productNutrientNameCallBack);
				NutriByte.view.productNutrientQuantityColumn.setCellValueFactory(new PropertyValueFactory<>("nutrientQuantity"));
				NutriByte.view.productNutrientUomColumn.setCellValueFactory(productNutrientUomCallBack); 
			}
		}
	}
	
	class AddDietButtonHandler implements EventHandler<ActionEvent>{
		@Override
		public void handle(ActionEvent event) {
			if(NutriByte.view.productsComboBox.getValue() != null) {
				Product product = NutriByte.view.productsComboBox.getSelectionModel().getSelectedItem();
				if(!(NutriByte.view.dietServingSizeTextField.getText().isEmpty())) {
					float dietSize = Float.parseFloat(NutriByte.view.dietServingSizeTextField.getText().trim());
					float householdSize = (dietSize*product.getHouseholdSize())/product.getServingSize();
					product.setHouseholdSize(householdSize);
					product.setServingSize(dietSize);
				}
				
				if(!(NutriByte.view.dietHouseholdSizeTextField.getText().isEmpty()) && (NutriByte.view.dietServingSizeTextField.getText().isEmpty())) {
					float dietHouseHoldSize = Float.parseFloat(NutriByte.view.dietHouseholdSizeTextField.getText().trim());
					float servingSize = (dietHouseHoldSize * product.getServingSize())/product.getHouseholdSize();
					product.setServingSize(servingSize);
					product.setHouseholdSize(dietHouseHoldSize);
				}
				//check if the product is already in the list?
				NutriByte.person.dietProductsList.add(product);
			}
			NutriByte.view.dietProductsTableView.setItems(NutriByte.person.dietProductsList);
			NutriByte.person.populateDietNutrientMap();
			NutriByte.view.nutriChart.updateChart();
		}
	}
	
	class RemoveDietButtonHandler implements EventHandler<ActionEvent>{
		@Override
		public void handle(ActionEvent arg0) {
			if(NutriByte.view.dietProductsTableView.getSelectionModel().getSelectedIndex() >=0) {
				int selectedProduct = NutriByte.view.dietProductsTableView.getSelectionModel().getFocusedIndex();
				NutriByte.person.dietProductsList.remove(selectedProduct);
				NutriByte.view.dietProductsTableView.setItems(NutriByte.person.dietProductsList);
				NutriByte.person.populateDietNutrientMap();
				NutriByte.view.nutriChart.updateChart();
				if(NutriByte.person.dietNutrientsMap.isEmpty()) {
					NutriByte.view.nutriChart.clearChart();
				}	
			}
		}		
	}
	
	class CloseMenuItemHandler implements EventHandler<ActionEvent>{
		public void handle(ActionEvent event) {
			NutriByte.view.root.setCenter(NutriByte.view.setupWelcomeScene());		
		}
	}
}
