/**
 * This class is to make Country objects for Project1.java.
 * It includes getter and setter methods, as well as a method
 * that calculates the GDP, a method that compares Country
 * objects by their names, and a method that prints the information
 * about a Country object.
 * <p>
 * @author Hailey Francis (n01402670)
 * @version September 13, 2019
 */
public class Country {

	private String name, code, capital;
	private int population, rank;
	private double GDP;
	
	public Country(String fname, String fcode, String fcapital, int fpopulation, double fGDP, int frank){
		name = fname;
		code = fcode;
		capital = fcapital;
		population = fpopulation;
		GDP = fGDP;
		rank = frank;	
	}
	
	/**
	 * getter methods
	 * @return 
	 */
	public String getName() {
		return name;
	}
	
	public String getCode() {
		return code;
	}
	
	public String getCapital() {
		return capital;
	}
	
	public int getPopulation() {
		return population;
	}
	
	public double getGDP() {
		return GDP;
	}
	
	public int getRank() {
		return rank;
	}
	
	/**
	 * setter methods
	 * @param 
	 */
	public void setName(String newName) {
		name = newName;
	}
	
	public void setCode(String newCode) {
		code = newCode;
	}
	
	public void setCapital(String newCapital) {
		capital = newCapital;
	}
	
	public void setPopulation(int newPopulation) {
		population = newPopulation;
	}
	
	public void setGDP(double newGDP) {
		GDP = newGDP;
	}
	
	/**
	 * Compares countries based on their names. 
	 * Returns -1 if this.name comes before c.name,
	 * returns 1 if this.name comes after c.name
	 * returns 0 if this.name and c.name are equivalent
	 * 
	 * @param c -- Country being compared
	 * @return 0, 1, -1 depending on value of names
	 */
	public int compareNameTo(Country c) {
		String thisName = this.getName();
		String cName = c.getName();
		if(thisName.compareTo(cName) < 0) {
			return -1; // this country comes before c
		}else if(thisName.compareTo(cName) > 0) {
			return 1; // this country comes after c
		}else {
			return 0; //same name, same country
		}
	}
	
	/**
	 * Compares String names using the compareTo method, but
	 * returns 1, -1, or 0 depending on if this.name comes
	 * before, after, or is the same as the target name.
	 * 
	 * @param targetName -- name we are comparing to
	 * @return 1, -1, or 0 depending on the value of names
	 */
	public int compareNameTo(String targetName) {
		String thisName = this.getName();
		if(thisName.compareTo(targetName) < 0) {
			return -1; // this country comes before c
		}else if(thisName.compareTo(targetName) > 0) {
			return 1; // this country comes after c
		}else {
			return 0; //same name, same country
		}
	}
	
	/**
	 * Calculates the GDP per capita of this Country object
	 * by dividing the GDP by the population.
	 * 
	 * @return GDP per capita -- GDP / Population
	 */
	public double calcGDPperCapita() {
		return this.getGDP() / (double) this.getPopulation();
	}
	
	public void print() {
		System.out.println("Name: " + name);
		System.out.println("Code: " + code);
		System.out.println("Capital: " + capital);
		System.out.println("Population: " + population);
		System.out.println("GDP: " + GDP);
		System.out.println("Happiness Rank: " + rank + "\n");
	}
	
	
}
