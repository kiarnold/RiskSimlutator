package risk;

import java.util.List;

public class Territory {
	private int troops;
	private String name;
	private List<Territory> connections;
	
	/**
	 * Constructor builds the object with name initalized, 
	 * troops set to 0, and an empty connections list.
	 * 
	 * @param name	the name of the territory.
	 **/
	public Territory(String name){
		troops = 0;
		this.name = name; 
		connections = new ArrayList<Territory>();
	}

	// Getters and Setters
	public int getTroops() { return troops; }

	public String getName() { return name; }

	public void setTroops(int num) { this.troops = num; }
	
	// toString
	@Override
	public String toString(){ return name; }
	
}
