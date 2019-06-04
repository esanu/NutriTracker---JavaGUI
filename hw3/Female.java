//Name: Sayo Sanu Andrew ID: esanu
package hw3;

public class Female extends Person {

	float[][] nutriConstantsTableFemale = new float[][]{
		//AgeGroups: 3M, 6M, 1Y, 3Y, 8Y, 13Y, 18Y, 30Y, 50Y, ABOVE 
		{1.52f, 1.52f, 1.2f, 1.05f, 0.95f, 0.95f, 0.71f, 0.8f, 0.8f, 0.8f}, //0: Protein constants
		{60, 60, 95, 130, 130, 130, 130, 130, 130, 130}, //1: Carbohydrate
		{19, 19, 19, 19, 25, 26, 26, 25, 25, 21},  //2: Fiber constants
		{36, 36, 32, 21, 16, 15, 14, 14, 14, 14}, 	//3: Histidine
		{88, 88, 43, 28, 22, 21, 19, 19, 19, 19}, 	//4: isoleucine
		{156, 156, 93, 63, 49, 47, 44 , 42, 42, 42},//5: leucine
		{107, 107, 89, 58, 46, 43, 40, 38, 38, 38}, //6: lysine
		{59, 59, 43, 28, 22, 21, 19, 19, 19, 19}, 	//7: methionine
		{59, 59, 43, 28, 22, 21, 19, 19, 19, 19}, 	//8: cysteine
		{135, 135, 84, 54, 41, 38, 35, 33, 33, 33}, //9: phenylalanine
		{135, 135, 84, 54, 41, 38, 35, 33, 33, 33}, //10: phenylalanine
		{73, 73, 49, 32, 24, 22, 21, 20, 20, 20}, 	//11: threonine
		{28, 28, 13, 8, 6, 6, 5, 5, 5, 5}, 			//12: tryptophan
		{87, 87, 58, 37, 28, 27, 24, 24, 24, 24	}  	//13: valine
	};
	
	Female(float age, float weight, float height, float physicalActivityLevel, String ingredientsToAvoid) {
		super(age, weight, height, physicalActivityLevel, ingredientsToAvoid);	//invokes super's constructor
		initializeNutriConstantsTable();	//also invokes method to initialize constants table
	}

	@Override
	float calculateEnergyRequirement() {
		float energyRequirement = 0.0f;
		int consX = 0;
		double consA = 0, consB = 0, consC = 0, consD = 0;
		switch(groupIndex) {	//based on age group, energy requirement is calculated
		case 0: consX = -75; energyRequirement = 89*weight - consX; break;
		case 1: consX = 44; energyRequirement = 89*weight - consX; break;
		case 2: consX = 78; energyRequirement = 89*weight - consX;break;
		case 3: consX = 80; energyRequirement = 89*weight - consX; break;
		case 4: 
		case 5:
		case 6: consA = 135.3; consB = 30.8; consC = 10; consD = 934;
				energyRequirement = (float) ((consA - (consB * age)) + physicalActivityLevel * (consC*weight + (consD*height/100)) + 20); 
				break;
		case 7: 
		case 8: 
		case 9: consA = 354; consB = 6.91; consC = 9.36; consD = 726; 
				energyRequirement = (float) ((consA - (consB * age)) + physicalActivityLevel * (consC*weight + (consD*height/100)) );
				break;
		}	
		return energyRequirement;
	}

	@Override
	void initializeNutriConstantsTable() {
		for(int i = 0; i < NutriProfiler.RECOMMENDED_NUTRI_COUNT; i++) {
			for(int j = 0; j < NutriProfiler.AGE_GROUP_COUNT; j++) {
				nutriConstantsTable[i][j]	=  nutriConstantsTableFemale[i][j];		//loads the constants in this class into the Person class
			}
		}
	}
}
